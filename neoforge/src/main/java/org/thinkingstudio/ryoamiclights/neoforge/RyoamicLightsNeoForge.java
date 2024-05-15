/*
 * Copyright © 2020~2024 LambdAurora <email@lambdaurora.dev>
 * Copyright © 2024 ThinkingStudio
 *
 * This file is part of RyoamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.thinkingstudio.ryoamiclights.neoforge;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.SynchronousResourceReloader;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.thinkingstudio.ryoamiclights.RyoamicLights;
import net.neoforged.fml.common.Mod;
import org.thinkingstudio.ryoamiclights.api.item.ItemLightSources;
import org.thinkingstudio.ryoamiclights.gui.SettingsScreen;
import org.thinkingstudio.ryoamiclights.neoforge.api.DynamicLightsInitializerEvent;

@Mod(RyoamicLights.NAMESPACE)
public class RyoamicLightsNeoForge {
    public RyoamicLightsNeoForge(IEventBus modEventBus) {
        ModLoadingContext context = ModLoadingContext.get();

        if (FMLLoader.getDist().isClient()) {
            RyoamicLights.get().clientInit();

            context.registerExtensionPoint(IConfigScreenFactory.class, () -> (client, screen) -> new SettingsScreen(screen));

            modEventBus.addListener(EventPriority.HIGHEST, RenderLevelStageEvent.class, event -> {
                if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
                    MinecraftClient.getInstance().getProfiler().swap("dynamic_lighting");
                    RyoamicLights.get().updateAll(event.getLevelRenderer());
                }
            });
            modEventBus.addListener(EventPriority.HIGHEST, RegisterClientReloadListenersEvent.class, event -> {
                event.registerReloadListener((SynchronousResourceReloader) ItemLightSources::load);
            });

            NeoForge.EVENT_BUS.post(new DynamicLightsInitializerEvent());
        }
    }
}
