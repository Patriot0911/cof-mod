package com.modding.cof.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.modding.cof.skills.IBaseSkill;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

public class SkillBranch {
    private final List<SkillButton> skillButtons = new ArrayList<>();
    private final SkillTreeScreen screen;
    private final Vec2 direction;
    private final int buttonSize;
    private List<IBaseSkill> skills;
    private int doneSkills = 1;

    public SkillBranch(SkillTreeScreen parentScreen, int centerX, int centerY, int buttonSize, Vec2 direction, List<IBaseSkill> branSkills) {
        this.screen = parentScreen;
        this.direction = direction;
        this.buttonSize = buttonSize;
        this.skills = branSkills;
        renderLearnedSkills();
    }

    public void renderLearnedSkills() {
        LocalPlayer player = Minecraft.getInstance().player; // TODO: send skills data to client side
        System.out.println(player.getName());
        for(int i = 0; i < skills.size(); i++) {
            System.out.println(skills.get(i).getName());
            // if(skills.get(i).getName() == null) { // check if player has
            //     break;
            // };
            SkillButton previousButton = skillButtons.size() - 1 < 0 ? null : skillButtons.get(skillButtons.size() - 1);
            int[] prevCords = previousButton == null ? new int[] {0, 0} : previousButton.getCords();
            int x = (int) (prevCords[0] + direction.x * 2 * buttonSize);
            int y = (int) (prevCords[1] + direction.y * 2 * buttonSize);
            SkillButton skillButton = new SkillButton(
                x, y, buttonSize, buttonSize,
                Component.literal(
                    I18n.exists(skills.get(i).getLangName()) ? I18n.get(skills.get(i).getLangName()) : skills.get(i).getLangName()
                ),
                this
            );
            skillButtons.add(skillButton);
        };
    };

    public SkillButton addButton() {
        if (doneSkills >= skills.size()) {
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
        for (SkillButton button : skillButtons) {
            button.renderButton(poseStack, mouseX, mouseY, partialTick);
        }
    }

    public SkillTreeScreen getScreen() {
        return screen;
    }
}
