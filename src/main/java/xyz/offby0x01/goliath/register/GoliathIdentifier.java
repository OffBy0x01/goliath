package xyz.offby0x01.goliath.register;

import net.minecraft.util.Identifier;
import xyz.offby0x01.goliath.Goliath;
public class GoliathIdentifier extends Identifier {
    public GoliathIdentifier(String path) {
        super(Goliath.MOD_ID, path);
    }
}
