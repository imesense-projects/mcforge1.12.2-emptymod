package org.imesense.emptymod;

import net.minecraft.init.Blocks;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;

import org.apache.logging.log4j.Logger;

/**
 * Main class of modification
 */
@Mod(
    modid = EmptyMod.MODID,
    name = EmptyMod.NAME,
    version = EmptyMod.VERSION
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
    private static Logger logger;

    /**
     * Preinitialize modification
     * 
     * @param event Preinitialization event
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        logger.info(
            "Called {}.{} method",
            new Object(){}.getClass().getName(),
            new Object(){}.getClass().getEnclosingMethod().getName()
        );
    }

    /**
     * Initialize modification
     * 
     * @param event Initialization event
     */
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info(
            "Called {}.{} method",
            new Object(){}.getClass().getName(),
            new Object(){}.getClass().getEnclosingMethod().getName()
        );
    }

    /**
     * Postinitialize modification
     * 
     * @param event Postinitialization event
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        logger.info(
            "Called {}.{} method",
            new Object(){}.getClass().getName(),
            new Object(){}.getClass().getEnclosingMethod().getName()
        );
    }

    /**
     * Load complete action
     * 
     * @param event Load complete event
     */
    @EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event)
    {
        logger.info(
            "Called {}.{} method",
            new Object(){}.getClass().getName(),
            new Object(){}.getClass().getEnclosingMethod().getName()
        );
    }

    /**
     * Server load action
     * 
     * @param event Server starting event
     */
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        logger.info(
            "Called {}.{} method",
            new Object(){}.getClass().getName(),
            new Object(){}.getClass().getEnclosingMethod().getName()
        );
    }

    /**
     * Server stopped action
     * 
     * @param event Server stopped action
     */
    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event)
    {
        logger.info(
            "Called {}.{} method",
            new Object(){}.getClass().getName(),
            new Object(){}.getClass().getEnclosingMethod().getName()
        );
    }
}
