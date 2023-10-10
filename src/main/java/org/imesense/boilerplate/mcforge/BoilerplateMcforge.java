package org.imesense.boilerplate.mcforge;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = BoilerplateMcforge.MODID,
    name = BoilerplateMcforge.NAME,
    version = BoilerplateMcforge.VERSION
)
public class BoilerplateMcforge
{
    public static final String MODID = "boilerplatemcforge";
    public static final String NAME = "Minecraft Forge Modification Boilerplate";
    public static final String VERSION = "1.12.2-14.23.5.2859";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
