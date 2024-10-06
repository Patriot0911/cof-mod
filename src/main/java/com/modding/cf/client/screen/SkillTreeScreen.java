package com.modding.cf.client.screen;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SkillTreeScreen extends Screen {
    private static final ResourceLocation BACKGROUND_TEXTURE = 
        new ResourceLocation("examplemod", "textures/gui/skill_tree_background.png");

    private float scrollX, scrollY;
    private int screenWidth, screenHeight;

    public SkillTreeScreen(ResourceLocation skillTreeId) {
        super(Component.literal("Skill Tree"));
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public void init() {
        clearWidgets();

        screenWidth = 4 * this.width / 5;
        screenHeight = 4 * this.height / 5;

        int buttonX = (this.width + screenWidth) / 2 - 25;
        int buttonY = (this.height - screenHeight) / 2 + 5;

        addRenderableWidget(new Button(buttonX, buttonY, 20, 20, Component.literal("X"), button -> {
            this.onClose();
        }));

        addSkillButtons();
    }

    private void addSkillButtons() { }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackgroundTexture(poseStack);

        renderSkills(poseStack, mouseX, mouseY, partialTick);

        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    private void renderBackgroundTexture(PoseStack poseStack) {
        int x = (this.width - screenWidth) / 2;
        int y = (this.height - screenHeight) / 2;

        fill(poseStack, 0, 0, this.width, this.height, 0x88000000);

        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        blit(poseStack, x, y, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
    }

    private void renderSkills(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        poseStack.pushPose();
        poseStack.translate(scrollX, scrollY, 0);
        poseStack.popPose();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
