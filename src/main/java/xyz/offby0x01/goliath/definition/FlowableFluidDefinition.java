package xyz.offby0x01.goliath.definition;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import xyz.offby0x01.goliath.fluid.GoliathBucketItem;
import xyz.offby0x01.goliath.fluid.GoliathFlowableFluid;
import xyz.offby0x01.goliath.items.SortOrder;
import xyz.offby0x01.goliath.register.GoliathItems;

public class FlowableFluidDefinition extends Definition implements FluidLike {
    public final int GOLIATH_FLUID_COLOR;
    public final GoliathFlowableFluid GOLIATH_FLUID;
    public final GoliathFlowableFluid GOLIATH_FLUID_FLOWING;
    public final FluidBlock GOLIATH_FLUID_BLOCK;
    private final FluidVariant GOLIATH_FLUID_VARIANT;

    public final ItemDefinition<GoliathBucketItem> GOLIATH_FLUID_BUCKET_DEFINITION;

    public FlowableFluidDefinition(String englishName, String id, int color) {
        super(englishName, id);
        this.GOLIATH_FLUID_COLOR = color;
        GOLIATH_FLUID = new GoliathFlowableFluid.Still();
        GOLIATH_FLUID_FLOWING = new GoliathFlowableFluid.Flowing();
        GOLIATH_FLUID_FLOWING.setStillFluid(GOLIATH_FLUID.asFluid());
        GOLIATH_FLUID.setFlowingFluid(GOLIATH_FLUID_FLOWING.asFluid());
        GOLIATH_FLUID_BUCKET_DEFINITION = GoliathItems.item(
                englishName + " Bucket",
                id + "_bucket",
                itemSettings -> new GoliathBucketItem(GOLIATH_FLUID, itemSettings, color),
                SortOrder.BUCKETS
        );
        GOLIATH_FLUID.setBucketItem(GOLIATH_FLUID_BUCKET_DEFINITION.asItem());

        GOLIATH_FLUID_BLOCK = new FluidBlock(GOLIATH_FLUID, AbstractBlock.Settings.copy(Blocks.LAVA));
        GOLIATH_FLUID_FLOWING.setFluidBlock(GOLIATH_FLUID_BLOCK);
        GOLIATH_FLUID.setFluidBlock(GOLIATH_FLUID_BLOCK);

        this.GOLIATH_FLUID_VARIANT = FluidVariant.of(GOLIATH_FLUID);
    }

    @Override
    public String getTranslationKey() {
        return this.getId().getPath();
    }

    @Override
    public Fluid asFluid() {
        return GOLIATH_FLUID;
    }

    public BucketItem getBucket() {
        return GOLIATH_FLUID_BUCKET_DEFINITION.asItem();
    }

    public FluidVariant variant() {
        return GOLIATH_FLUID_VARIANT;
    }
}