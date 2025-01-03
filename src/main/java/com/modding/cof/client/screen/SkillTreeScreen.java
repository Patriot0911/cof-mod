package com.modding.cof.client.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.modding.cof.skills.Armor;
import com.modding.cof.skills.ArrowRestore;
import com.modding.cof.skills.IBaseSkill;
import com.modding.cof.skills.LvlUpHeal;
import com.modding.cof.skills.Starvation;
import com.modding.cof.skills.ToolsRepair;
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

    private List<List<IBaseSkill>> branches = new ArrayList<>(
        Arrays.asList(
            new ArrayList<>(
                Arrays.asList(
                    new Starvation()
                )
            ),
            new ArrayList<>(
                Arrays.asList(
                    new LvlUpHeal()
                )
            ),
            new ArrayList<>(
                Arrays.asList(
                    new ArrowRestore()
                )
            ),
            new ArrayList<>(
                Arrays.asList(
                    new Armor()
                )
            ),
            new ArrayList<>(
                Arrays.asList(
                    new ToolsRepair()
                )
            )
        )
    );

    public String toolTip = "";

    public SkillTreeScreen(ResourceLocation skillTreeId) {
        super(Component.literal("Skill Tree"));
        this.minecraft = Minecraft.getInstance();
    }

    public static List<Vec2> generateDirections(int bCount, float radius, int centerX, int centerY, double startAngle) {
        List<Vec2> directions = new ArrayList<>();
        double angleStep = 2 * Math.PI / bCount;
        for(int i = 0; i < bCount; i++) {
            double angle = i * angleStep + startAngle;
            float x = (float) (Math.cos(angle)) * radius;
            float y = (float) (Math.sin(angle)) * radius;
            directions.add(new Vec2(x, y));
        };
        return directions;
    };

    @Override
    public void init() {
        clearWidgets();

        this.screenWidth = 4 * this.width / 5;
        this.screenHeight = 4 * this.height / 5;

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int buttonSize = 20;

        float r = 1;
        List<Vec2> directions = generateDirections(branches.size(), r, centerX, centerY, 3*Math.PI/2);

        for(int i = 0; i < branches.size(); i++) {
            Vec2 direction = directions.get(i);
            List<IBaseSkill> branchSkills = branches.get(i);
            SkillBranch branch = new SkillBranch(
                this,
                centerX,
                centerY,
                buttonSize,
                direction,
                branchSkills
            );
            skillBranchList.add(branch);
            branch.addButtonsToScreen();
        };
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

    @Override
    public void resize(Minecraft p_96575_, int p_96576_, int p_96577_) {
        super.resize(p_96575_, p_96576_, p_96577_);
        skillBranchList.clear();
    }

    public void addWidgetButton(SkillButton button) {
        this.addRenderableWidget(button);
    }
}
