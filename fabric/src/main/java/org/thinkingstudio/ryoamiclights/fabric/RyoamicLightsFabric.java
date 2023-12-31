package org.thinkingstudio.ryoamiclights.fabric;

import org.thinkingstudio.ryoamiclights.RyoamicLights;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;

public class RyoamicLightsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        RyoamicLights.get().onInitializeClient();

        WorldRenderEvents.START.register(context -> {
            MinecraftClient.getInstance().getProfiler().swap("dynamic_lighting");
            RyoamicLights.get().updateAll(context.worldRenderer());
        });
    }
}
