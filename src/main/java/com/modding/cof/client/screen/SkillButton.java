package com.modding.cof.client.screen;

import java.util.HashMap;
import java.util.Map;

import com.modding.cof.client.data.ClientSkillsData;
import com.modding.cof.client.data.subClasses.ClientLocalSkill;
import com.modding.cof.client.screen.SkillBranch.SkillGuiType;
import com.modding.cof.skills.IBaseSkill;
import com.modding.cof.skills.LvlUpHeal;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SkillButton extends Button {
    private final SkillBranch parentBranch;
    private static final ResourceLocation ICON_TEXTURE = new ResourceLocation("cof", "textures/gui/skill_icon.jpg");
    private int startX;
    private int startY;
    private SkillBranch.SkillGuiType type;
    private IBaseSkill gSkill;

    public SkillButton(int x, int y, int width, int height, SkillBranch.SkillGuiType type, IBaseSkill skill, SkillBranch branch) {
        super(x, y, width, height, null, button -> {});
        this.type = type;
        this.parentBranch = branch;
        this.startX = x;
        this.gSkill = skill;
        this.startY = y;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
        if(type == SkillGuiType.Learnable) {
            type = SkillGuiType.Learned;
            IBaseSkill skill = new LvlUpHeal();
            // test way
            Map<String, ClientLocalSkill> map = new HashMap<>();
            map.put(
                skill.getName(),
                new ClientLocalSkill(1, "")
            );
            ClientSkillsData.set(map);
        } else {

        };
        // System.out.println("Skill button clicked!");
        // SkillTreeScreen screen = parentBranch.getScreen();
    }

    public int[] getCords() {
        return new int[] { this.startX, this.startY };
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        boolean isHovered = isHoveredOrFocused();

        SkillTreeScreen parentScreen = parentBranch.getScreen();

        x = (int) (startX - parentScreen.scrollX);
        y = (int) (startY - parentScreen.scrollY);

        float paddingTop = (float) (parentScreen.screenHeight * 0.0349744686);
        float paddingLeft = (float) (parentScreen.screenWidth * 0.0170898438);

        int borderColor = type == SkillGuiType.Learned ? 0x00FF00 : 0xA0000000;
        int borderThickness = 5;

        fill(poseStack, x-borderThickness, y-borderThickness, this.x+this.width + borderThickness, this.y+this.height + borderThickness, borderColor);

        RenderSystem.enableScissor(
                (int) ((parentScreen.window_startX + paddingLeft) * mc.getWindow().getGuiScale()),
                (int) ((parentScreen.window_startY + paddingTop) * mc.getWindow().getGuiScale()),
                (int) ((parentScreen.screenWidth - paddingLeft * 2) * mc.getWindow().getGuiScale()),
                (int) ((parentScreen.screenHeight - paddingTop * 2) * mc.getWindow().getGuiScale()));
        fill(poseStack, x, y, x + width, y + height, isHovered ? 0xA0000000 : 0x80000000);

        RenderSystem.setShaderTexture(0, ICON_TEXTURE);
        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        blit(poseStack, 0, 0, 0, 0, width, height, width, height);
        poseStack.popPose();
        if (isHovered) {
            parentScreen.toolTip = I18n.exists(gSkill.getLangName()) ? I18n.get(gSkill.getLangName()) : gSkill.getLangName();

            // .renderTooltip(poseStack, Component.literal("This is a tooltip"), mouseX,
            // mouseY);
            // renderTooltip(poseStack, mouseX, mouseY);
            // drawCenteredString(poseStack, mc.font, getMessage(), x + width / 2, y + (height - 8) / 2, 0xFFFFFF);
        }
        RenderSystem.disableScissor();
    }

    private void renderTooltip(PoseStack poseStack, int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getInstance();

        String tooltipText = "This is a skill tooltip!";
        int tooltipWidth = mc.font.width(tooltipText) + 6;
        int tooltipHeight = 12;

        int tooltipX = mouseX + 10;
        int tooltipY = mouseY + 10;

        fill(poseStack, tooltipX, tooltipY, tooltipX + tooltipWidth, tooltipY + tooltipHeight, 0xCC000000);

        mc.font.draw(poseStack, tooltipText, tooltipX + 3, tooltipY + 3, 0xFFFFFF);
    }
}

