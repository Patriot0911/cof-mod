package com.modding.cof.client.init;

import org.lwjgl.glfw.GLFW;

import com.modding.cof.CoFMod;
import com.modding.cof.client.screen.SkillTreeScreen;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = CoFMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class Keybinds {
  private static final KeyMapping SKILL_TREE_KEY =
      new KeyMapping(
          "key.display_skill_tree", GLFW.GLFW_KEY_O, "key.categories." + CoFMod.MOD_ID);

  @SubscribeEvent
  public static void registerKeybinds(RegisterKeyMappingsEvent event) {
    event.register(SKILL_TREE_KEY);
  }

  @EventBusSubscriber(modid = CoFMod.MOD_ID, value = Dist.CLIENT)
  private static class KeyEvents {
    @SubscribeEvent
    public static void keyPressed(InputEvent.Key event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.player == null || minecraft.screen != null) return;
      if (event.getKey() == SKILL_TREE_KEY.getKey().getValue()) {
        minecraft.setScreen(
            new SkillTreeScreen(new ResourceLocation(CoFMod.MOD_ID, "main_tree")));
      }
    }
  }
}
