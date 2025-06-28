package org.imesense.boilerplate.mcforge.mixin;

import net.minecraft.entity.player.EntityPlayer;

public class MixinEntityPlayerLogic
{
    public static void onPlayerUpdate(EntityPlayer player)
    {
        System.out.println("Player " + player.getName() + " updated!");
    }
}
