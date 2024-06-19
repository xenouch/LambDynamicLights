package org.thinkingstudio.ryoamiclights.mixin.lightsource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.BlockAttachedEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.thinkingstudio.ryoamiclights.DynamicLightSource;
import org.thinkingstudio.ryoamiclights.RyoamicLights;
import org.thinkingstudio.ryoamiclights.api.DynamicLightHandlers;

@Mixin(BlockAttachedEntity.class)
public abstract class BlockAttachedEntityMixin extends Entity implements DynamicLightSource {
    public BlockAttachedEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        // We do not want to update the entity on the server.
        if (this.getWorld().isClient()) {
            if (this.isRemoved()) {
                this.ryoamicLights$setDynamicLightEnabled(false);
            } else {
                if (!RyoamicLights.get().config.getEntitiesLightSource().get() || !DynamicLightHandlers.canLightUp(this))
                    this.ryoamicLights$resetDynamicLight();
                else
                    this.ryoamicLights$dynamicLightTick();
                RyoamicLights.updateTracking(this);
            }
        }
    }
}
