package xyz.offby0x01.goliath.block.crucible;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.offby0x01.goliath.Goliath;
import xyz.offby0x01.goliath.register.GoliathBlockEntityTypes;
import xyz.offby0x01.goliath.register.GoliathTags;

public class CrucibleBlockEntity extends BlockEntity {
    private int temperatureDelta = 0;
    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            // Here, you can pick your capacity depending on the fluid variant.
            // For example, if we want to store 8 buckets of any fluid:
            return (8 * FluidConstants.BUCKET) / 81; // This will convert it to mB amount to be consistent
        }

    };

    public CrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(GoliathBlockEntityTypes.CRUCIBLE, pos, state);
    }

    public BlockState getStateForNeighborState(BlockState neighbourState) {
        BlockState state = this.getCachedState();
        if (!neighbourState.isIn(GoliathTags.CRUCIBLE_HEAT_SOURCES)) return state;

        Goliath.LOGGER.info("getStateForNeighborState -> update state here");

        return state;
    }

    public static void tick(World world, BlockPos pos, BlockState state, CrucibleBlockEntity be) {

    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.put("fluidVariant", fluidStorage.variant.toNbt());
        nbt.putLong("amount", fluidStorage.amount);
        nbt.putInt("temperatureDelta", temperatureDelta);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        temperatureDelta = nbt.getInt("temperatureDelta");
        fluidStorage.variant = FluidVariant.fromNbt(nbt.getCompound("fluidVariant"));
        fluidStorage.amount = nbt.getLong("amount");
    }
}
