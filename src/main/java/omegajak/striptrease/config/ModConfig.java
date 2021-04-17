package omegajak.striptrease.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import omegajak.striptrease.StripTrease;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@Config(name = StripTrease.MODID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public UseAxeStrippingMode useAxeStrippingMode = UseAxeStrippingMode.REQUIRE_SNEAK;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean addStripRecipe = false;

    public enum UseAxeStrippingMode implements SelectionListEntry.Translatable {
        VANILLA,
        REQUIRE_SNEAK,
        DISABLE;

        @Override
        public @NotNull String getKey() {
            return "text.autoconfig.striptrease.option.useAxeStrippingMode." + name().toLowerCase(Locale.ROOT);
        }
    }

}
