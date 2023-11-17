package xyz.offby0x01.goliath.definition;

import net.minecraft.fluid.Fluid;

public interface FluidLike {
    default Fluid asFluid() {
        return null;
    }
}
