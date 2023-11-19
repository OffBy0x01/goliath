package xyz.offby0x01.goliath.register;

import net.fabricmc.fabric.api.datagen.v1.loot.FabricBlockLootTableGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import xyz.offby0x01.goliath.Goliath;
import xyz.offby0x01.goliath.block.crucible.CrucibleBlock;
import xyz.offby0x01.goliath.definition.BlockDefinition;
import xyz.offby0x01.goliath.items.SortOrder;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;


public class GoliathBlocks {
    public static SortedMap<Identifier, BlockDefinition<?>> BLOCKS = new TreeMap<>();

    public static final BlockDefinition<CrucibleBlock> CRUCIBLE = block(
            "Crucible",
            "crucible",
            new CrucibleBlock(FabricBlockSettings.create()
                .mapColor(MapColor.IRON_GRAY)
                .requiresTool()
                .strength(5.0f, 1200.0f)
                .sounds(BlockSoundGroup.ANVIL)
                .pistonBehavior(PistonBehavior.BLOCK)),
            BlockItem::new,
            null,
            List.of(BlockTags.NEEDS_STONE_TOOL, BlockTags.PICKAXE_MINEABLE),
            SortOrder.BLOCKS_OTHER
    );

    private static <T extends Block> BlockDefinition<T> block(
            String englishName,
            String id,
            T block,
            BiFunction<? super T, FabricItemSettings, BlockItem> blockItemCtor,
            BiConsumer<Item, ItemModelGenerator> itemModelGenerator,
            List<TagKey<Block>> tags,
            SortOrder sortOrder) {
        BlockDefinition<T> definition = new BlockDefinition<>(
            englishName,
            id,
            block,
            blockItemCtor,
            itemModelGenerator,
            tags,
            sortOrder
        );

        // just focus on what you can do & what you definitely need
        // currently that is block, blockItemCtor & itemModelGenerator
        // do everything else only once these are working!!!

        if (BLOCKS.put(definition.getId(), definition) != null) {
            throw new IllegalArgumentException("Block id already taken : " + definition.getId());
        }

        return definition;
    }

    public static void init() {
        Goliath.LOGGER.info("initializing GoliathBlocks...");
        for (Map.Entry<Identifier, BlockDefinition<?>> entry : GoliathBlocks.BLOCKS.entrySet()) {
            Registry.register(Registries.BLOCK, entry.getKey(), entry.getValue().asBlock());
            entry.getValue().onRegister();
            // no need to register bucket - handled within setupItems via FluidDefinition
        }
    }
}
