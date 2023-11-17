package xyz.offby0x01.goliath.register;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.offby0x01.goliath.Goliath;
import xyz.offby0x01.goliath.definition.FlowableFluidDefinition;

import java.util.LinkedHashMap;
import java.util.Map;


public class GoliathFluids {
    public static Map<Identifier, FlowableFluidDefinition> FLUIDS = new LinkedHashMap<>();

    public static final FlowableFluidDefinition MOLTEN_COPPER = fluid(
            "Molten Copper",
            "molten_copper",
            0xf59568);
    public static final FlowableFluidDefinition MOLTEN_IRON = fluid(
            "Molten Iron",
            "molten_iron",
            0xe7e7e4);

    public static final FlowableFluidDefinition MOLTEN_ZINC = fluid(
            "Molten Zinc",
            "molten_zinc",
            0xe9fff6);

    public static final FlowableFluidDefinition MOLTEN_GOLD = fluid(
            "Molten Gold",
            "molten_gold",
            0xffc638);

    public static final FlowableFluidDefinition MOLTEN_BRASS = fluid(
            "Molten Brass",
            "molten_brass",
            0xffc760);

    public static final FlowableFluidDefinition MOLTEN_ANDESITE_ALLOY = fluid(
            "Molten Andesite Alloy",
            "molten_andesite_alloy",
            0xafafb6);

    public static FlowableFluidDefinition fluid(
            String englishName,
            String id,
            int color) {

        FlowableFluidDefinition fluidDefinition = new FlowableFluidDefinition(englishName, id, color);
        if (FLUIDS.put(fluidDefinition.getId(), fluidDefinition) != null) {
            throw new IllegalArgumentException("Fluid ID already taken: " + fluidDefinition.getId());
        }

        return fluidDefinition;
    }

    public static void init(){
        Goliath.LOGGER.info("initializing GoliathFluids...");
        for (Map.Entry<Identifier, FlowableFluidDefinition> entry : GoliathFluids.FLUIDS.entrySet()) {
            Registry.register(Registries.FLUID, entry.getKey(), entry.getValue().GOLIATH_FLUID);
            Registry.register(Registries.FLUID, entry.getKey()+"_flowing", entry.getValue().GOLIATH_FLUID_FLOWING);
            Registry.register(Registries.BLOCK, entry.getKey()+"_block", entry.getValue().GOLIATH_FLUID_BLOCK);
            // no need to register bucket - handled within setupItems via FluidDefinition
        }
    }

    private GoliathFluids() {
    }
}
