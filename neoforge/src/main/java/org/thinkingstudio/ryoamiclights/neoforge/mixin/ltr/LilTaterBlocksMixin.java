/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.thinkingstudio.ryoamiclights.neoforge.mixin.ltr;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.thinkingstudio.ryoamiclights.RyoamicLights;
import org.thinkingstudio.ryoamiclights.api.DynamicLightHandlers;
import org.thinkingstudio.ryoamiclights.util.LilTaterBlockEntityAccessor;

@Pseudo
@Mixin(targets = "mods.ltr.registry.LilTaterBlocks", remap = false)
public class LilTaterBlocksMixin {
	@Accessor("LIL_TATER_BLOCK_ENTITY")
	private static BlockEntityType<?> getLilTaterBlockEntity() {
		throw new IllegalStateException("Accessor did not apply properly.");
	}

	@Dynamic
	@Inject(method = "init", at = @At("TAIL"))
	private static void onInit(CallbackInfo ci) {
		DynamicLightHandlers.registerDynamicLightHandler(getLilTaterBlockEntity(), entity -> {
			int luminance = 0;
			if (!((LilTaterBlockEntityAccessor) entity).lambdynlights$isEmpty()) {
				for (ItemStack item : ((LilTaterBlockEntityAccessor) entity).lambdynlights$getItems()) {
					luminance = Math.max(luminance, RyoamicLights.getLuminanceFromItemStack(item, !entity.getCachedState().getFluidState().isEmpty()));
				}
			}
			return luminance;
		});
	}
}
