package org.imesense.emptymod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
 * Main class for EmptyMod modification
 * <p>
 * This class serves as the primary entry point for the mod and handles all major
 * lifecycle events in the Forge mod loading process. It includes functionality for:
 * <ul>
 *     <li>Mixin configuration loading</li>
 *     <li>Localization support</li>
 *     <li>Standard Forge mod lifecycle management</li>
 * </ul>
 *
 * {@code @Mod} annotation configures the basic mod metadata and dependencies.
 *
 * @see Mod
 */
@Mod(
    modid = EmptyMod.MOD_ID,
    name = EmptyMod.NAME,
    version = EmptyMod.VERSION,
    dependencies = "required-after:fermiumbooter"
)
public final class EmptyMod
{
    /**
     * Modification unique identifier
     */
    public static final String MOD_ID = "emptymod";

    /**
     * Modification name
     */
    public static final String NAME = "Empty Mod";

    /**
     * Minecraft version
     */
    public static final String VERSION = "1.12.2-14.23.5.2864";

    /**
     * Logger instance for {@link EmptyMod}
     *
     * @see Logger
     * @see LogManager
     */
    static Logger logger = LogManager.getLogger(EmptyMod.class);

    /**
     * Logs a method call to the logger.
     *
     * @param methodName the name of the method being called
     */
    private void logMethodCall(String methodName)
    {
        logger.info(
            "Called {}.{} method",
            this.getClass().getName(),
            methodName
        );
    }

    /**
     * Sets the localized metadata for the mod (client-side only).
     *
     * @param event pre-initialization event containing mod metadata
     *
     * @see FMLPreInitializationEvent
     * @see ModMetadata
     * @see I18n
     */
    @SideOnly(Side.CLIENT)
    static void setLocaleMetadata(FMLPreInitializationEvent event)
    {
        ModMetadata metadata = event.getModMetadata();
        metadata.name = I18n.format("mod." + MOD_ID + ".name");
        metadata.description = I18n.format("mod." + MOD_ID + ".description");
    }

    /**
     * Handles the mod pre-initialization phase.
     * <p>
     * This method:
     * <ul>
     *   <li>Sets up localization (client-side only)</li>
     *   <li>Loads and validates Mixin configuration files</li>
     *   <li>Logs all Mixin configurations found</li>
     * </ul>
     *
     * @param event the pre-initialization event
     *
     * @see FMLPreInitializationEvent
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
                logger.error("No mixin config files found!");
                return;
            }

            logger.info("Found {} mixin config files:", configFiles.size());

            for (String configFile : configFiles)
            {
                try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile))
                {
                    assert inputStream != null;

                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
                    {
                        logger.info("Loading mixin: {}", configFile);

                        String line;
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((line = bufferedReader.readLine()) != null)
                        {
                            stringBuilder.append(line).append("\n");
                        }

                        logger.info("Contents of {}:\n{}", configFile, stringBuilder);
                        logger.info("Successfully loaded: {}", configFile);
                    }
                }
                catch (Exception exception)
                {
                    logger.error("Error loading {}: {}", configFile, exception.getMessage());
                }
            }
        }
        catch (Exception exception)
        {
            logger.error("Common error: {}", exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * Handles the mod initialization phase.
     *
     * @param event the initialization event
     *
     * @see FMLInitializationEvent
     */
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logMethodCall(
            new Object(){}
                .getClass()
                .getEnclosingMethod()
                .getName()
        );
    }

    /**
     * Handles the mod post-initialization phase.
     *
     * @param event the post-initialization event
     *
     * @see FMLPostInitializationEvent
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        logMethodCall(
            new Object(){}
                .getClass()
                .getEnclosingMethod()
                .getName()
        );
    }

    /**
     * Handles the load complete event.
     *
     * @param event the load complete event
     *
     * @see FMLLoadCompleteEvent
     */
    @EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event)
    {
        logMethodCall(
            new Object(){}
                .getClass()
                .getEnclosingMethod()
                .getName()
        );
    }

    /**
     * Handles server starting event.
     *
     * @param event the server starting event
     *
     * @see FMLServerStartingEvent
     */
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        logMethodCall(
            new Object(){}
                .getClass()
                .getEnclosingMethod()
                .getName()
        );
    }

    /**
     * Handles server stopped event.
     *
     * @param event the server stopped event
     *
     * @see FMLServerStoppedEvent
     */
    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event)
    {
        logMethodCall(
            new Object(){}
                .getClass()
                .getEnclosingMethod()
                .getName()
        );
    }
}
