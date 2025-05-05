package org.imesense.emptymod.mixin;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft
{
    @Inject(method = "run", at = @At("HEAD"))
    private void onRun(CallbackInfo callbackInfo)
    {
        System.out.println(
            "Called " +
            this.getClass().getName() +
            "." +
            new Object(){}.getClass().getEnclosingMethod().getName() +
            "method"
        );
    }
}
