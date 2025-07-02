package org.imesense.emptymod.plugin;

import java.util.Collections;

import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LoadingPluginTest
{
    @Mock
    private Logger mockLogger;

    private Logger originalLogger;

    @BeforeEach
    public void setUp()
    {
        LoadingPlugin.enableTestMode();

        originalLogger = LoadingPlugin.logger;
        LoadingPlugin.logger = mockLogger;
    }

    @AfterEach
    public void tearDown()
    {
        LoadingPlugin.logger = originalLogger;
    }

    @Test
    public void loadingPlugin_Class_MCVersionAnnotationIsCorrect()
    {
        IFMLLoadingPlugin.MCVersion annotation = LoadingPlugin.class
            .getAnnotation(IFMLLoadingPlugin.MCVersion.class);
        assertNotNull(
            annotation,
            "@MCVersion annotation should be set"
        );
        assertEquals(
            "1.12.2",
            annotation.value(),
            "Minecraft version should be 1.12.2"
        );
    }

    @Test
    public void loadingPlugin_Constructor_LogsInitialization()
    {
        new LoadingPlugin();
        verify(mockLogger).info("Initializing LoadingPlugin");
        verify(mockLogger, never()).info("Mixin initialization complete");
    }

    @Test
    public void loadingPlugin_Constructor_ThrowsExceptionWhenMixinFails()
    {
        LoadingPlugin.testMode = false;

        doThrow(new RuntimeException("Mixin error"))
            .when(mockLogger)
            .debug("Initializing Mixin");

        try
        {
            Exception exception = assertThrows(RuntimeException.class, LoadingPlugin::new);
            assertEquals("Mixin error", exception.getMessage());
            verify(mockLogger).error(
                eq("Failed to initialize Mixin"),
                any(RuntimeException.class)
            );
        }
        finally
        {
            LoadingPlugin.testMode = true;
        }
    }

    @Test
    public void loadingPlugin_IFMLLoadingPlugin_MethodsCallsAreCorrect()
    {
        LoadingPlugin plugin = new LoadingPlugin();

        assertArrayEquals(new String[0], plugin.getASMTransformerClass());
        assertNull(plugin.getModContainerClass());
        assertNull(plugin.getSetupClass());
        assertNull(plugin.getAccessTransformerClass());

        assertDoesNotThrow(() -> plugin.injectData(Collections.emptyMap()));
        assertDoesNotThrow(() -> plugin.injectData(null));
    }
}
