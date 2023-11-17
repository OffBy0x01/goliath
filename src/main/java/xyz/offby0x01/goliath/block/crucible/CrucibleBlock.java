package xyz.offby0x01.goliath.block.crucible;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import xyz.offby0x01.goliath.Goliath;
import xyz.offby0x01.goliath.register.GoliathBlockEntityTypes;

public class CrucibleBlock extends BlockWithEntity implements BlockEntityProvider {

    public CrucibleBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // With inheriting from BlockWithEntity this defaults to INVISIBLE
        return BlockRenderType.MODEL;
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, GoliathBlockEntityTypes.CRUCIBLE, (world1, pos, state1, be) -> CrucibleBlockEntity.tick(world1, pos, state1, be));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrucibleBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
//            if (blockEntity instanceof CrucibleBlockEntity) {
//                ItemScatterer.spawn(world, pos, (CrucibleBlockEntity)blockEntity);
//                world.updateComparators(pos,this);
//            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;

//        NamedScreenHandlerFactory screenHandlerFactory = ((CrucibleBlockEntity) world.getBlockEntity(pos));
//
//        if (screenHandlerFactory != null) {
//            player.openHandledScreen(screenHandlerFactory);
//        }

        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        // a block beside this one has changed state
        if (world.isClient()) return state;
        if (Direction.DOWN != direction) return state;

        CrucibleBlockEntity crucible = (CrucibleBlockEntity) world.getBlockEntity(pos);
        if (crucible == null) return state;

        crucible.getStateForNeighborState(neighborState);

        return state;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        // block has been freshly placed
        World world = ctx.getWorld();
        if (world.isClient) return super.getPlacementState(ctx);

        BlockPos blockPos = ctx.getBlockPos();
        CrucibleBlockEntity crucible = (CrucibleBlockEntity) world.getBlockEntity(blockPos);
        if (crucible == null) return super.getPlacementState(ctx);
        // i,j,k -> x,y,z
        BlockPos neighbourPos = blockPos.add(0,-1,0);
        BlockState neighbourState = world.getBlockState(neighbourPos);

        return crucible.getStateForNeighborState(neighbourState);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        // block has been moved into new position e.g. by piston
        if (world.isClient) return;

        CrucibleBlockEntity crucible = (CrucibleBlockEntity) world.getBlockEntity(pos);
        if (crucible == null) return;

        BlockPos neighbourPos = pos.add(0,-1,0);
        BlockState neighbourState = world.getBlockState(neighbourPos);

        BlockState updatedState = crucible.getStateForNeighborState(neighbourState);
        super.onBlockAdded(updatedState, world, pos, state, notify);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (world.isClient) return;
        if (!(entity instanceof LivingEntity)) return;
        // if state != hot return
        // entity.damage()
        Goliath.LOGGER.info(this.getClass().getSimpleName()+": onSteppedOn");

        super.onSteppedOn(world, pos, state, entity);
    }


}
