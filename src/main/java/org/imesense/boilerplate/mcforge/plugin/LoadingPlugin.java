package org.imesense.boilerplate.mcforge.plugin;

import java.io.File;
import java.util.Map;

import org.slf4j.LoggerFactory;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import org.imesense.boilerplate.mcforge.BoilerplateMcforge;
import org.imesense.boilerplate.mcforge.transformer.ClassTransformer;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.Name(BoilerplateMcforge.MODID)
@IFMLLoadingPlugin.SortingIndex(LoadingPlugin.AFTER_DEOBFUSCATION)
public final class LoadingPlugin implements IFMLLoadingPlugin
{
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LoadingPlugin.class);

    public static File FileLocation;
    public static Boolean RuntimeDeobfuscation;

    public static final int AFTER_DEOBFUSCATION = 1001;

    public LoadingPlugin()
    {
        LOGGER.info("Initializing LoadingPlugin");

        try
        {
            LOGGER.debug("Initializing Mixin");

            MixinBootstrap.init();
            Mixins.addConfiguration("mixins.boilerplatemcforge.json");

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
        LOGGER.debug("Providing ASM transformer classes");

        return new String[]
        {
            ClassTransformer.class.getName()
        };
    }

    @Override
    public String getModContainerClass()
    {
        LOGGER.trace("getModContainerClass() called - returning null");

        return null;
    }

    @Override
    public String getSetupClass()
    {
        LOGGER.trace("getSetupClass() called - returning null");

        return null;
    }

    @Override
    public void injectData(Map<String, Object> objectMap)
    {
        LOGGER.debug("Injecting coremod data");

        try
        {
            RuntimeDeobfuscation = (Boolean) objectMap.get("runtimeDeobfuscationEnabled");
            LOGGER.debug("Runtime deobfuscation enabled: {}", RuntimeDeobfuscation);

            FileLocation = (File) objectMap.get("coremodLocation");
            if (FileLocation == null)
            {
                LOGGER.debug("coremodLocation not provided, determining from protection domain");

                FileLocation = new File(
                    getClass()
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .getPath());
            }
            LOGGER.debug("Coremod location: {}", FileLocation);
        }
        catch (Exception exception)
        {
            LOGGER.error("Error during data injection", exception);

            throw exception;
        }
    }

    @Override
    public String getAccessTransformerClass()
    {
        LOGGER.trace("getAccessTransformerClass() called - returning null");

        return null;
    }
}
