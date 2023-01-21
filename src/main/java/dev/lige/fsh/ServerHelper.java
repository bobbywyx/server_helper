package dev.lige.fsh;

import com.google.gson.Gson;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ServerHelper {
    public static MinecraftServer minecraft_server;

    public static void onServerInit(MinecraftServer server){
        minecraft_server = server;

        // read config

        // read json

    }

    public static void onPlayerLoggedIn(ServerPlayerEntity player) {
        player.sendMessage(new LiteralText("Hello, " + player.getName().getString()), false);
    }
    public static void onPlayerDead(ServerPlayerEntity player){
        BlockPos deathpoint = player.getBlockPos();

        player.sendMessage(new LiteralText("you died at: [x:" +
                deathpoint.getX()+ "  y:" +
                deathpoint.getY()+ "  z:" +
                deathpoint.getZ()+ "]"
        ),false);
    }

    public static void log(){
        // Serialization
        Gson gson = new Gson();

        gson.toJson(1);            // ==> 1
        gson.toJson("abcd");       // ==> "abcd"
//        gson.toJson(new Long(10)); // ==> 10
        int[] values = { 1 };
        gson.toJson(values);       // ==> [1]

// Deserialization
        int i = gson.fromJson("1", int.class);
        Integer intObj = gson.fromJson("1", Integer.class);
        Long longObj = gson.fromJson("1", Long.class);
        Boolean boolObj = gson.fromJson("false", Boolean.class);
        String str = gson.fromJson("\"abc\"", String.class);
        String[] strArray = gson.fromJson("[\"abc\"]", String[].class);
    }


}
