package org.imesense.boilerplate.mcforge;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;

/**
 * Main class of modification
 */
@Mod(
    modid = BoilerplateMcforge.MODID,
    name = BoilerplateMcforge.NAME,
    version = BoilerplateMcforge.VERSION
)
public final class BoilerplateMcforge
{
    /**
     * Modification ID
     */
    public static final String MODID = "boilerplatemcforge";

    /**
     * Modification name
     */
    public static final String NAME = "Minecraft Forge Modification Boilerplate";

    /**
     * Minecraft version
     */
    public static final String VERSION = "1.12.2-14.23.5.2860";

    /**
     * Logger object
     */
    public static Logger Logger;

    /**
     * Writes method call to log
     *
     * @param methodName Name of method
     */
    private void logMethodCall(String methodName)
    {
        Logger.info(
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

    /**
     * Preinitialize modification
     *
     * @param event Preinitialization event
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Logger = event.getModLog();
        logMethodCall(new Object(){}.getClass().getEnclosingMethod().getName());

        try
        {
            String filename = "mixins.boilerplatemcforge.json";
            InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
            if (stream != null)
            {
                Logger.info("Mixins config {} loaded", filename);

                String content = getBufferedReader(stream);
                Logger.info("File content: {}", content);
                Logger.info("Mixins loaded successfully");
            }
            else
            {
                Logger.error("File {} not found", filename);
            }
        }
        catch (Exception exception)
        {
            Logger.error("Mixin loading error: {}", exception.getMessage());
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
