package omegajak.striptrease.item;

import net.minecraft.block.Block;
import net.minecraft.item.*;

import java.util.Optional;

public class ExposedAxeItem extends AxeItem {
    protected ExposedAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public static Optional<Block> getStrippedBlock(BlockItem blockItem) {
        return getStrippedBlock(blockItem.getBlock());
    }

    public static Optional<Block> getStrippedBlock(Block block) {
        return Optional.ofNullable(STRIPPED_BLOCKS.get(block));
    }

    public static boolean isStrippable(ItemStack itemStack) {
        return isStrippable(itemStack.getItem());
    }

    public static boolean isStrippable(Item item) {
        return item instanceof BlockItem && getStrippedBlock((BlockItem)item).isPresent();
    }
}
