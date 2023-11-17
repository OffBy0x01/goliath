package xyz.offby0x01.goliath.api.tool;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import xyz.offby0x01.goliath.Goliath;

public class BlockBreaker {
    private BlockBreaker(){};

    private static Direction getBlockFaceDirection(World world, PlayerEntity player){
        Vec3d origin = player.getCameraPosVec(1);
        Vec3d rotation = player.getRotationVec(1);
        double reach = player.isCreative() ? 4.5f : 5f;
        Vec3d absolute = origin.add(rotation.x* reach, rotation.y* reach, rotation.z* reach);


        return world.raycast(new RaycastContext(origin, absolute, RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, player)).getSide();
        // todo blockHitResult.getType() === HitResult.Type.BLOCK

    }

    public static void breakWith(World world, PlayerEntity player, BlockPos blockPos, int radius, int penetration, BreakCheck breakCheck, boolean damageTool) {
        ItemStack mainHandStack = player.getMainHandStack();

        Direction lookDirection = getBlockFaceDirection(world, player),
                firstDirection = Direction.NORTH,
                secondDirection = Direction.EAST;

        if(lookDirection == Direction.NORTH || lookDirection ==  Direction.SOUTH){
            firstDirection = Direction.UP;
            secondDirection = Direction.EAST;
        } else if(lookDirection == Direction.EAST || lookDirection == Direction.WEST){
            firstDirection = Direction.UP;
            secondDirection = Direction.NORTH;
        }


        // penetration allows multiple levels of breakage
//        for (BlockPos penetrationPos : BlockPos.iterate(blockPos, blockPos.offset(lookDirection, penetration)))
        // first & second direction are used as axis
        for (BlockPos targetPos : BlockPos.iterateInSquare(blockPos, radius, firstDirection, secondDirection)) {
            Goliath.LOGGER.info(String.format("BreakWith -> iterateInSquare x:%d, y:%d, z:%d", targetPos.getX(), targetPos.getY(), targetPos.getZ()));
            if (!breakCheck.canBreak(world, targetPos)) continue;

            boolean successfullyBroken = world.breakBlock(targetPos,true, player);
            if (successfullyBroken && damageTool) mainHandStack.damage(1, player, (p) -> p.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
    }
}
