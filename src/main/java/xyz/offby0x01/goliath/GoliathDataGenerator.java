package xyz.offby0x01.goliath;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import xyz.offby0x01.goliath.datagen.GoliathEnglishLangProvider;
import xyz.offby0x01.goliath.datagen.GoliathModelProvider;

public class GoliathDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(GoliathEnglishLangProvider::new);
		pack.addProvider(GoliathModelProvider::new);
	}
}
