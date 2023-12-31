/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.thinkingstudio.ryoamiclights.mixin.lightsource;

import org.thinkingstudio.ryoamiclights.DynamicLightSource;
import org.thinkingstudio.ryoamiclights.RyoamicLights;
import org.thinkingstudio.ryoamiclights.api.DynamicLightHandlers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements DynamicLightSource {
	@Unique
	protected int lambdynlights$luminance;

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Override
	public void dynamicLightTick() {
		if (!RyoamicLights.get().config.getEntitiesLightSource().get() || !DynamicLightHandlers.canLightUp(this)) {
			this.lambdynlights$luminance = 0;
			return;
		}

		if (this.isOnFire() || this.isGlowing()) {
			this.lambdynlights$luminance = 15;
		} else {
			this.lambdynlights$luminance = RyoamicLights.getLivingEntityLuminanceFromItems((LivingEntity) (Object) this);
		}

		int luminance = DynamicLightHandlers.getLuminanceFrom(this);
		if (luminance > this.lambdynlights$luminance)
			this.lambdynlights$luminance = luminance;
	}

	@Override
	public int getLuminance() {
		return this.lambdynlights$luminance;
	}
}
