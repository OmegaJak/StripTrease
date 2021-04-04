package omegajak.striptrease.interfaces;

import net.minecraft.block.Block;

import java.util.Optional;

public interface LogStripper {
    Optional<Block> getStrippedBlock(Block block);
}
