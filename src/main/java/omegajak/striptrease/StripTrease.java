package omegajak.striptrease;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import omegajak.striptrease.config.ModConfig;
import omegajak.striptrease.recipe.StripLogRecipe;

import java.util.Random;

public class StripTrease implements ModInitializer {
	public static final String MODID = "striptrease";
	public static SpecialRecipeSerializer<StripLogRecipe> STRIP_LOG_SERIALIZER;
	public static Random RANDOM = new Random();

	private static ConfigHolder<ModConfig> CONFIG;

	@Override
	public void onInitialize() {
		StripTrease.CONFIG = AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
		STRIP_LOG_SERIALIZER = RecipeSerializer.register("crafting_special_striplogs", new SpecialRecipeSerializer<>(StripLogRecipe::new));
	}

	public static ModConfig getCurrentConfig() {
		return CONFIG.get();
	}
}
