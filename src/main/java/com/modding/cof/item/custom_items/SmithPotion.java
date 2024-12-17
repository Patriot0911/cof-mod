package com.modding.cof.item.custom_items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class SmithPotion extends Item {
    public SmithPotion(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 300, 0));
            
            if (entity instanceof Player player) {
                String[] messages = {
                    "В хід пішла сама дешманська горілка, яка взагалі куплялася для компресів",
                    "Пішла за 5хв",
                    "Як же ахуєнно не відчувати нічого",
                    "Я раніше не розумів, чому люди вживають алкоголь.", 
                    "Тепер розумію: достатня його доза дозволяє не відчувати абсолютно нічого."
                };
                player.displayClientMessage(Component.nullToEmpty(messages[(int) (Math.random() * messages.length)]), true);
            }
        }

        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }
}
