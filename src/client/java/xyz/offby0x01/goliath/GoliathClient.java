package xyz.offby0x01.goliath;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.util.Identifier;
import xyz.offby0x01.goliath.definition.BlockDefinition;
import xyz.offby0x01.goliath.definition.FlowableFluidDefinition;
import xyz.offby0x01.goliath.fluid.GoliathBucketItem;
import xyz.offby0x01.goliath.register.GoliathBlocks;
import xyz.offby0x01.goliath.register.GoliathFluids;
import xyz.offby0x01.goliath.register.GoliathIdentifier;

import java.util.Map;

public class GoliathClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		for (Map.Entry<Identifier, FlowableFluidDefinition> entry : GoliathFluids.FLUIDS.entrySet()) {
			FluidRenderHandlerRegistry.INSTANCE.register(entry.getValue().GOLIATH_FLUID, entry.getValue().GOLIATH_FLUID_FLOWING, new SimpleFluidRenderHandler(
					new GoliathIdentifier("block/fluid/still"),
					new GoliathIdentifier("block/fluid/flowing"),
					entry.getValue().GOLIATH_FLUID_COLOR
			));

			ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((GoliathBucketItem) stack.getItem()).getColor(tintIndex), entry.getValue().getBucket());
		}
	}
}