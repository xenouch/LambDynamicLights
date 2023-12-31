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
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin<T extends BlockEntity> implements DynamicLightHandlerHolder<T> {
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
			var self = (BlockEntityType<?>) (Object) this;
			var id = Registries.BLOCK_ENTITY_TYPE.getId(self);
			if (id == null) {
				return null;
			}

			this.lambdynlights$setting = new LightSourceSettingEntry("light_sources.settings.block_entities."
					+ id.getNamespace() + '.' + id.getPath().replace('/', '.'),
					true, null, null);
			RyoamicLights.get().config.load(this.lambdynlights$setting);
		}

		return this.lambdynlights$setting;
	}

	@Override
	public Text lambdynlights$getName() {
		var self = (BlockEntityType<?>) (Object) this;
		var id = Registries.BLOCK_ENTITY_TYPE.getId(self);
		if (id == null) {
			return Text.empty();
		}
		return Text.literal(id.getNamespace() + ':' + id.getPath());
	}
}
