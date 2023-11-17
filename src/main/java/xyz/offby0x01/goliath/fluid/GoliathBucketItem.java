package xyz.offby0x01.goliath.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Items;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class GoliathBucketItem extends BucketItem {
    public final int color;

    public GoliathBucketItem(Fluid fluid, Settings settings, int color) {
        super(fluid, settings.maxCount(1).recipeRemainder(Items.BUCKET));
        this.color = color;
    }

    public int getColor(int tintIndex) {
        return tintIndex == 1 ? color: -1;
    }
    
    @Override
    public Text getName() {
        return Text.translatable(this.getTranslationKey()).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(color)));
    }
}
