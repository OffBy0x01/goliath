package xyz.offby0x01.goliath.datagen;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import xyz.offby0x01.goliath.definition.Definition;
import xyz.offby0x01.goliath.register.GoliathText;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class GoliathEnglishLangProvider extends FabricLanguageProvider {

    private static final Map<String, String> translationPairs = new TreeMap<>();
    public GoliathEnglishLangProvider(FabricDataOutput generator) {
        super(generator, "en_us");
    }

    public void addTranslation(String key, String val, TranslationBuilder translationBuilder) {
        if (translationPairs.containsKey(key)) throw new IllegalArgumentException(
                String.format("Error adding translation key %s for translation %s : already registered for translation %s", key, val, translationPairs.get(key))
        );

        translationBuilder.add(key, val);
        translationPairs.put(key, val);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        // generate from explicit GoliathText definitions
        for (var entry : GoliathText.values()) {
            addTranslation(entry.getTranslationKey(), entry.getEnglishText(), translationBuilder);
            for (String additionalKey : entry.getAdditionalTranslationKey()) {
                addTranslation(additionalKey, entry.getEnglishText(), translationBuilder);
            }
        }

        // generate from definitions e.g. items, fluid, etc.
        for (Definition definition : Definition.TRANSLATABLE_DEFINITION) {
            addTranslation(definition.getTranslationKey(), definition.getEnglishName(), translationBuilder);
        }

        // generate from manually defined entries
//        Path existingTranslationsPath = dataGen.getModContainer().findPath("assets/goliath/lang").get();
//        try (var paths = Files.walk(existingTranslationsPath, 1)) {
//            paths.forEach(path -> {
//                try {
//                    translationBuilder.add(path);
//                } catch (java.io.IOException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        }
    }


}
