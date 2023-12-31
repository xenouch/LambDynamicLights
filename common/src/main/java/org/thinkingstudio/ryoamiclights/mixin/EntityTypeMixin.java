/*
 * Copyright © 2021 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.thinkingstudio.ryoamiclights.mixin;

import org.thinkingstudio.ryoamiclights.RyoamicLights;
import org.thinkingstudio.ryoamiclights.accessor.DynamicLightHandlerHolder;
import org.thinkingstudio.ryoamiclights.api.DynamicLightHandler;
import org.thinkingstudio.ryoamiclights.config.LightSourceSettingEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityType.class)
public abstract class EntityTypeMixin<T extends Entity> implements DynamicLightHandlerHolder<T> {
	@Shadow
	public abstract Text getName();

	@Unique
	private DynamicLightHandler<T> lambdynlights$lightHandler;
	@Unique
	private LightSourceSettingEntry lambdynlights$setting;

	@Override
	public @Nullable DynamicLightHandler<T> lambdynlights$getDynamicLightHandler() {
		return this.lambdynlights$lightHandler;
	}

	@Override
	public void lambdynlights$setDynamicLightHandler(DynamicLightHandler<T> handler) {
		this.lambdynlights$lightHandler = handler;
	}

	@Override
	public LightSourceSettingEntry lambdynlights$getSetting() {
		if (this.lambdynlights$setting == null) {
			var self = (EntityType<?>) (Object) this;
			var id = Registries.ENTITY_TYPE.getId(self);
			if (id.getNamespace().equals("minecraft") && id.getPath().equals("pig") && self != EntityType.PIG) {
				return null;
			}

			this.lambdynlights$setting = new LightSourceSettingEntry("light_sources.settings.entities."
					+ id.getNamespace() + '.' + id.getPath().replace('/', '.'),
					true, null, null);
			RyoamicLights.get().config.load(this.lambdynlights$setting);
		}

		return this.lambdynlights$setting;
	}

	@Override
	public Text lambdynlights$getName() {
		var name = this.getName();
		if (name == null) {
			return Text.translatable("lambdynlights.dummy");
		}
		return name;
	}
}
