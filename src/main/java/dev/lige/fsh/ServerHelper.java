package dev.lige.fsh;

import com.google.gson.Gson;
import com.mojang.brigadier.CommandDispatcher;
import dev.lige.fsh.commands.InfoCommand;
import dev.lige.fsh.data.Config;
import dev.lige.fsh.fileIO.FileManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerHelper implements ModInitializer {
    public static MinecraftServer server;
    public static Config config;
    private static final Logger LOGGER = LoggerFactory.getLogger("server_helper");

    @Override
    public void onInitialize() {
        LOGGER.info("server_helper initializing");
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> commandsRegister(dispatcher));
    }
    private void commandsRegister(CommandDispatcher<ServerCommandSource> dispatcher) {
        InfoCommand.register(dispatcher);
    }


    public static void onServerInit(MinecraftServer server){
        ServerHelper.server = server;
        ServerHelper.config = FileManager.loadConfig();
    }

    public static void onPlayerLoggedIn(ServerPlayerEntity player) {
        player.sendMessage(Text.of("Hello, " + player.getName().getString()),
                false);
        player.sendMessage(Text.of("This is " + config.server_name),
                false);
    }
    public static void onPlayerDead(ServerPlayerEntity player){
        BlockPos death_point = player.getBlockPos();

        player.sendMessage(Text.of("You died at: " +
                player.getWorld().getRegistryKey().getValue().toString() +
                "  [x:" +
                death_point.getX()+ "  y:" +
                death_point.getY()+ "  z:" +
                death_point.getZ()+ "]"
        ),false);
    }

    public static void log(){
        LOGGER.info("server_helper logging");

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

