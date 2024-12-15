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
    private final int skillAmount;
    private int doneSkills = 1;

    public SkillBranch(SkillTreeScreen parentScreen, int centerX, int centerY, int buttonSize, Vec2 direction,
            int skillAmount) {
        this.screen = parentScreen;
        this.direction = direction;
        this.buttonSize = buttonSize;
        this.skillAmount = skillAmount;

        int x = (int) (centerX + direction.x * 2 * buttonSize);
        int y = (int) (centerY + direction.y * 2 * buttonSize);

        SkillButton skillButton = new SkillButton(x, y, buttonSize, buttonSize,
                Component.literal("Skill 1"), this);
        skillButtons.add(skillButton);
    }

    public SkillButton addButton() {
        if (doneSkills >= skillAmount) {
            return null;
        }

        SkillButton previousButton = skillButtons.get(skillButtons.size() - 1);
        int[] prevCords = previousButton.getCords();

        int x = (int) (prevCords[0] + direction.x * 2 * buttonSize);
        int y = (int) (prevCords[1] + direction.y * 2 * buttonSize);

        String buttonText = "Skill " + (skillButtons.size() + 1);
        SkillButton skillButton = new SkillButton(x, y, buttonSize, buttonSize, Component.literal(buttonText), this);

        skillButtons.add(skillButton);
        doneSkills++;
        return skillButton;
    }

    public void addButtonsToScreen() {
        for (SkillButton button : skillButtons) {
            screen.addWidgetButton(button);
        }
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        for (int i = 0; i < skillButtons.size() - 2; i++) {
            SkillButton button1 = skillButtons.get(i);
            SkillButton button2 = skillButtons.get(i + 1);

            int[] cords1 = button1.getCords();
            int[] cords2 = button2.getCords();

            System.out.println(
                    "Drawing line from " + cords1[0] + ", " + cords1[1] + " to " + cords2[0] + ", " + cords2[1]);
            drawLine(poseStack, cords1[0], cords1[1], cords2[0], cords2[1]);
        }
        for (SkillButton button : skillButtons) {
            button.renderButton(poseStack, mouseX, mouseY, partialTick);
        }

    }

    public SkillTreeScreen getScreen() {
        return screen;
    }

    private void drawLine(PoseStack poseStack, int x1, int y1, int x2, int y2) {
        //TODO: Implement this method
    }
}
