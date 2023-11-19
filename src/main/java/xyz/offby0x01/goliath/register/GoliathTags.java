package xyz.offby0x01.goliath.register;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import xyz.offby0x01.goliath.Goliath;

public class GoliathTags {
    public static final TagKey<Block> CRUCIBLE_HEAT_SOURCES = block("crucible_heat_sources");

    public static TagKey<Item> item(String path) {
        return TagKey.of(RegistryKeys.ITEM, new GoliathIdentifier(path));
    }

    public static TagKey<Block> block(String path) {
        return TagKey.of(RegistryKeys.BLOCK, new GoliathIdentifier(path));
    }

    public static void init() {
        Goliath.LOGGER.debug("initializing GoliathTags...");

    }

}
