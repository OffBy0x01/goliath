package xyz.offby0x01.goliath.definition;

/*
 * MIT License
 *
 * Copyright (c) 2020 Azercoco & Technici4n
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xyz.offby0x01.goliath.items.SortOrder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ItemDefinition<T extends Item> extends Definition {
    private final T item;
    public final SortOrder sortOrder;

    public final BiConsumer<Item, ItemModelGenerator> modelGenerator;
    private Consumer<? super T> onItemRegistrationEvent;

    public ItemDefinition(String englishName, String id, T item,
                          BiConsumer<Item, ItemModelGenerator> modelGenerator, SortOrder sortOrder) {
        super(englishName, id);
        this.item = item;
        this.modelGenerator = modelGenerator;
        this.onItemRegistrationEvent = null;
        this.sortOrder = sortOrder;
    }

    public ItemDefinition<T> withItemRegistrationEvent(Consumer<? super T> onItemRegistrationEvent) {
        this.onItemRegistrationEvent = onItemRegistrationEvent;
        return this;
    }

    public void onRegister() {
        if (this.onItemRegistrationEvent != null) {
            this.onItemRegistrationEvent.accept(item);
        }
    }
    public ItemStack stack() {
        return stack(1);
    }

    public ItemStack stack(int stackSize) {
        return new ItemStack(item, stackSize);
    }

    public boolean is(ItemStack stack) {
        return stack.equals(item);
    }

    public T asItem() {
        return item;
    }

    @Override
    public String getTranslationKey() {
        return item.getTranslationKey();
    }

}