package com.modding.cof.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SkillButton extends Button {
    private static final ResourceLocation ICON_TEXTURE = new ResourceLocation("cof", "textures/gui/skill_icon.jpg");
    private SkillTreeScreen parentScreen;
    private int startX;
    private int startY;

    public SkillButton(int x, int y, int width, int height, Component message, SkillTreeScreen screen) {
        super(x, y, width, height, message, button -> {
            System.out.println("Skill button clicked!");
        });
        this.startX = x;
        this.startY = y;
        this.parentScreen = screen;
    }

    @Override
    public void onClick(double p_93371_, double p_93372_) {
        // ++parentScreen.doneSkills;
        parentScreen.addSkill();
        super.onClick(p_93371_, p_93372_);
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        boolean isHovered = isHoveredOrFocused();

        x = (int) (startX-parentScreen.scrollX);
        y = (int) (startY-parentScreen.scrollY);

        float paddingTop = (float) (parentScreen.screenHeight*0.0349744686);
        float paddingLeft = (float) (parentScreen.screenWidth*0.0170898438);

        RenderSystem.enableScissor(
            (int) ((parentScreen.window_startX+paddingLeft)*mc.getWindow().getGuiScale()),
            (int) ((parentScreen.window_startY+paddingTop)*mc.getWindow().getGuiScale()),
            (int) ((parentScreen.screenWidth-paddingLeft*2)*mc.getWindow().getGuiScale()),
            (int) ((parentScreen.screenHeight-paddingTop*2)*mc.getWindow().getGuiScale())
        );
        fill(poseStack, x, y, x + width, y + height, isHovered ? 0xA0000000 : 0x80000000);

        RenderSystem.setShaderTexture(0, ICON_TEXTURE);
        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        blit(poseStack, 0, 0, 0, 0, width, height, width, height);
        poseStack.popPose();
        if (isHovered) {
            parentScreen.toolTip = "This is a tooltip";
            // .renderTooltip(poseStack, Component.literal("This is a tooltip"), mouseX, mouseY);
            // renderTooltip(poseStack, mouseX, mouseY);
            drawCenteredString(poseStack, mc.font, getMessage(), x + width / 2, y + (height - 8) / 2, 0xFFFFFF);
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
