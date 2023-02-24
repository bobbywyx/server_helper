package dev.lige.fsh;

import com.google.gson.Gson;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHelper {
    public static MinecraftServer server;
    public static final Logger LOGGER = LoggerFactory.getLogger("modid");

    public static void onServerInit(MinecraftServer server){
        ServerHelper.server = server;

        // read config json

        // read data json

    }

    public static void onPlayerLoggedIn(ServerPlayerEntity player) {
        player.sendMessage(Text.of("Hello, " + player.getName().getString()),
                false);
        player.sendMessage(Text.of("This is"),
                false);
    }
    public static void onPlayerDead(ServerPlayerEntity player){
        BlockPos deathpoint = player.getBlockPos();

        player.sendMessage(Text.of("You just died at: [x:" +
                deathpoint.getX()+ "  y:" +
                deathpoint.getY()+ "  z:" +
                deathpoint.getZ()+ "]"
        ),false);
    }

    public static void log(){
        LOGGER.info("server_logger logging");

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
