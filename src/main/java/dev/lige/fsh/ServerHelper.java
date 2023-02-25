package dev.lige.fsh;

import com.google.gson.Gson;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ServerHelper {
    public static MinecraftServer server;
    public static server_helper_config config;
    public static final Logger LOGGER = LoggerFactory.getLogger("modid");
    public static void onServerInit(MinecraftServer server){
        ServerHelper.server = server;
        ServerHelper.config = new server_helper_config();

        Path dir = Paths.get("./server_helper");
        Path config_path = Paths.get("./server_helper/config.json");
        Path data_path = Paths.get("./server_helper/data.json");

        if (!Files.exists(dir)) {
            LOGGER.info("server_helper folder not found, creating one");
            try {
                Files.createDirectory(dir);
                Files.createFile(config_path);
                Files.createFile(data_path);
            } catch (IOException e) {
                LOGGER.error("Create server_helper folder failed");
                e.printStackTrace();
            }
        }
        // read config json
        try{
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(config_path);

            Map<?, ?> map = gson.fromJson(reader, Map.class);

            LOGGER.info("reading config.json");
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                LOGGER.info("load config:  " + entry.getKey() + "=" + entry.getValue());
                if ( entry.getKey() == "server_name" ){
                    config.server_name = (String) entry.getValue();
                }
                if(entry.getKey() == "web_site"){
                    config.web_site = (String) entry.getValue();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Read config.json failed, will use default config");
        }
        // read data json


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

class server_helper_config{
    public String server_name = "my server";
    public String web_site = "https://github.com/bobbywyx/server_helper";
}