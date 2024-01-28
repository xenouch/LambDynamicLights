package org.thinkingstudio.ryoamiclights;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class RyoamicLightsExpectPlatform {
    @ExpectPlatform
    public static boolean isDevEnvironment() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
}
