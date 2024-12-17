package com.modding.cof.item.custom_items_recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class SmithPotionBrewingRecipe implements IBrewingRecipe {

    @Override
    public boolean isInput(ItemStack input) {
        boolean valid = input.getItem() == Items.POTION &&
                input.hasTag() &&
                "minecraft:awkward".equals(input.getTag().getString("Potion"));
        return valid;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        boolean valid = ingredient.getItem() == Items.WHEAT;
        return valid;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (isInput(input) && isIngredient(ingredient)) {
            return new ItemStack(com.modding.cof.item.Items.smithPotion.get());
        }
        return ItemStack.EMPTY;
    }

}
