package omegajak.striptrease.integration;

import me.shedaniel.rei.api.*;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import me.shedaniel.rei.plugin.crafting.DefaultCustomDisplay;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import omegajak.striptrease.ExposedAxeItem;
import omegajak.striptrease.StripTrease;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class REIPlugin implements REIPluginV0 {
    public static final Identifier REI_PLUGIN = new Identifier(StripTrease.MODID + ":plugin");;

    @Override
    public Identifier getPluginIdentifier() {
        return REI_PLUGIN;
    }

    @Override
    public void registerEntries(EntryRegistry entryRegistry) {
    }

    @Override
    public void registerPluginCategories(RecipeHelper recipeHelper) {
    }

    @Override
    public void registerRecipeDisplays(RecipeHelper recipeHelper) {
        List<EntryStack> axeEntries = FilterEntryStacks(entryStack -> entryStack.getItem() instanceof AxeItem).collect(Collectors.toList());
        Stream<EntryStack> strippableEntries = FilterEntryStacks(entryStack -> ExposedAxeItem.isStrippable(entryStack.getItem()));

        strippableEntries.forEach(strippableEntry -> {
            List<List<EntryStack>> inputs = new ArrayList<>();
            inputs.add(axeEntries);
            inputs.add(Collections.singletonList(EntryStack.create(strippableEntry.getItemStack())));

            Block stripped = ExposedAxeItem.getStrippedBlock((BlockItem)strippableEntry.getItem()).get();
            ItemStack outputStack = new ItemStack(stripped.asItem(), 1);
            List<EntryStack> output = Collections.singletonList(EntryStack.create(outputStack));
            recipeHelper.registerDisplay(new DefaultCustomDisplay(null, inputs, output));
        });
    }

    private Stream<EntryStack> FilterEntryStacks(Predicate<EntryStack> predicate) {
        return EntryRegistry.getInstance().getEntryStacks().filter(predicate::test);
    }

    @Override
    public void registerBounds(DisplayHelper displayHelper) {

    }

    @Override
    public void registerOthers(RecipeHelper recipeHelper) {

    }

    @Override
    public void preRegister() {

    }

    @Override
    public void postRegister() {

    }
}
