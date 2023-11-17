package xyz.offby0x01.goliath.register;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.offby0x01.goliath.Goliath;
import xyz.offby0x01.goliath.block.crucible.CrucibleBlockEntity;
import xyz.offby0x01.goliath.definition.BlockDefinition;

import java.util.SortedMap;
import java.util.TreeMap;

public class GoliathBlockEntityTypes {

    public static BlockEntityType<CrucibleBlockEntity> CRUCIBLE;


    public static void init() {
        Goliath.LOGGER.info("initializing GoliathBlockEntityTypes...");
        CRUCIBLE = register(GoliathBlocks.CRUCIBLE, CrucibleBlockEntity::new);

    }

    private static <T extends BlockEntity> BlockEntityType<T> register(BlockDefinition<?> block, FabricBlockEntityTypeBuilder.Factory<T> factory) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, block.getId(),
                FabricBlockEntityTypeBuilder.create(factory, block.asBlock()).build());
    }
}
