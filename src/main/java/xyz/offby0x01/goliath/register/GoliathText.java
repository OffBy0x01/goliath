package xyz.offby0x01.goliath.register;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import xyz.offby0x01.goliath.Goliath;

import java.util.List;

public enum GoliathText {
    Empty("Empty"),
    Eu("%s%s EU"),
    EuMaxed("%s / %s %sEU"),
    EuT("%s%s EU/t");

    private final String root;
    private final String englishText;
    private final List<String> additionalTranslationsKey;

    GoliathText(String englishText, String... additionalTranslationKey) {
        this.root = "text." + Goliath.MOD_ID;
        this.englishText = englishText;
        this.additionalTranslationsKey = List.of(additionalTranslationKey);
    }

    public List<String> getAdditionalTranslationKey() {
        return this.additionalTranslationsKey;
    }

    public String getEnglishText() {
        return englishText;
    }

    public String getTranslationKey() {
        return this.root + '.' + name();
    }

    public MutableText text() {
        return Text.translatable(getTranslationKey());
    }

    public MutableText text(Object... args) {
        return Text.translatable(getTranslationKey(), args);
    }

}
