package xyz.offby0x01.goliath.definition;

import net.minecraft.item.Item;

public interface ItemLike {
    default Item asItem() {
        return null;
    }
}
