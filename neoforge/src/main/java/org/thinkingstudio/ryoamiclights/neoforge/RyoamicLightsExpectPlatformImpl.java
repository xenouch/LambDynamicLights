package org.thinkingstudio.ryoamiclights.neoforge;

import net.neoforged.fml.loading.FMLLoader;

public class RyoamicLightsExpectPlatformImpl {
    public static boolean isDevEnvironment() {
        return !FMLLoader.isProduction();
    }
}
