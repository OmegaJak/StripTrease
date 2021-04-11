package omegajak.striptrease;

import net.minecraft.block.Block;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class StripLogRecipe extends SpecialCraftingRecipe {
    public StripLogRecipe(Identifier identifier) { super(identifier); }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        return getAxeAndStrippable(inventory).isPresent();
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        Pair<ItemStack, ItemStack> axeAndStrippable = getAxeAndStrippable(inventory).get();
        Item stripped = ExposedAxeItem.getStrippedBlock((BlockItem)axeAndStrippable.getRight().getItem()).get().asItem();
        return new ItemStack(stripped);
    }

    @Override
    public DefaultedList<ItemStack> getRemainingStacks(CraftingInventory inventory) {
        DefaultedList<ItemStack> remainingStacks = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        Optional<ItemStack> optionalAxeStack = getAxe(inventory);
        if (optionalAxeStack.isPresent()) {
            ItemStack remainingAxe = optionalAxeStack.get().copy();
            if (!remainingAxe.damage(1, StripTrease.RANDOM, null)) {
                int axeIndex = getIndexOf(inventory, optionalAxeStack.get());
                remainingStacks.set(axeIndex, remainingAxe);
            }
        }

        return remainingStacks;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return StripTrease.STRIP_LOG_SERIALIZER;
    }

    private int getIndexOf(CraftingInventory inventory, ItemStack itemStack) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.getStack(i) == itemStack) return i;
        }

        return -1;
    }

    private Optional<Pair<ItemStack, ItemStack>> getAxeAndStrippable(CraftingInventory inventory) {
        Optional<ItemStack> optionalAxeStack = getAxe(inventory);
        if (!optionalAxeStack.isPresent()) return Optional.empty();

        ArrayList<ItemStack> strippables = getStrippables(inventory);
        if (strippables.size() != 1) return Optional.empty();

        return Optional.of(new Pair<>(optionalAxeStack.get(), strippables.get(0)));
    }

    private Optional<ItemStack> getAxe(CraftingInventory inventory) {
        ArrayList<ItemStack> axes = getAxes(inventory);
        if (axes.size() != 1) return Optional.empty();

        return Optional.of(axes.get(0));
    }

    @NotNull
    private ArrayList<ItemStack> getAxes(CraftingInventory inventory) {
        return getItemsMatchingPredicate(inventory, itemStack -> itemStack.getItem() instanceof AxeItem && itemStack.isDamageable());
    }

    @NotNull
    private ArrayList<ItemStack> getStrippables(CraftingInventory inventory) {
        return getItemsMatchingPredicate(inventory, ExposedAxeItem::isStrippable);
    }

    private ArrayList<ItemStack> getItemsMatchingPredicate(CraftingInventory inventory, Predicate<ItemStack> predicate) {
        ArrayList<ItemStack> matching = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            if (predicate.test(inventory.getStack(i))) {
                matching.add(inventory.getStack(i));
            }
        }

        return matching;
    }

    @Override
    public DefaultedList<Ingredient> getPreviewInputs() {
        return null;
    }
}
