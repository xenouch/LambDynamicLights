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

import net.neoforged.fml.loading.FMLLoader;
import org.thinkingstudio.ryoamiclights.RyoamicLights;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.thinkingstudio.ryoamiclights.api.DynamicLightHandlers;
import org.thinkingstudio.ryoamiclights.neoforge.api.DynamicLightsInitializerEvent;

@Mod(RyoamicLights.NAMESPACE)
public class RyoamicLightsNeoForge {
    public RyoamicLightsNeoForge() {
        if (FMLLoader.getDist().isClient()) {
            this.onInitializeClient();
        }
    }

    public void onInitializeClient() {
        new RyoamicLights().clientInit();

        /*
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, RenderLevelStageEvent.class, event -> {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
                MinecraftClient.getInstance().getProfiler().swap("dynamic_lighting");
                RyoamicLights.get().updateAll(event.getLevelRenderer());
            }
        });
         */
        NeoForge.EVENT_BUS.addListener(DynamicLightsInitializerEvent.class, event -> {
            DynamicLightHandlers.registerDefaultHandlers();
        });
    }
}
