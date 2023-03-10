package dev.lige.fsh.mixin;

import dev.lige.fsh.ServerHelper;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @Inject(method = "loadWorld", at = @At("HEAD"))
    private void serverLoaded(CallbackInfo ci)
    {
        ServerHelper.onServerInit((MinecraftServer) (Object) this);
    }
}
