package org.thinkingstudio.ryoamiclights.fabric;

import org.thinkingstudio.ryoamiclights.RyoamicLightsCompat;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class RyoamicLightsFabricMixinPlugin  implements IMixinConfigPlugin {
    private final Object2BooleanMap<String> conditionalMixins = new Object2BooleanOpenHashMap<>();

    public RyoamicLightsFabricMixinPlugin() {
        boolean sodiumInstalled = RyoamicLightsCompat.isSodiumInstalled();
        this.conditionalMixins.put("sodium.mixin.org.thinkingstudio.ryoamiclights.fabric.ArrayLightDataCacheMixin", sodiumInstalled);
        this.conditionalMixins.put("sodium.mixin.org.thinkingstudio.ryoamiclights.fabric.FlatLightPipelineMixin", sodiumInstalled);
        this.conditionalMixins.put("sodium.mixin.org.thinkingstudio.ryoamiclights.fabric.LightDataAccessMixin", sodiumInstalled);

        boolean fabricApiInstalled = RyoamicLightsCompat.isFabricApiInstalled();
        this.conditionalMixins.put("fabricapi.mixin.org.thinkingstudio.ryoamiclights.fabric.AoCalculatorMixin", fabricApiInstalled);
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return this.conditionalMixins.getOrDefault(mixinClassName, true);
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
