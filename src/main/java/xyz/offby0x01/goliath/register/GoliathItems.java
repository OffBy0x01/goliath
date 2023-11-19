package xyz.offby0x01.goliath.register;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.offby0x01.goliath.Goliath;
import xyz.offby0x01.goliath.api.tool.GoliathTool;
import xyz.offby0x01.goliath.definition.ItemDefinition;
import xyz.offby0x01.goliath.items.SortOrder;
import xyz.offby0x01.goliath.items.equipment.HammerLikeItem;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static xyz.offby0x01.goliath.items.SortOrder.HAMMER;
import static xyz.offby0x01.goliath.items.SortOrder.ITEMS_OTHER;

@SuppressWarnings("unused")
public final class GoliathItems {
    public static Map<Identifier, ItemDefinition<?>> ITEMS = new LinkedHashMap<>();

    public static final ItemDefinition<HammerLikeItem> DEEPSLATE_HAMMER = itemHandheld("Deepslate Hammer", "deepslate_hammer",
            p -> new HammerLikeItem(ToolMaterials.STONE, 1, 0.25f, p.maxDamageIfAbsent(90), 1, 1), HAMMER);
    public static final ItemDefinition<HammerLikeItem> IRON_HAMMER = itemHandheld("Iron Hammer", "iron_hammer",
            p -> new HammerLikeItem(ToolMaterials.IRON, 2, 0.33f, p.maxDamageIfAbsent(180), 1, 1), HAMMER);

    public static final ItemDefinition<HammerLikeItem> DIAMOND_HAMMER = item("Diamond Hammer", "diamond_hammer",
            s -> new HammerLikeItem(ToolMaterials.DIAMOND, 2, 0.4f, s.maxDamageIfAbsent(360), 1, 2),
            (item, modelGenerator) -> modelGenerator.register(item, Models.HANDHELD),
            SortOrder.HAMMER);

    public static <T extends Item> ItemDefinition<T> item(
            String englishName,
            String path,
            Function<? super FabricItemSettings, T> ctor,
            BiConsumer<Item, ItemModelGenerator> modelGenerator,
            SortOrder sortOrder) {

        T item = ctor.apply(new FabricItemSettings());

        ItemDefinition<T> definition = new ItemDefinition<T>(englishName, path, item, modelGenerator, sortOrder);

        if (ITEMS.put(definition.getId(), definition) != null) {
            throw new IllegalArgumentException("Item id already taken : " + definition.getId());
        }

        return definition;
    }

    public static ItemDefinition<Item> item(String englishName, String path, SortOrder sortOrder) {

        return GoliathItems.item(englishName, path, Item::new, (item, modelGenerator) -> modelGenerator.register(item, Models.HANDHELD_ROD), sortOrder);
    }

    public static <T extends Item> ItemDefinition<T> item(String englishName, String path, Function<? super FabricItemSettings, T> ctor,
                                                          SortOrder sortOrder) {
        return GoliathItems.item(englishName, path, ctor, (item, modelGenerator) -> modelGenerator.register(item, Models.HANDHELD_ROD), sortOrder);
    }

    public static <T extends Item> ItemDefinition<T> itemHandheld(String englishName, String path, Function<? super FabricItemSettings, T> ctor) {
        return itemHandheld(englishName, path, ctor, ITEMS_OTHER);
    }

    public static <T extends Item> ItemDefinition<T> itemHandheld(String englishName, String path, Function<? super FabricItemSettings, T> ctor, SortOrder sortOrder) {
        return GoliathItems.item(englishName, path, p -> ctor.apply(p.maxCount(1)), (item, modelGenerator) -> modelGenerator.register(item, Models.HANDHELD_ROD), sortOrder);
    }

    public static void init() {
        Goliath.LOGGER.info("initializing GoliathItems...");
        GoliathItems.ITEMS.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().sortOrder)).forEach(entry -> {
            Registry.register(Registries.ITEM, entry.getKey(), entry.getValue().asItem());
            entry.getValue().onRegister();
        });

        PlayerBlockBreakEvents.BEFORE.register((World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) -> {
            if (world.isClient()) return true;

            Item mainHandItem = player.getMainHandStack().getItem();
            if (!(mainHandItem instanceof GoliathTool)) return true;

            ((GoliathTool)mainHandItem).tryBreakBlocks(world, pos, player);

            return false;
        });
    }

    private GoliathItems() {
    }
}
