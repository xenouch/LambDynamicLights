/*
 * Copyright © 2020~2024 LambdAurora <email@lambdaurora.dev>
 * Copyright © 2024 ThinkingStudio
 *
 * This file is part of RyoamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.thinkingstudio.ryoamiclights.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface LilTaterBlockEntityAccessor {
	boolean ryoamiclights$isEmpty();

	DefaultedList<ItemStack> ryoamiclights$getItems();
}
