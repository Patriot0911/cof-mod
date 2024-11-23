package com.modding.cof.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class PlayerCapabilitiesRegisterData {
    public Class<? extends ICapabilityProvider> provider;
    public String resourceName;
    public Capability capability;

    public PlayerCapabilitiesRegisterData(
        Class<? extends ICapabilityProvider> provider,
        String resource,
        Capability cap
    ) {
        this.provider = provider;
        this.resourceName = resource;
        this.capability = cap;
    };
};
