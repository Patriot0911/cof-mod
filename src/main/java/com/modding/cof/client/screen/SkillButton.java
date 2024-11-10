package com.modding.cof.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SkillButton extends Button {
    private static final ResourceLocation ICON_TEXTURE = new ResourceLocation("cof", "textures/gui/skill_icon.jpg");
    private List<SkillButton> connectedButtons = new ArrayList<>();

    public SkillButton(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message, button -> {
            System.out.println("Skill button clicked!");
        });
    }

    public void addConnection(SkillButton button) {
        if (!connectedButtons.contains(button)) {
            connectedButtons.add(button);
        }
    }

    public List<SkillButton> getConnectedButtons() {
        return connectedButtons;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        boolean isHovered = isHoveredOrFocused();

        fill(poseStack, x, y, x + width, y + height, isHovered ? 0xA0000000 : 0x80000000);

        RenderSystem.setShaderTexture(0, ICON_TEXTURE);
        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        blit(poseStack, 0, 0, 0, 0, width, height, width, height);
        poseStack.popPose();

        if (isHovered) {
            drawCenteredString(poseStack, mc.font, getMessage(), x + width / 2, y + (height - 8) / 2, 0xFFFFFF);
        }
    }
}
