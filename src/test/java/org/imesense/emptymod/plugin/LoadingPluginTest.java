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

/**
 * Test class for {@link LoadingPlugin} which verifies its behavior as an FML loading plugin.
 * This test class focuses on:
 * <ul>
 *   <li>Annotation configuration</li>
 *   <li>Initialization logging</li>
 *   <li>Error handling during mixin initialization</li>
 *   <li>Implementation of {@link IFMLLoadingPlugin} interface methods</li>
 * </ul>

 * @see LoadingPlugin
 * @see IFMLLoadingPlugin
 */
@ExtendWith(MockitoExtension.class)
public class LoadingPluginTest
{
    /**
     * Mock logger instance used to verify logging behavior in tests.
     *
     * @see Logger
     */
    @Mock
    private Logger mockLogger;

    /**
     * Stores the original logger instance to restore after tests.
     *
     * @see Logger
     */
    private Logger originalLogger;

    /**
     * Sets up the test environment before each test:
     * <ul>
     *   <li>Enables test mode in {@link LoadingPlugin}</li>
     *   <li>Replaces the original logger with a mock logger</li>
     * </ul>
     *
     * @see LoadingPlugin
     */
    @BeforeEach
    public void setUp()
    {
        LoadingPlugin.enableTestMode();

        originalLogger = LoadingPlugin.logger;
        LoadingPlugin.logger = mockLogger;
    }

    /**
     * Cleans up after each test by restoring the original logger.
     */
    @AfterEach
    public void tearDown()
    {
        LoadingPlugin.logger = originalLogger;
    }

    /**
     * Verifies that the {@link LoadingPlugin} class has the correct {@link IFMLLoadingPlugin.MCVersion} annotation
     * with the expected Minecraft version (1.12.2).
     *
     * @see LoadingPlugin
     * @see IFMLLoadingPlugin.MCVersion
     */
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

    /**
     * Tests that the constructor properly logs initialization messages.
     * Verifies that:
     * <ul>
     *   <li>The initialization message is logged</li>
     *   <li>The mixin completion message is not logged during construction</li>
     * </ul>
     */
    @Test
    public void loadingPlugin_Constructor_LogsInitialization()
    {
        new LoadingPlugin();
        verify(mockLogger).info("Initializing LoadingPlugin");
        verify(mockLogger, never()).info("Mixin initialization complete");
    }

    /**
     * Tests error handling when mixin initialization fails.
     * Verifies that:
     * <ul>
     *   <li>An exception is thrown when mixin fails to initialize</li>
     *   <li>The error is properly logged</li>
     *   <li>The original exception message is preserved</li>
     * </ul>
     */
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

    /**
     * Tests the implementation of {@link IFMLLoadingPlugin} interface methods.
     * Verifies that:
     * <ul>
     *   <li>Transformer class method returns empty array</li>
     *   <li>Other methods return null as expected</li>
     *   <li>Data injection methods don't throw exceptions</li>
     * </ul>
     *
     * @see IFMLLoadingPlugin
     */
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
