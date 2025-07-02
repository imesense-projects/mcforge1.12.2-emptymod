package org.imesense.boilerplate.mcforge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link BoilerplateMcforge} functionality.
 *
 * @see BoilerplateMcforge
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings("LoggingSimilarMessage")
public class BoilerplateMcforgeTest
{
    /**
     * The fully qualified class name of {@link BoilerplateMcforge} being tested.
     *
     * @see BoilerplateMcforge
     */
    private static final String CLASS_NAME =
        "org.imesense.boilerplate.mcforge.BoilerplateMcforge";

    /**
     * The log message template used for method call logging.
     */
    private static final String LOG_MESSAGE =
        "Called {}.{} method";

    /**
     * Mocked logger instance for verifying log messages.
     *
     * @see Logger
     */
    @Mock
    private Logger mockLogger;

    /**
     * Mocked pre-initialization event for testing.
     *
     * @see FMLPreInitializationEvent
     */
    @Mock
    private FMLPreInitializationEvent mockPreInitEvent;

    /**
     * Mocked initialization event for testing.
     *
     * @see FMLInitializationEvent
     */
    @Mock
    private FMLInitializationEvent mockInitEvent;

    /**
     * Mocked post-initialization event for testing.
     *
     * @see FMLPostInitializationEvent
     */
    @Mock
    private FMLPostInitializationEvent mockPostInitEvent;

    /**
     * Mocked load complete event for testing.
     *
     * @see FMLLoadCompleteEvent
     */
    @Mock
    private FMLLoadCompleteEvent mockLoadCompleteEvent;

    /**
     * Mocked server starting event for testing.
     *
     * @see FMLServerStartingEvent
     */
    @Mock
    private FMLServerStartingEvent mockServerStartingEvent;

    /**
     * Mocked server stopped event for testing.
     *
     * @see FMLServerStoppedEvent
     */
    @Mock
    private FMLServerStoppedEvent mockServerStoppedEvent;

    /**
     * Mocked mod metadata for testing.
     *
     * @see ModMetadata
     */
    @Mock
    private ModMetadata mockModMetadata;

    /**
     * Instance of {@link BoilerplateMcforge} being tested.
     *
     * @see BoilerplateMcforge
     */
    private BoilerplateMcforge boilerplateMcforge;

    /**
     * Sets up the test environment before each test method execution.
     * Initializes the {@link BoilerplateMcforge} instance and injects the mock logger.
     *
     * @see BoilerplateMcforge
     */
    @BeforeEach
    public void setUp()
    {
        boilerplateMcforge = new BoilerplateMcforge();

        try
        {
            Field field = BoilerplateMcforge.class.getDeclaredField("logger");
            field.setAccessible(true);
            field.set(null, mockLogger);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        when(mockPreInitEvent.getModMetadata()).thenReturn(mockModMetadata);
    }

    /**
     * Tests that the {@link BoilerplateMcforge} constants are set correctly.
     *
     * @see BoilerplateMcforge
     */
    @Test
    public void boilerplateMcforge_Constants_SetsCorrect()
    {
        assertEquals("boilerplatemcforge", BoilerplateMcforge.MOD_ID);
        assertEquals("Minecraft Forge Modification Boilerplate", BoilerplateMcforge.NAME);
        assertEquals("1.12.2-14.23.5.2860", BoilerplateMcforge.VERSION);
    }

    /**
     * Tests that the {@code logMethodCall} method logs the correct message.
     *
     * @throws Exception if reflection access fails
     */
    @Test
    public void boilerplateMcforge_logMethodCall_LogsCorrect() throws Exception
    {
        Method method = BoilerplateMcforge.class.getDeclaredMethod("logMethodCall", String.class);
        method.setAccessible(true);
        method.invoke(boilerplateMcforge, "testMethod");
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "testMethod");
    }

    /**
     * Tests that {@link BoilerplateMcforge#setLocaleMetadata} correctly sets localized metadata.
     */
    @Test
    public void boilerplateMcforge_setLocaleMetadata_SetsCorrect()
    {
        try (MockedStatic<I18n> mockedI18n = mockStatic(I18n.class))
        {
            String expectedName = "Localized Mod Name";
            String expectedDesc = "Localized Mod Description";

            mockedI18n.when(() -> I18n.format("mod.boilerplatemcforge.name")).thenReturn(expectedName);
            mockedI18n.when(() -> I18n.format("mod.boilerplatemcforge.description")).thenReturn(expectedDesc);

            BoilerplateMcforge.setLocaleMetadata(mockPreInitEvent);

            assertEquals(expectedName, mockModMetadata.name);
            assertEquals(expectedDesc, mockModMetadata.description);
        }
    }

    /**
     * Tests that {@link BoilerplateMcforge#preInit} logs an error when called from client side.
     */
    @Test
    public void boilerplateMcforge_preInit_ClientSideLogsError()
    {
        when(mockPreInitEvent.getSide()).thenReturn(Side.CLIENT);

        try (MockedStatic<I18n> mockedI18n = mockStatic(I18n.class))
        {
            mockedI18n.when(() -> I18n.format(anyString())).thenReturn("test");

            boilerplateMcforge.preInit(mockPreInitEvent);

            verify(mockLogger).error("No mixin config files found!");
        }
    }

    /**
     * Tests that {@link BoilerplateMcforge#preInit} logs correctly when called from server side.
     */
    @Test
    public void boilerplateMcforge_preInit_ServerSideLogsCorrect()
    {
        when(mockPreInitEvent.getSide()).thenReturn(Side.SERVER);

        boilerplateMcforge.preInit(mockPreInitEvent);

        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "preInit");
        verifyNoMoreInteractions(mockLogger);
    }

    /**
     * Tests that {@link BoilerplateMcforge#init} logs the correct method call.
     */
    @Test
    public void boilerplateMcforge_init_CallsCorrect()
    {
        boilerplateMcforge.init(mockInitEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "init");
    }

    /**
     * Tests that {@link BoilerplateMcforge#postInit} logs the correct method call.
     */
    @Test
    public void boilerplateMcforge_postInit_CallsCorrect()
    {
        boilerplateMcforge.postInit(mockPostInitEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "postInit");
    }

    /**
     * Tests that {@link BoilerplateMcforge#onLoadComplete} logs the correct method call.
     */
    @Test
    public void boilerplateMcforge_onLoadComplete_CallsCorrect()
    {
        boilerplateMcforge.onLoadComplete(mockLoadCompleteEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "onLoadComplete");
    }

    /**
     * Tests that {@link BoilerplateMcforge#serverLoad} logs the correct method call.
     */
    @Test
    public void boilerplateMcforge_serverLoad_CallsCorrect()
    {
        boilerplateMcforge.serverLoad(mockServerStartingEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "serverLoad");
    }

    /**
     * Tests that {@link BoilerplateMcforge#serverStopped} logs the correct method call.
     */
    @Test
    public void boilerplateMcforge_serverStopped_CallsCorrect()
    {
        boilerplateMcforge.serverStopped(mockServerStoppedEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "serverStopped");
    }
}
