package xyz.offby0x01.goliath;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.offby0x01.goliath.api.GoliathTool;
import xyz.offby0x01.goliath.items.ModItems;

public class Goliath implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "goliath";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		ModItems.registerModItems();

		PlayerBlockBreakEvents.BEFORE.register((World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) -> {
			if (world.isClient()) return true;

			Item mainHandItem = player.getMainHandStack().getItem();
			if (!(mainHandItem instanceof GoliathTool)) return true;

			((GoliathTool)mainHandItem).tryBreakBlocks(world, pos, player);

			return false;
		});
	}
}