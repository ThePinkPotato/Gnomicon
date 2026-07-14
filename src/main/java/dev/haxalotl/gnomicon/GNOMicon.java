package dev.haxalotl.gnomicon;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Files;
import java.nio.file.Path;

public class GNOMicon implements ModInitializer {
	public static final String MOD_ID = "gnomicon";
	public static final GNOMiconConfig config = new GNOMiconConfig();
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

        LOGGER.info("GNOME is gonna give me an aneurysm");
		config.createConfig();
		config.readConfig();

        try {
			Process process = new ProcessBuilder("gnome-shell", "--version").start();
			String getGnome = new String(process.getInputStream().readAllBytes()).trim();
			process.waitFor();
			Path desktopPath = Path.of(System.getProperty("user.home") + "/.local/share/applications/" + "gnomicon-" + config.windowName + ".desktop");

			if (getGnome.toUpperCase().contains("GNOME") && Files.notExists(desktopPath)) {
				System.out.println("GNOME version: " + getGnome);

				Files.createFile(desktopPath);
				Files.writeString(desktopPath,
						"#!/usr/bin/env xdg-open\n\n" +
						"[Desktop Entry]\n" +
						"Type=Application\n" +
						"Icon=" + FabricLoader.getInstance().getConfigDir().toString() + "/gnomicon/" + config.iconName + "\n" +
						"StartupWMClass=" + config.windowName
				);
				LOGGER.info("Desktop file has been created");
			} else {
				LOGGER.warn("Your computer is probably using KDE");
			}
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
