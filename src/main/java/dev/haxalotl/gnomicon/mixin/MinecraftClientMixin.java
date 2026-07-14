package dev.haxalotl.gnomicon.mixin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import dev.haxalotl.gnomicon.GNOMiconConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.nio.file.Files;
import java.nio.file.Path;

@Mixin(value = MinecraftClient.class, priority = Integer.MAX_VALUE)
public class MinecraftClientMixin {
    @ModifyReturnValue(method="getWindowTitle", at = @At("RETURN"))
    private String gnomicon$getWindowTitle(String original) {
        return System.getProperty("title") != null ? System.getProperty("title") : "Minecraft";
    }


}
