package com.modding.cof.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.modding.cof.client.data.ClientSkillsData;
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
    private int[] initCords;

    public SkillBranch(SkillTreeScreen parentScreen, int centerX, int centerY, int buttonSize, Vec2 direction, List<IBaseSkill> branSkills) {
        this.screen = parentScreen;
        this.direction = direction;
        this.buttonSize = buttonSize;
        this.skills = branSkills;
        this.initCords = new int[] { centerX, centerY };
        renderLearnedSkills();
    }

    enum SkillGuiType {
        Learnable,
        Learned,
        Blocked,
        Cursed
    }

    public void renderLearnedSkills() {
        LocalPlayer player = Minecraft.getInstance().player;
        System.out.println(player.getName());
        for(int i = 0; i < skills.size(); i++) {
            IBaseSkill prevSkill = i > 0 ? skills.get(i-1) : null;
            SkillGuiType type = SkillGuiType.Learnable;
            if(prevSkill != null) {
                if(ClientSkillsData.getPlayerSkill(prevSkill.getName()) == null) {
                    continue;
                };
                type = ClientSkillsData.getPlayerSkill(skills.get(i).getName()) == null ?
                    SkillGuiType.Learnable : SkillGuiType.Learned;
            };
            SkillButton previousButton = skillButtons.size() - 1 < 0 ? null : skillButtons.get(skillButtons.size() - 1);
            int[] prevCords = previousButton == null ? new int[] {initCords[0], initCords[1]} : previousButton.getCords();
            int x = (int) (prevCords[0] + direction.x * 2 * buttonSize);
            int y = (int) (prevCords[1] + direction.y * 2 * buttonSize);
            SkillButton skillButton = new SkillButton(
                x, y, buttonSize, buttonSize,
                type,
                skills.get(i),
                this
            );
            skillButtons.add(skillButton);
        };
    };

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
