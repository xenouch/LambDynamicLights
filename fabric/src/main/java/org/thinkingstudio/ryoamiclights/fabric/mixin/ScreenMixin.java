package org.thinkingstudio.ryoamiclights.fabric.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.thinkingstudio.obsidianui.Tooltip;

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(method = "render", at = @At(value = "TAIL"))
    private void onRender(GuiGraphics graphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (((Screen) (Object) this) instanceof VideoOptionsScreen) {
            Tooltip.renderAll(graphics);
        }
    }
}
