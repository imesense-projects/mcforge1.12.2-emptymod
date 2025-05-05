package org.imesense.emptymod.plugin;

import java.io.File;
import java.util.Map;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import org.imesense.emptymod.EmptyMod;
import org.imesense.emptymod.transformer.ClassTransformer;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.Name(EmptyMod.MODID)
@IFMLLoadingPlugin.SortingIndex(LoadingPlugin.AFTER_DEOBFUSCATION)
public final class LoadingPlugin implements IFMLLoadingPlugin
{
    public static File FileLocation;
    public static Boolean RuntimeDeobfuscation;

    public static final int AFTER_DEOBFUSCATION = 1001;

    public LoadingPlugin()
    {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.emptymod.json");
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[]
        {
            ClassTransformer.class.getName()
        };
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
        RuntimeDeobfuscation = (Boolean) objectMap.get("runtimeDeobfuscationEnabled");
        FileLocation = (File) objectMap.get("coremodLocation");
        if (FileLocation == null)
        {
            FileLocation = new File(
                getClass()
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath());
        }
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
