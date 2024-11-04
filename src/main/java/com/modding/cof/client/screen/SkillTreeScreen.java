package com.modding.cof.client.screen;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SkillTreeScreen extends Screen {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("cof", "textures/gui/skill_tree_background.png");
    public static final int BACKGROUND_SIZE = 2048;

    private float scrollX = 0, scrollY = 0;
    private int screenWidth, screenHeight;
    private float zoomLevel = 1.0f;
    private List<SkillButton> skillButtons = new ArrayList<>();

    public SkillTreeScreen(ResourceLocation skillTreeId) {
        super(Component.literal("Skill Tree"));
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public void init() {
        clearWidgets();
        skillButtons.clear();

        screenWidth = 4 * this.width / 5;
        screenHeight = 4 * this.height / 5;

        int buttonX = (this.width + screenWidth) / 2 - 25;
        int buttonY = (this.height - screenHeight) / 2 + 5;

        addRenderableWidget(new Button(buttonX, buttonY, 20, 20, Component.literal("X"), button -> {
            this.onClose();
        }));

        addSkillButtons();
    }

    private void addSkillButtons() {
        int buttonSize = 20;
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int x = centerX + (i - 2) * 40 - buttonSize / 2;
                int y = centerY + (j - 2) * 40 - buttonSize / 2;
                SkillButton skillButton = new SkillButton(x, y, buttonSize, buttonSize, Component.literal("Skill"));
                skillButtons.add(skillButton);
                addRenderableWidget(skillButton);
            }
        }

        if (skillButtons.size() > 1) {
            for (int i = 0; i < skillButtons.size() - 1; i++) {
                SkillButton button1 = skillButtons.get(i);
                SkillButton button2 = skillButtons.get(i + 1);
                button1.addConnection(button2);
                button2.addConnection(button1);
            }
        }
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackgroundTexture(poseStack);

        int bgLeft = (this.width - BACKGROUND_SIZE) / 2;
        int bgTop = (this.height - BACKGROUND_SIZE) / 2;
        poseStack.pushPose();
        poseStack.translate(bgLeft, bgTop, 0);
        RenderSystem.enableScissor(bgLeft, bgTop, BACKGROUND_SIZE, BACKGROUND_SIZE);

        renderSkills(poseStack);

        RenderSystem.disableScissor();
        poseStack.popPose();

        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    private void renderBackgroundTexture(PoseStack poseStack) {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int adjustedWidth = screenWidth;
        int adjustedHeight = screenHeight;

        int x = centerX - adjustedWidth / 2;
        int y = centerY - adjustedHeight / 2;

        fill(poseStack, 0, 0, this.width, this.height, 0x88000000);

        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        blit(poseStack, x, y, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
    }

    private void renderSkills(PoseStack poseStack) {
        poseStack.pushPose();
        poseStack.translate(scrollX, scrollY, 0);
        poseStack.scale(zoomLevel, zoomLevel, 1.0f);

        for (SkillButton skillButton : skillButtons) {
            for (SkillButton connectedButton : skillButton.getConnectedButtons()) {
                drawLineBetweenButtons(poseStack, skillButton, connectedButton, 0xFFFFFF);
            }
        }

        poseStack.popPose();
    }

    private void drawLineBetweenButtons(PoseStack poseStack, SkillButton button1, SkillButton button2, int color) {
        int startX = (int) ((button1.x + button1.getWidth() / 2) * zoomLevel);
        int startY = (int) ((button1.y + button1.getHeight() / 2) * zoomLevel);
        int endX = (int) ((button2.x + button2.getWidth() / 2) * zoomLevel);
        int endY = (int) ((button2.y + button2.getHeight() / 2) * zoomLevel);

        this.hLine(poseStack, startX, endX, startY, color);
        this.vLine(poseStack, startX, startY, endY, color);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta > 0) {
            zoomLevel = Math.min(zoomLevel + 0.1f, 2.0f);
        } else if (delta < 0) {
            zoomLevel = Math.max(zoomLevel - 0.1f, 0.5f);
        }
        return true;
    }
}
