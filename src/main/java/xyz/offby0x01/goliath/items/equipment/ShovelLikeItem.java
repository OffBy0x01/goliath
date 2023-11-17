package xyz.offby0x01.goliath.items.equipment;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import xyz.offby0x01.goliath.api.tool.GoliathTool;

public class ShovelLikeItem extends ShovelItem implements GoliathTool {

    private int radius = 1; // 3x3
    private int penetration = 0;

    public ShovelLikeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, int radius, int penetration) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.radius = radius;
        this.penetration = penetration;
    }

    public ShovelLikeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public int getRadius(ItemStack stack) {
        return radius;
    }

}

