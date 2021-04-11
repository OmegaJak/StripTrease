package omegajak.striptrease.mixin;

import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import omegajak.striptrease.StripTrease;
import omegajak.striptrease.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {

    @Inject(at = @At("HEAD"), method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;", cancellable = true)
    public void onAxeUseMixin(ItemUsageContext context, CallbackInfoReturnable<ActionResult> callbackInfo) {
        ModConfig.UseAxeStrippingMode strippingMode = StripTrease.getCurrentConfig().useAxeStrippingMode;
        if (strippingMode == ModConfig.UseAxeStrippingMode.DISABLE
                || (strippingMode == ModConfig.UseAxeStrippingMode.REQUIRE_SNEAK && context.getPlayer() != null && !context.getPlayer().isSneaking())) {
            callbackInfo.setReturnValue(ActionResult.PASS);
            callbackInfo.cancel();
        }
    }
}

