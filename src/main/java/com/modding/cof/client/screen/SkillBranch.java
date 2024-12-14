package com.modding.cof.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

public class SkillBranch {
    private final List<SkillButton> skillButtons = new ArrayList<>();
    private final SkillTreeScreen screen;
    private final Vec2 direction;
    private final int buttonSize;

    public SkillBranch(SkillTreeScreen parentScreen, int centerX, int centerY, int buttonSize, Vec2 direction) {
        this.screen = parentScreen;
        this.direction = direction;
        this.buttonSize = buttonSize;

        int x = (int) (centerX + direction.x * 2 * buttonSize - buttonSize / 2);
        int y = (int) (centerY + direction.y * 2 * buttonSize - buttonSize / 2);

        System.out.println("x: " + direction.x + ", y: " + direction.y);

        SkillButton skillButton = new SkillButton(x, y, buttonSize, buttonSize,
                Component.literal("Skill 1"), parentScreen);
        skillButtons.add(skillButton);
    }

    public SkillButton addButton() {
        SkillButton previousButton = skillButtons.get(skillButtons.size() - 1);

        int x = (int) (previousButton.x + direction.x * 2 * buttonSize);
        int y = (int) (previousButton.y + direction.y * 2 * buttonSize);

        String buttonText = "Skill " + (skillButtons.size() + 1);
        SkillButton skillButton = new SkillButton(x, y, buttonSize, buttonSize, Component.literal(buttonText), screen);

        skillButtons.add(skillButton);
        return skillButton;
    }

    public void addButtonsToScreen() {
        for (SkillButton button : skillButtons) {
            screen.addWidgetButton(button);
        }
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        for (SkillButton button : skillButtons) {
            button.renderButton(poseStack, mouseX, mouseY, partialTick);
        }
    }
}