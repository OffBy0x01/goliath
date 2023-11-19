package xyz.offby0x01.goliath.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.offby0x01.goliath.Goliath;
import xyz.offby0x01.goliath.definition.BlockDefinition;
import xyz.offby0x01.goliath.definition.ItemDefinition;
import xyz.offby0x01.goliath.register.GoliathBlocks;
import xyz.offby0x01.goliath.register.GoliathIdentifier;
import xyz.offby0x01.goliath.register.GoliathItems;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class GoliathModelProvider extends FabricModelProvider {
    public GoliathModelProvider(FabricDataOutput generator) {
        super(generator);
    }
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
//        blockStateModelGenerator.registerSingleton()
        for (Map.Entry<Identifier, BlockDefinition<?>> entry : GoliathBlocks.BLOCKS.entrySet()) {
            BlockDefinition<?> blockDefinition = entry.getValue();
            Identifier identifier = entry.getKey();
            blockStateModelGenerator.registerSimpleCubeAll(blockDefinition.asBlock());
        }

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        GoliathItems.ITEMS.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().sortOrder)).forEach(entry -> {
            ItemDefinition<?> itemDefinition = entry.getValue();
            if (itemDefinition.modelGenerator != null) {
                Goliath.LOGGER.info(String.format("Using provided model for '%s'", itemDefinition.id()));
                itemDefinition.modelGenerator.accept(itemDefinition.asItem(), itemModelGenerator);
            } else {
                Goliath.LOGGER.info(String.format("No model defined for '%s', skipping model generation", itemDefinition.id()));
            }
        });

    }
}


