package xyz.offby0x01.goliath.items.equipment;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import xyz.offby0x01.goliath.api.tool.GoliathTool;

public class HammerLikeItem extends PickaxeItem implements GoliathTool {

    private int radius = 1; // 3x3
    private int penetration = 0;

    public HammerLikeItem(FabricItemSettings settings) {
        super(ToolMaterials.STONE, 1, 1, settings);
    }

    public HammerLikeItem(ToolMaterial toolMaterial, FabricItemSettings settings){
        super(toolMaterial, toolMaterial.getMiningLevel(), 0.2f, settings);
        this.radius = toolMaterial.getMiningLevel();
    }

    public HammerLikeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, FabricItemSettings settings, int radius, int penetration) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.radius = radius;
        this.penetration = penetration;
    }

    public HammerLikeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, FabricItemSettings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public int getRadius(ItemStack stack) {
        return radius;
    }

}
