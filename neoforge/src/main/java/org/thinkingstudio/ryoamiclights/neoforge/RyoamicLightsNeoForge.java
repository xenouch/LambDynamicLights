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
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloader;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.profiler.Profiler;
import net.neoforged.api.distmarker.Dist;
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
import org.thinkingstudio.ryoamiclights.pride.PrideFlag;
import org.thinkingstudio.ryoamiclights.pride.PrideFlags;
import org.thinkingstudio.ryoamiclights.pride.PrideLoader;

import java.util.List;

@Mod(value = RyoamicLights.NAMESPACE, dist = Dist.CLIENT)
public class RyoamicLightsNeoForge {
    public RyoamicLightsNeoForge(IEventBus modEventBus) {
        IEventBus forgeEventBus = NeoForge.EVENT_BUS;
        ModLoadingContext context = ModLoadingContext.get();

        if (FMLLoader.getDist().isClient()) {
            RyoamicLights.get().clientInit();

            context.registerExtensionPoint(IConfigScreenFactory.class, () -> (client, screen) -> new SettingsScreen(screen));

            forgeEventBus.addListener(EventPriority.HIGHEST, RenderLevelStageEvent.class, event -> {
                if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
                    MinecraftClient.getInstance().getProfiler().swap("dynamic_lighting");
                    RyoamicLights.get().updateAll(event.getLevelRenderer());
                }
            });
            modEventBus.addListener(EventPriority.HIGHEST, RegisterClientReloadListenersEvent.class, event -> {
                event.registerReloadListener((SynchronousResourceReloader) ItemLightSources::load);
                event.registerReloadListener(new SinglePreparationResourceReloader<List<PrideFlag>>() {
                    @Override
                    protected List<PrideFlag> prepare(ResourceManager manager, Profiler profiler) {
                        return PrideLoader.loadFlags(manager);
                    }

                    @Override
                    protected void apply(List<PrideFlag> flags, ResourceManager manager, Profiler profiler) {
                        PrideFlags.setFlags(flags);
                    }
                });
            });
        }
    }
}
