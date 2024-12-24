package com.modding.cof.capabilities.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.minecraft.nbt.CompoundTag;

class PlayerLevelTest {

    private PlayerLevel playerLevel;

    @BeforeEach
    void setUp() {
        playerLevel = new PlayerLevel();
    }

    @Test
    void testGetLevel() {
        assertEquals(0, playerLevel.getLevel(), "Initial level should be 0");
    }

    @Test
    void testAddLevel() {
        int newLevel = playerLevel.addLevel(10);
        assertEquals(10, newLevel, "Level should be updated to 10");

        newLevel = playerLevel.addLevel(995);
        assertEquals(1000, newLevel, "Level should not exceed MAX_LEVEL (1000)");
    }

    @Test
    void testXpForNextLvl() {
        int xpForNext = playerLevel.xpForNextLvl(-1);
        assertEquals(57, xpForNext, "XP for next level at level 0 should be 57");

        playerLevel.addLevel(10);
        xpForNext = playerLevel.xpForNextLvl(-1);
        assertEquals(377, xpForNext, "XP for next level at level 10 should be 377");

        xpForNext = playerLevel.xpForNextLvl(20);
        assertEquals(697, xpForNext, "XP for level 20 should be 697");
    }

    @Test
    void testCopyFrom() {
        PlayerLevel source = new PlayerLevel();
        source.addLevel(50);

        playerLevel.copyFrom(source);
        assertEquals(50, playerLevel.getLevel(), "Level should be copied from source");
    }

    @Test
    void testSaveNBTData() {
        CompoundTag mockTag = mock(CompoundTag.class);

        playerLevel.addLevel(25);
        playerLevel.saveNBTData(mockTag);

        verify(mockTag).putInt("pl_level", 25);
    }

    @Test
    void testLoadNBTData() {
        CompoundTag mockTag = mock(CompoundTag.class);
        when(mockTag.getInt("pl_level")).thenReturn(75);

        playerLevel.loadNBTData(mockTag);

        assertEquals(75, playerLevel.getLevel(), "Level should be loaded from NBT data");
    }
}
