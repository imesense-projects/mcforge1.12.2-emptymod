package org.imesense.boilerplate.mcforge.plugin;

import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import org.spongepowered.asm.launch.MixinBootstrap;

import fermiumbooter.FermiumRegistryAPI;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public final class LoadingPlugin implements IFMLLoadingPlugin
{
    private static final Logger LOGGER = LogManager.getLogger(LoadingPlugin.class);

    public LoadingPlugin()
    {
        LOGGER.info("Initializing LoadingPlugin");

        try
        {
            LOGGER.debug("Initializing Mixin");

            MixinBootstrap.init();
            FermiumRegistryAPI.enqueueMixin(false, "mixins.boilerplatemcforge.json");

            LOGGER.info("Mixin initialization complete");
        }
        catch (Exception exception)
        {
            LOGGER.error("Failed to initialize Mixin", exception);
            throw exception;
        }
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> objectMap)
    {
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
