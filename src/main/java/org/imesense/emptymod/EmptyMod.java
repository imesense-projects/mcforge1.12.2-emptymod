package org.imesense.emptymod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraft.client.resources.I18n;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Main class of modification
 */
@Mod(
    modid = EmptyMod.MODID,
    name = EmptyMod.NAME,
    version = EmptyMod.VERSION,
    dependencies = "required-after:fermiumbooter"
)
public final class EmptyMod
{
    /**
     * Modification ID
     */
    public static final String MODID = "emptymod";

    /**
     * Modification name
     */
    public static final String NAME = "Empty Mod";

    /**
     * Minecraft version
     */
    public static final String VERSION = "1.12.2-14.23.5.2860";

    /**
     * Logger object
     */
    private static final Logger LOGGER = LogManager.getLogger(EmptyMod.class);

    /**
     * Writes method call to log
     *
     * @param methodName Name of method
     */
    private void logMethodCall(String methodName)
    {
        LOGGER.info(
            "Called {}.{} method",
            this.getClass().getName(),
            methodName
        );
    }

    private static String getBufferedReader(InputStream stream) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder fileContent = new StringBuilder();

        String line;
        boolean isFirstLine = true;
        while ((line = reader.readLine()) != null)
        {
            if (!isFirstLine)
            {
                fileContent.append("\n");
            }
            else
            {
                isFirstLine = false;
            }
            fileContent.append(line);
        }

        reader.close();

        return fileContent.toString();
    }

    @SideOnly(Side.CLIENT)
    private static void setLocaleMetadata(FMLPreInitializationEvent event)
    {
        ModMetadata metadata = event.getModMetadata();
        metadata.name = I18n.format("mod." + MODID + ".name");
        metadata.description = I18n.format("mod." + MODID + ".description");
    }

    /**
     * Preinitialize modification
     *
     * @param event Preinitialization event
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logMethodCall(new Object(){}.getClass().getEnclosingMethod().getName());

        if (!event.getSide().isClient())
        {
            return;
        }

        setLocaleMetadata(event);

        try
        {
            String resourcePath = "";
            List<String> configFiles = new ArrayList<>();

            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath))
            {
                assert inputStream != null;

                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
                {
                    String resource;
                    while ((resource = bufferedReader.readLine()) != null)
                    {
                        if (resource.startsWith("mixin.") && resource.endsWith(".json"))
                        {
                            configFiles.add(resource);
                        }
                    }
                }
            }

            if (configFiles.isEmpty())
            {
                LOGGER.error("No mixin config files found!");
                return;
            }

            LOGGER.info("Found {} mixin config files:", configFiles.size());

            for (String configFile : configFiles)
            {
                try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile))
                {
                    assert inputStream != null;

                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
                    {
                        LOGGER.info("Loading mixin: {}", configFile);

                        String line;
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((line = bufferedReader.readLine()) != null)
                        {
                            stringBuilder.append(line).append("\n");
                        }

                        LOGGER.info("Contents of {}:\n{}", configFile, stringBuilder);
                        LOGGER.info("Successfully loaded: {}", configFile);
                    }
                }
                catch (Exception exception)
                {
                    LOGGER.error("Error loading {}: {}", configFile, exception.getMessage());
                }
            }
        }
        catch (Exception exception)
        {
            LOGGER.error("Common error: {}", exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * Initialize modification
     * 
     * @param event Initialization event
     */
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logMethodCall(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    /**
     * Postinitialize modification
     * 
     * @param event Postinitialization event
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        logMethodCall(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    /**
     * Load complete action
     * 
     * @param event Load complete event
     */
    @EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event)
    {
        logMethodCall(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    /**
     * Server load action
     * 
     * @param event Server starting event
     */
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        logMethodCall(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    /**
     * Server stopped action
     * 
     * @param event Server stopped action
     */
    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event)
    {
        logMethodCall(new Object(){}.getClass().getEnclosingMethod().getName());
    }
}
