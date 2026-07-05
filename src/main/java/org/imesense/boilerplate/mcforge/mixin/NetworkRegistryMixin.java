package org.imesense.boilerplate.mcforge.mixin;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin for {@link NetworkRegistry} class.
 * <p>
 * Fixes {@link Side} enumeration used during network channel registration.
 * Default {@link Side#values()} array contains a stale, forgotten Bukkit-related
 * variant that breaks the game launch when running under a debugger. This mixin
 * redirects the call to return only the valid sides ({@link Side#CLIENT} and
 * {@link Side#SERVER}), ensuring correct behavior.
 *
 * @see Mixin
 * @see NetworkRegistry
 * @see Side
 */
@Mixin(NetworkRegistry.class)
public abstract class NetworkRegistryMixin
{
    /**
     * Redirects {@link Side#values()} call within {@code newChannel} methods
     * to return a corrected array of sides.
     * <p>
     * Original call returns an array that includes an invalid, leftover Bukkit
     * variant of {@link Side}, which causes the game to crash when launched under
     * a debugger. This method returns only {@link Side#CLIENT} and
     * {@link Side#SERVER}, effectively removing the broken entry.
     *
     * @return a corrected array containing only {@link Side#CLIENT} and
     * {@link Side#SERVER}
     *
     * @see Redirect
     * @see Side
     */
    @Redirect(
        method = {
            "newChannel(Ljava/lang/String;[Lio/netty/channel/ChannelHandler;)Ljava/util/EnumMap;",
            "newChannel(Lnet/minecraftforge/fml/common/ModContainer;Ljava/lang/String;[Lio/netty/channel/ChannelHandler;)Ljava/util/EnumMap;"
        },
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/fml/relauncher/Side;values()[Lnet/minecraftforge/fml/relauncher/Side;"
        ),
        remap = false
    )
    private Side[] removeBukkit()
    {
        return new Side[] {
            Side.CLIENT,
            Side.SERVER
        };
    }
}
