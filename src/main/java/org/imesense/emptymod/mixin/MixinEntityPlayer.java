package org.imesense.emptymod.mixin;

import net.minecraft.entity.player.EntityPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@link EntityPlayer} class.
 * <p>
 * Adds additional logic during player state updates (when {@code onUpdate} is called).
 *
 * @see Mixin
 * @see EntityPlayer
 */
@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer
{
    /**
     * Intercepts the {@link EntityPlayer#onUpdate()} method call to perform additional actions.
     * <p>
     * Current implementation prints a player update notification to console.
     *
     * @param callbackInfo Callback information for controlling Mixin injection
     *
     * @see Inject
     * @see CallbackInfo
     */
    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void onPlayerUpdate(CallbackInfo callbackInfo)
    {
        EntityPlayer player = (EntityPlayer) (Object) this;
        System.out.println("Player " + player.getName() + " is updating!");
    }
}
