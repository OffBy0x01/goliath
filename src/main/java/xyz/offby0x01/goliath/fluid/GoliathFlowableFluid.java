package xyz.offby0x01.goliath.fluid;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import xyz.offby0x01.goliath.definition.FluidLike;

import java.util.Optional;

public abstract class GoliathFlowableFluid extends FlowableFluid implements FluidLike {

    public Fluid flowingFluid;
    public Fluid stillFluid;
    public FluidBlock fluidBlock;
    private BucketItem bucket;
    public GoliathFlowableFluid() {}

    public void setFluidBlock(FluidBlock fluidBlock){
        this.fluidBlock = fluidBlock;
    }

    public void setFlowingFluid(Fluid fluid) {
        this.flowingFluid = fluid;
    }

    @Override
    public Fluid getFlowing() {
        return flowingFluid;
    }

    public void setStillFluid(Fluid fluid) {
        this.stillFluid = fluid;
    }

    @Override
    public Fluid getStill() {
        return stillFluid;
    }

    public void setBucketItem(BucketItem bucket) {
        this.bucket = bucket;
    }

    @Override
    public Item getBucketItem() {
        return Items.WATER_BUCKET;
    }

    @Override
    protected boolean isInfinite(World world) {
        return false;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override
    public int getFlowSpeed(WorldView world) {
        return 4;
    }

    @Override
    public BlockState toBlockState(FluidState state) {
        return fluidBlock.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == flowingFluid || fluid == stillFluid;
    }

    @Override
    public int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }

    @Override
    public int getTickRate(WorldView world) {
        return 5;
    }

    @Override
    public boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
//        return direction == Direction.DOWN && !fluid.matchesType(TestFluids.NO_OVERLAY);
    }

    @Override
    protected float getBlastResistance() {
        return 100.0F;
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL);
    }

    @Override
    public Fluid asFluid() {
        return this;
    }

    public static class Flowing extends GoliathFlowableFluid {
        public Flowing() {
            this.flowingFluid = this.asFluid();
        }

        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }
    }

    public static class Still extends GoliathFlowableFluid {
        public Still() {
            this.stillFluid = this.asFluid();
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }
    }
}
