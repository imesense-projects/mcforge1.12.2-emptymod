package org.imesense.boilerplate.mcforge.mixin;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Utility class containing logic for mixins targeting {@link EntityPlayer}.
 * <p>
 * Provides static helper methods for player-related mixin operations.
 *
 * @see EntityPlayer
 */
public class MixinEntityPlayerLogic
{
    /**
     * Handles player update events.
     *
     * @param player the player entity being updated
     *
     * @see EntityPlayer#onUpdate()
     */
    public static void onPlayerUpdate(EntityPlayer player)
    {
        System.out.println("Player " + player.getName() + " updated!");
    }
}
