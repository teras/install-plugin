package com.panayotis.gradle.shadows;

import com.panayotis.gradle.clsmng.Shadow;
import com.panayotis.gradle.clsmng.ShadowClass;

public class NativeBuildTypeS extends Shadow {
    public static ShadowClass CLASS = new ShadowClass("org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType");

    public NativeBuildTypeS(Object data) {
        super(data, CLASS);
    }

    public boolean isDebuggable() {
        return get("debuggable");
    }
}
