package xyz.offby0x01.goliath.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.offby0x01.goliath.Goliath;
import xyz.offby0x01.goliath.items.equipment.HammerLikeItem;

public class ModItems {
    public static final Item DEEPSLATE_HAMMER = registerItem("deepslate_hammer",new HammerLikeItem(ToolMaterials.STONE,1,0.5f, new FabricItemSettings().maxDamageIfAbsent(64), 1, 1));
    public static final Item IRON_HAMMER = registerItem("iron_hammer",new HammerLikeItem(ToolMaterials.IRON,2,1f, new FabricItemSettings().maxDamageIfAbsent(128), 1, 1));
    public static final Item DIAMOND_HAMMER = registerItem("diamond_hammer",new HammerLikeItem(ToolMaterials.DIAMOND,3,1.5f, new FabricItemSettings().maxDamageIfAbsent(256), 2, 1));
    public static final Item NETHERITE_HAMMER = registerItem("netherite_hammer",new HammerLikeItem(ToolMaterials.NETHERITE,3,2f, new FabricItemSettings().maxDamageIfAbsent(512), 3, 1));
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Goliath.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Goliath.LOGGER.info(String.format("registering items for %s", Goliath.MOD_ID));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((FabricItemGroupEntries entries) -> {
            entries.add(DEEPSLATE_HAMMER);
            entries.add(IRON_HAMMER);
            entries.add(DIAMOND_HAMMER);
            entries.add(NETHERITE_HAMMER);
        });
    }
}
