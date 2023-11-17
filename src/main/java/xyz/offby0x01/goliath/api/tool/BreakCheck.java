package xyz.offby0x01.goliath.api.tool;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public interface BreakCheck {
    boolean canBreak(BlockView view, BlockPos pos);
}
