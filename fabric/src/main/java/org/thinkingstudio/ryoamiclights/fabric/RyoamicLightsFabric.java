package org.thinkingstudio.ryoamiclights.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import org.thinkingstudio.ryoamiclights.RyoamicLights;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import org.thinkingstudio.ryoamiclights.api.DynamicLightsInitializer;

public class RyoamicLightsFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new RyoamicLights().clientInit();

        FabricLoader.getInstance().getEntrypointContainers("dynamiclights", DynamicLightsInitializer.class)
                .stream().map(EntrypointContainer::getEntrypoint)
                .forEach(DynamicLightsInitializer::onInitializeDynamicLights);

        WorldRenderEvents.START.register(context -> {
            MinecraftClient.getInstance().getProfiler().swap("dynamic_lighting");
            RyoamicLights.get().updateAll(context.worldRenderer());
        });
    }
}
