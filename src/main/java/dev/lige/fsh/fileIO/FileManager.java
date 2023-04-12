package dev.lige.fsh.fileIO;

import dev.lige.fsh.data.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.google.gson.Gson;

public class FileManager {
    private static final Logger LOGGER = LoggerFactory.getLogger("server_helper");

    public static void testFolderExist(String path){
        Path path1 = Paths.get(path);
        if (!Files.exists(path1)) {
            LOGGER.info("server_helper folder not found, creating one");
            try {
                Files.createDirectory(path1);
            } catch (IOException e) {
                LOGGER.error("Create server_helper folder failed");
                e.printStackTrace();
            }
        }
    }

    public static void testFileExist(String path){
        Path path1 = Paths.get(path);
        if (!Files.exists(path1)) {
            LOGGER.info("file: [ " +  path + " ] not found, creating one");
            try {
                Files.createFile(path1);
            } catch (IOException e) {
                LOGGER.error("Create file: [ " +  path + " ] failed");
                e.printStackTrace();
            }
        }
    }

    public static Config loadConfig(){
        testFolderExist("./server_helper");
        testFileExist("./server_helper/config.json");
        testFileExist("./server_helper/data.json");

        Config config = new Config();
        Path config_path = Paths.get("./server_helper/config.json");
        // read config json
        try{
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(config_path);

            Map<?, ?> map = gson.fromJson(reader, Map.class);

            LOGGER.info("====Server Helper====");
            LOGGER.info("reading config.json");
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if ( entry.getKey().equals("server_name")){
                    config.server_name = (String) entry.getValue();
                    LOGGER.info("load config:  " + entry.getKey() + "=" + entry.getValue());
                }
                if(entry.getKey().equals("web_site")){
                    config.web_site = (String) entry.getValue();
                    LOGGER.info("load config:  " + entry.getKey() + "=" + entry.getValue());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Read config.json failed, will use default config");
            e.printStackTrace();
        }
        return config;
    }



}
