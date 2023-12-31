/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.thinkingstudio.ryoamiclights.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface LilTaterBlockEntityAccessor {
	boolean lambdynlights$isEmpty();

	DefaultedList<ItemStack> lambdynlights$getItems();
}
