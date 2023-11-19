package xyz.offby0x01.goliath.definition;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.tag.TagKey;
import xyz.offby0x01.goliath.items.SortOrder;
import xyz.offby0x01.goliath.register.GoliathItems;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class BlockDefinition<T extends Block> extends Definition implements ItemLike {
    public final T block;
    public final ItemDefinition<BlockItem> blockItem;
    public final List<TagKey<Block>> tags;
    private BiConsumer<Block, Item> onBlockRegistrationEvent;

    public BlockDefinition(
        String englishName,
        String id,
        T block,
        BiFunction<? super T, FabricItemSettings, BlockItem> blockItemCtor,
        BiConsumer<Item, ItemModelGenerator> itemModelGenerator,
        List<TagKey<Block>> tags,
        SortOrder sortOrder) {

        super(englishName, id, false);
        this.block = block;
        this.blockItem = GoliathItems.item(
            englishName,
            id,
            s -> blockItemCtor.apply(block, s),
            itemModelGenerator,
            sortOrder);
        this.tags = tags;
    }

    public BlockDefinition<T> withBlockRegistrationEvent(BiConsumer<Block, Item> onBlockRegistrationEvent) {
        this.onBlockRegistrationEvent = onBlockRegistrationEvent;
        return this;
    }

    public T asBlock() {
        return block;
    }

    @Override
    public Item asItem() {
        if (blockItem != null) {
            return blockItem.asItem();
        } else {
            return null;
        }
    }

    @Override
    public String getTranslationKey() {
        return blockItem.getTranslationKey();
    }

    public void onRegister() {
        if (onBlockRegistrationEvent != null) {
            onBlockRegistrationEvent.accept(block, blockItem.asItem());
        }
    }
}
