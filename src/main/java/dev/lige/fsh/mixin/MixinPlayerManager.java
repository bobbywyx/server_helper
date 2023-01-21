package dev.lige.fsh.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.lige.fsh.ServerHelper;


@Mixin(PlayerManager.class)
public class MixinPlayerManager {
    // welcome message
    @Inject(method = "onPlayerConnect", at = @At("RETURN"))
    private void sendMOTD(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        ServerHelper.onPlayerLoggedIn(player);
    }
    // Respawn and get last death
    @Inject(method = "respawnPlayer", at = @At("HEAD"))
    private void sendLastDeathInfo(ServerPlayerEntity player, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> cir) {
        ServerHelper.onPlayerDead(player);
    }
}
