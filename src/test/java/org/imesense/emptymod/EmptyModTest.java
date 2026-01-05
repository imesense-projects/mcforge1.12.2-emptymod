package org.imesense.emptymod;

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
 * Test class for {@link EmptyMod} functionality.
 *
 * @see EmptyMod
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings("LoggingSimilarMessage")
public class EmptyModTest
{
    /**
     * The fully qualified class name of {@link EmptyMod} being tested.
     *
     * @see EmptyMod
     */
    private static final String CLASS_NAME =
        "org.imesense.emptymod.EmptyMod";

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
     * Instance of {@link EmptyMod} being tested.
     *
     * @see EmptyMod
     */
    private EmptyMod emptyMod;

    /**
     * Sets up the test environment before each test method execution.
     * Initializes the {@link EmptyMod} instance and injects the mock logger.
     *
     * @see EmptyMod
     */
    @BeforeEach
    public void setUp()
    {
        emptyMod = new EmptyMod();

        try
        {
            Field field = EmptyMod.class.getDeclaredField("logger");
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
     * Tests that the {@link EmptyMod} constants are set correctly.
     *
     * @see EmptyMod
     */
    @Test
    public void emptyMod_Constants_SetsCorrect()
    {
        assertEquals("emptymod", EmptyMod.MOD_ID);
        assertEquals("Empty Mod", EmptyMod.NAME);
        assertEquals("1.12.2-14.23.5.2864", EmptyMod.VERSION);
    }

    /**
     * Tests that the {@code logMethodCall} method logs the correct message.
     *
     * @throws Exception if reflection access fails
     */
    @Test
    public void emptyMod_logMethodCall_LogsCorrect() throws Exception
    {
        Method method = EmptyMod.class.getDeclaredMethod("logMethodCall", String.class);
        method.setAccessible(true);
        method.invoke(emptyMod, "testMethod");
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "testMethod");
    }

    /**
     * Tests that {@link EmptyMod#setLocaleMetadata} correctly sets localized metadata.
     */
    @Test
    public void emptyMod_setLocaleMetadata_SetsCorrect()
    {
        try (MockedStatic<I18n> mockedI18n = mockStatic(I18n.class))
        {
            String expectedName = "Localized Mod Name";
            String expectedDesc = "Localized Mod Description";

            mockedI18n.when(() -> I18n.format("mod.emptymod.name")).thenReturn(expectedName);
            mockedI18n.when(() -> I18n.format("mod.emptymod.description")).thenReturn(expectedDesc);

            EmptyMod.setLocaleMetadata(mockPreInitEvent);

            assertEquals(expectedName, mockModMetadata.name);
            assertEquals(expectedDesc, mockModMetadata.description);
        }
    }

    /**
     * Tests that {@link EmptyMod#preInit} logs an error when called from client side.
     */
    @Test
    public void emptyMod_preInit_ClientSideLogsError()
    {
        when(mockPreInitEvent.getSide()).thenReturn(Side.CLIENT);

        try (MockedStatic<I18n> mockedI18n = mockStatic(I18n.class))
        {
            mockedI18n.when(() -> I18n.format(anyString())).thenReturn("test");

            emptyMod.preInit(mockPreInitEvent);

            verify(mockLogger).error("No mixin config files found!");
        }
    }

    /**
     * Tests that {@link EmptyMod#preInit} logs correctly when called from server side.
     */
    @Test
    public void emptyMod_preInit_ServerSideLogsCorrect()
    {
        when(mockPreInitEvent.getSide()).thenReturn(Side.SERVER);

        emptyMod.preInit(mockPreInitEvent);

        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "preInit");
        verifyNoMoreInteractions(mockLogger);
    }

    /**
     * Tests that {@link EmptyMod#init} logs the correct method call.
     */
    @Test
    public void emptyMod_init_CallsCorrect()
    {
        emptyMod.init(mockInitEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "init");
    }

    /**
     * Tests that {@link EmptyMod#postInit} logs the correct method call.
     */
    @Test
    public void emptyMod_postInit_CallsCorrect()
    {
        emptyMod.postInit(mockPostInitEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "postInit");
    }

    /**
     * Tests that {@link EmptyMod#onLoadComplete} logs the correct method call.
     */
    @Test
    public void emptyMod_onLoadComplete_CallsCorrect()
    {
        emptyMod.onLoadComplete(mockLoadCompleteEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "onLoadComplete");
    }

    /**
     * Tests that {@link EmptyMod#serverLoad} logs the correct method call.
     */
    @Test
    public void emptyMod_serverLoad_CallsCorrect()
    {
        emptyMod.serverLoad(mockServerStartingEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "serverLoad");
    }

    /**
     * Tests that {@link EmptyMod#serverStopped} logs the correct method call.
     */
    @Test
    public void emptyMod_serverStopped_CallsCorrect()
    {
        emptyMod.serverStopped(mockServerStoppedEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "serverStopped");
    }
}
