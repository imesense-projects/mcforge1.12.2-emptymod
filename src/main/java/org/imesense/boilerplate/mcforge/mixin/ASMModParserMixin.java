package org.imesense.boilerplate.mcforge.mixin;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.discovery.asm.ASMModParser;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ASMModParser.class)
public class ASMModParserMixin
{
    @Redirect(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Throwable;)V"
        )
    )
    private void suppressClassReadError(Logger logger, String message, Throwable throwable)
    {
        // ничего не делаем
    }
}
