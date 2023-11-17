package xyz.offby0x01.goliath.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.offby0x01.goliath.Goliath;
import xyz.offby0x01.goliath.register.GoliathIdentifier;
import xyz.offby0x01.goliath.register.GoliathItems;

import java.util.Comparator;
import java.util.Optional;

public class GoliathModelProvider extends FabricModelProvider {
    public GoliathModelProvider(FabricDataOutput generator) {
        super(generator);
    }
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        GoliathItems.ITEMS.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().sortOrder)).forEach(entry -> {
            if (entry.getKey().toString().contains("bucket")) {
                itemModelGenerator.register(
                    entry.getValue().asItem(),
                    new Model(Optional.of(new GoliathIdentifier("item/bucket")), Optional.empty())
                );
            } else { // todo make this better for non-tool items
                itemModelGenerator.register(
                    entry.getValue().asItem(),
                    Models.HANDHELD
                );
            }
        });

    }

    public static Model item(String parent) {
        return new Model(Optional.of(new Identifier(Goliath.MOD_ID, "item/" + parent)), Optional.empty());
    }


}


