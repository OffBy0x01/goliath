package xyz.offby0x01.goliath;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.offby0x01.goliath.register.GoliathBlockEntityTypes;
import xyz.offby0x01.goliath.register.GoliathBlocks;
import xyz.offby0x01.goliath.register.GoliathFluids;
import xyz.offby0x01.goliath.register.GoliathItems;

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

		GoliathFluids.init();
		GoliathItems.init();
		GoliathBlocks.init();
		GoliathBlockEntityTypes.init();

		LOGGER.info("Goliath setup done!");
	}

}