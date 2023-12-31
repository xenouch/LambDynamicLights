package org.thinkingstudio.ryoamiclights.neoforge;

import org.thinkingstudio.ryoamiclights.RyoamicLights;
import net.minecraft.client.MinecraftClient;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(RyoamicLights.NAMESPACE)
public class RyoamicLightsNeoForge {
    public RyoamicLightsNeoForge() {
        RyoamicLights.get().onInitializeClient();

        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, RenderLevelStageEvent.class, event -> {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
                MinecraftClient.getInstance().getProfiler().swap("dynamic_lighting");
                RyoamicLights.get().updateAll(event.getLevelRenderer());
            }
        });
    }
}
