package com.modding.cof.client.screen;

import com.modding.cof.CoFMod;
import com.modding.cof.client.screen.SkillBranch.SkillGuiType;
import com.modding.cof.network.NetworkManager;
import com.modding.cof.network.packet.skills.LearnSkillC2S;
import com.modding.cof.skills.IBaseSkill;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.language.I18n;
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
            // System.out.println(gSkill.getName());
            type = SkillGuiType.Learned;
            NetworkManager.sendToServer(
                new LearnSkillC2S(gSkill.getName())
            );
        };
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

        int borderColor = type == SkillGuiType.Learned ? 0x00FFFFFF : 0xA0000000;
        int borderThickness = 2;

        RenderSystem.enableScissor(
            (int) ((parentScreen.window_startX + paddingLeft) * mc.getWindow().getGuiScale()),
            (int) ((parentScreen.window_startY + paddingTop) * mc.getWindow().getGuiScale()),
            (int) ((parentScreen.screenWidth - paddingLeft * 2) * mc.getWindow().getGuiScale()),
            (int) ((parentScreen.screenHeight - paddingTop * 2) * mc.getWindow().getGuiScale())
        );
        RenderSystem.setShaderTexture(
            0,
            new ResourceLocation(CoFMod.MOD_ID,
                type == SkillGuiType.Learnable ? "textures/skills/bg_skill_to_learn.png" : "textures/skills/bg_skill_learned.png"
            )
        );
        poseStack.pushPose();
        poseStack.translate(x-borderThickness, y-borderThickness, 0);
        blit(poseStack,
            0, 0,
            0, 0, 0,
            this.width + borderThickness*2,
            this.height + borderThickness*2,
            this.width + borderThickness*2,
            this.height + borderThickness*2
        );
        poseStack.popPose();
        fill(poseStack, x, y, x + width, y + height, isHovered ? 0xA0000000 : 0x80000000);

        RenderSystem.setShaderTexture(0, gSkill.getIconResource());
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

