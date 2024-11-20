package com.modding.cof.skills;

public class BaseSkill implements IBaseSkill {
    protected int maxLvl;
    protected String name;

    public BaseSkill(String sName, int mLvl) {
        this.maxLvl = mLvl;
        this.name = sName;
    }

    @Override
    public int getMaxLvl() {
        return this.maxLvl;
    }
    @Override
    public String getName() {
        return this.name;
    }
}
