package com.modding.cof.events.capabilities;

import com.modding.cof.capabilities.level.PlayerLevelProvider;
import com.modding.cof.capabilities.playerPoints.PlayerPointsProvider;
import com.modding.cof.capabilities.playerSkills.PlayerSkillsProvider;
import com.modding.cof.capabilities.playerXP.PlayerXpProvider;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.LazyOptional;

public class InitCapabilitiesTest {
    private void assertCapabilityAttached(GameTestHelper helper, LazyOptional<?> capability, String capabilityName) {
        if (!capability.isPresent()) {
            helper.fail(capabilityName + " attached!");
        }
    }

    @GameTest(templateNamespace = "cof", template = "empty")
    public void testCapabilitiesAttached(GameTestHelper helper) {
        ServerPlayer player = (ServerPlayer) helper.makeMockPlayer();

        player.reviveCaps();

        assertCapabilityAttached(helper, player.getCapability(PlayerLevelProvider.PLAYER_LEVEL), "PlayerLevelProvider");
        assertCapabilityAttached(helper, player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS),
                "PlayerSkillsProvider");
        assertCapabilityAttached(helper, player.getCapability(PlayerXpProvider.PLAYER_XP), "PlayerXpProvider");
        assertCapabilityAttached(helper, player.getCapability(PlayerPointsProvider.PLAYER_SKILL_POINTS),
                "PlayerPointsProvider");
    }
}
