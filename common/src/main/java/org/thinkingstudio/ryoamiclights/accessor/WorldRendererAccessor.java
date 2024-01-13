/*
 * Copyright © 2020~2024 LambdAurora <email@lambdaurora.dev>
 * Copyright © 2024 ThinkingStudio
 *
 * This file is part of RyoamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.thinkingstudio.ryoamiclights.accessor;

/**
 * Represents an accessor for WorldRenderer.
 *
 * @author LambdAurora
 * @version 1.0.0
 * @since 1.0.0
 */
public interface WorldRendererAccessor {
	/**
	 * Schedules a chunk rebuild.
	 *
	 * @param x X coordinates of the chunk
	 * @param y Y coordinates of the chunk
	 * @param z Z coordinates of the chunk
	 * @param important {@code true} if important, else {@code false}
	 */
	void ryoamiclights$scheduleChunkRebuild(int x, int y, int z, boolean important);
}
