package xyz.offby0x01.goliath;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;
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
			BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), entry.getValue().GOLIATH_FLUID, entry.getValue().GOLIATH_FLUID_FLOWING);
		}
	}
}


/* TODO - look at whether this can be used to tint screen on entering fluid
*
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class ModHud implements HudRenderCallback{
    private int scaledWidth;
    private int scaledHeight;
public static final Identifier MELON_OUTLINE = new Identifier("textures/entity/melon_overlay.png");




    private void renderOverlay(DrawContext context, Identifier texture, float opacity) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        context.setShaderColor(1.0f, 1.0f, 1.0f, opacity);
        context.drawTexture(texture, 0, 0, -90, 0.0f, 0.0f, this.scaledWidth, this.scaledHeight, this.scaledWidth, this.scaledHeight);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        this.scaledWidth = drawContext.getScaledWindowWidth();
        this.scaledHeight = drawContext.getScaledWindowHeight();
        this.renderOverlay(drawContext, MELON_OUTLINE, 0.5f);
    }
}
*
* */