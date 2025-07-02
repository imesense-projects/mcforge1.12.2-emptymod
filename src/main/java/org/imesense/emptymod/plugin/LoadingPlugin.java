package org.imesense.emptymod.plugin;

import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import org.spongepowered.asm.launch.MixinBootstrap;

import fermiumbooter.FermiumRegistryAPI;

/**
 * Core loading plugin for the EmptyMod that handles early initialization of Mixin framework.
 * <p>
 * Implements {@link IFMLLoadingPlugin} to integrate with Forge's mod loading system.
 *
 * @see IFMLLoadingPlugin
 * @see MixinBootstrap
 */
@IFMLLoadingPlugin.MCVersion("1.12.2")
public final class LoadingPlugin implements IFMLLoadingPlugin
{
    /**
     * Logger instance for {@link LoadingPlugin}
     *
     * @see Logger
     * @see LogManager
     */
    static Logger logger = LogManager.getLogger(LoadingPlugin.class);

    /**
     * Indicating whether the plugin is running in test mode.
     * <p>
     * When enabled, skips mixin initialization during construction.
     *
     * @see #enableTestMode()
     */
    static boolean testMode = false;

    /**
     * Enables test mode for the loading plugin.
     *
     * @see #testMode
     */
    public static void enableTestMode()
    {
        testMode = true;
    }

    /**
     * Constructs the loading plugin and initializes Mixin framework.
     * <p>
     * Performs the following initialization sequence:
     * <ol>
     *     <li>Bootstraps the mixin environment</li>
     *     <li>Registers the mod's mixin configuration through {@link FermiumRegistryAPI}</li>
     * </ol>
     *
     * @throws RuntimeException if Mixin initialization fails
     *
     * @see MixinBootstrap
     * @see FermiumRegistryAPI
     */
    public LoadingPlugin()
    {
        logger.info("Initializing LoadingPlugin");

        if (testMode)
        {
            return;
        }

        try
        {
            logger.debug("Initializing Mixin");

            MixinBootstrap.init();
            FermiumRegistryAPI.enqueueMixin(false, "mixins.emptymod.json");

            logger.info("Mixin initialization complete");
        }
        catch (Exception exception)
        {
            logger.error("Failed to initialize Mixin", exception);
            throw exception;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return Empty array as no ASM transformers are used
     */
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }

    /**
     * {@inheritDoc}
     *
     * @return null as no mod container class is specified
     */
    @Override
    public String getModContainerClass()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @return null as no setup class is specified
     */
    @Override
    public String getSetupClass()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param objectMap Data provided by FML during loading phase
     */
    @Override
    public void injectData(Map<String, Object> objectMap)
    {
    }

    /**
     * {@inheritDoc}
     *
     * @return null as no access transformer class is specified
     */
    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
