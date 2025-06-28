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

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BoilerplateMcforgeTest
{
    private static final String CLASS_NAME = "org.imesense.boilerplate.mcforge.BoilerplateMcforge";
    private static final String LOG_MESSAGE = "Called {}.{} method";

    @Mock
    private Logger mockLogger;

    @Mock
    private FMLPreInitializationEvent mockPreInitEvent;

    @Mock
    private FMLInitializationEvent mockInitEvent;

    @Mock
    private FMLPostInitializationEvent mockPostInitEvent;

    @Mock
    private FMLLoadCompleteEvent mockLoadCompleteEvent;

    @Mock
    private FMLServerStartingEvent mockServerStartingEvent;

    @Mock
    private FMLServerStoppedEvent mockServerStoppedEvent;

    @Mock
    private ModMetadata mockModMetadata;

    private BoilerplateMcforge boilerplateMcforge;

    @BeforeEach
    public void setUp()
    {
        boilerplateMcforge = new BoilerplateMcforge();

        try
        {
            Field field = BoilerplateMcforge.class.getDeclaredField("LOGGER");
            field.setAccessible(true);
            field.set(null, mockLogger);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        when(mockPreInitEvent.getModMetadata()).thenReturn(mockModMetadata);
    }

    @Test
    public void testClassConstants_SetsCorrect()
    {
        assertEquals("boilerplatemcforge", BoilerplateMcforge.MODID);
        assertEquals("Minecraft Forge Modification Boilerplate", BoilerplateMcforge.NAME);
        assertEquals("1.12.2-14.23.5.2860", BoilerplateMcforge.VERSION);
    }

    @Test
    public void testPreInit_ClientSideLogsError()
    {
        when(mockPreInitEvent.getSide()).thenReturn(Side.CLIENT);

        try (MockedStatic<I18n> mockedI18n = mockStatic(I18n.class))
        {
            mockedI18n.when(() -> I18n.format(anyString())).thenReturn("test");

            boilerplateMcforge.preInit(mockPreInitEvent);

            verify(mockLogger).error("No mixin config files found!");
        }
    }

    @Test
    public void testPreInit_ServerSideLogsCorrect()
    {
        when(mockPreInitEvent.getSide()).thenReturn(Side.SERVER);

        boilerplateMcforge.preInit(mockPreInitEvent);

        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "preInit");
        verifyNoMoreInteractions(mockLogger);
    }

    @Test
    public void testSetLocaleMetadata_SetsCorrect()
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

    @Test
    public void testInit_CallsCorrect()
    {
        boilerplateMcforge.init(mockInitEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "init");
    }

    @Test
    public void testPostInit_CallsCorrect()
    {
        boilerplateMcforge.postInit(mockPostInitEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "postInit");
    }

    @Test
    public void testOnLoadComplete_CallsCorrect()
    {
        boilerplateMcforge.onLoadComplete(mockLoadCompleteEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "onLoadComplete");
    }

    @Test
    public void testServerLoad_CallsCorrect()
    {
        boilerplateMcforge.serverLoad(mockServerStartingEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "serverLoad");
    }

    @Test
    public void testServerStopped_CallsCorrect()
    {
        boilerplateMcforge.serverStopped(mockServerStoppedEvent);
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "serverStopped");
    }

    @Test
    public void testLogMethodCall_LogsCorrect() throws Exception
    {
        Method method = BoilerplateMcforge.class.getDeclaredMethod("logMethodCall", String.class);
        method.setAccessible(true);
        method.invoke(boilerplateMcforge, "testMethod");
        verify(mockLogger).info(LOG_MESSAGE, CLASS_NAME, "testMethod");
    }
}
