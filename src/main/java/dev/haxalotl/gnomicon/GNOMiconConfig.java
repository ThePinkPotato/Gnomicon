package dev.haxalotl.gnomicon;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;

public class GNOMiconConfig {

    public String configString;
    public String windowName;
    public String iconName;

    private Path configPath = Path.of(FabricLoader.getInstance().getConfigDir() + "/gnomicon");
    private Path jsonPath = Path.of(configPath + "/gnomicon.json");
    private Path iconPath = Path.of(configPath + "/icon.png");


    public void createConfig() {
        try {
            if (Files.notExists(configPath)) {
                Files.createDirectory(configPath);
            }

            if (Files.notExists(jsonPath)) {
                Files.createFile(jsonPath);
                Files.writeString(jsonPath,
                        "{\n" +
                                "\"name\":\"Minecraft\",\n".indent(4) +
                                "\"icon\":\"icon.png\"\n".indent(4) +
                                "}"
                );
            }

            if (Files.notExists(iconPath)) {
                Files.copy(FabricLoader.getInstance().getModContainer("gnomicon").orElseThrow().findPath("assets/gnomicon/icon.png").orElseThrow(), iconPath);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readConfig() {

        try {
            configString = String.join(" ", Files.readAllLines(Path.of(FabricLoader.getInstance().getConfigDir() + "/gnomicon/gnomicon.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObject gnomiconConfig = JsonParser.parseString(configString).getAsJsonObject();
        System.out.println(gnomiconConfig.get("name").getAsString());
        windowName = gnomiconConfig.get("name").getAsString();
        iconName = gnomiconConfig.get("icon").getAsString();
    }

}
