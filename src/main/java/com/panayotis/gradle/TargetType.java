package com.panayotis.gradle;

import java.io.File;
import java.util.Arrays;

public enum TargetType {
    LINUX_X64("linux-x86_64", "linux"),
    LINUX_ARM64("linux-aarch64", "aarch64.linux"),
    MACOS_X64("darwin-x86_64", "macintel"),
    MACOS_ARM64("darwin-arm64", "macarm"),
    MINGW_X64("windows-x86_64", "64.exe");
    private final String loc;
    private final String ext;

    TargetType(String loc, String ext) {
        this.loc = loc;
        this.ext = ext;
    }

    public static TargetType byName(String name) {
        String uname = name.toUpperCase();
        return Arrays.stream(TargetType.values()).filter(t -> t.name().equals(uname)).findFirst().orElseThrow(() -> new InstallException("Invalid target name: " + name));
    }

    public File getAllFile(String baseName, File installDir) {
        return new File(installDir, "all/" + baseName + "." + ext);
    }

    public File getLinkFile(String baseName, File installDir) {
        return new File(installDir, loc + "/" + baseName + (ext.contains("exe") ? ".exe" : ""));
    }
}
