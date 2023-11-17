package xyz.offby0x01.goliath.api.tool;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import xyz.offby0x01.goliath.Goliath;

public interface GoliathTool {

    default int getRadius(ItemStack toolStack) {
        return 0;
    }

    default int getPenetration(ItemStack toolStack) {
        return 0;
    }


    default boolean canBreakBlock(BlockView blockView, BlockPos blockPos, ItemStack toolStack) {
        BlockState blockState = blockView.getBlockState(blockPos);

        if (blockState.getHardness(blockView, blockPos) == -1.0) return false;

        if (toolStack.isSuitableFor(blockState)) return true;

        return false;
    }

    default void tryBreakBlocks(World world, BlockPos blockPos, PlayerEntity player) {
        ItemStack toolStack = player.getMainHandStack();
        Boolean canBreak = canBreakBlock(world, blockPos, toolStack);
        String s = "false";
        if (canBreak) s = "true";
        Goliath.LOGGER.info(String.format("tryBreakBlock.canBreak -> %s", s));
        if (!canBreak) return;


        int radius = getRadius(toolStack);
        int penetration = getPenetration(toolStack);

        BlockBreaker.breakWith(world, player, blockPos, radius, penetration, (view, targetPos) -> canBreakBlock(view, targetPos, toolStack), true);
    }


}
