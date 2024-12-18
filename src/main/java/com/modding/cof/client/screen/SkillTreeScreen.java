package com.modding.cof.client.screen;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;

public class SkillTreeScreen extends Screen {
    public final int windowPadding = 3;
    public final int BACKGROUND_SIZE = 2048;
    public final float minScrollX = 0, minScrollY = 0;

    private List<SkillBranch> skillBranchList = new ArrayList<>();

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("cof",
            "textures/gui/skill_tree_background.png");
    public int window_startX, window_startY;
    public int screenWidth, screenHeight;

    public float scrollX = 0, scrollY = 0;
    private boolean dragging = false;
    private float zoomLevel = 1.0f;

    public String toolTip = "";

    public SkillTreeScreen(ResourceLocation skillTreeId) {
        super(Component.literal("Skill Tree"));
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public void init() {
        clearWidgets();

        this.screenWidth = 4 * this.width / 5;
        this.screenHeight = 4 * this.height / 5;

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int buttonSize = 20;
        int branchSize = 5;

        float sin60 = (float) Math.sin(Math.PI / 3);
        float sin30 = (float) Math.sin(Math.PI / 6);

        Vec2[] directions = { 
            new Vec2(sin60, sin30),
            new Vec2(0, 1),
            new Vec2(-sin60, sin30),
            new Vec2(-sin60, -sin30),
            new Vec2(0, -1),
            new Vec2(sin60, -sin30)
        };

        for (Vec2 direction : directions) {
            SkillBranch branch = new SkillBranch(this, centerX, centerY, buttonSize, direction, branchSize);
            skillBranchList.add(branch);
            branch.addButtonsToScreen();
        }
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackgroundTexture(poseStack);
        for (SkillBranch skillBranch : skillBranchList) {
            skillBranch.render(poseStack, mouseX, mouseY, partialTick);
        }
        super.render(poseStack, mouseX, mouseY, partialTick);
    
        if (this.toolTip != null) {
            this.renderTooltip(poseStack, Component.literal(toolTip), mouseX, mouseY);
            this.toolTip = null;
        }
    }

    private void renderBackgroundTexture(PoseStack poseStack) {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        this.window_startX = centerX - screenWidth / 2;
        this.window_startY = centerY - screenHeight / 2;

        fill(poseStack, 0, 0, this.width, this.height, 0x88000000);

        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        blit(poseStack, window_startX, window_startY, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == 0 && dragging) {
            scrollX -= deltaX;
            scrollY -= deltaY;

            // // Запобігаємо виходу за межі мапи
            // scrollX = Mth.clamp(scrollX, -(int)((-BACKGROUND_SIZE + screenWidth)*1.5),
            // 0);
            // scrollY = Mth.clamp(scrollY, (int)((-BACKGROUND_SIZE + screenHeight)*1.5),
            // 0);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
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

    public void addWidgetButton(SkillButton button) {
        this.addRenderableWidget(button);
    }

    public void addSkill(SkillBranch branch) {
        SkillButton newButton = branch.addButton();
        if (newButton != null) {
            addRenderableWidget(newButton);
        }
    }
}
