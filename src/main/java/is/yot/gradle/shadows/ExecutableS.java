package is.yot.gradle.shadows;

import is.yot.gradle.clsmng.Shadow;
import is.yot.gradle.clsmng.ShadowClass;

import java.io.File;

public class ExecutableS extends Shadow {
    public static final ShadowClass CLASS = new ShadowClass("org.jetbrains.kotlin.gradle.plugin.mpp.Executable");

    public ExecutableS(Object data) {
        super(data, CLASS);
    }

    public File getOutputFile() {
        return get("outputFile");
    }

    public String getBaseName() {
        return get("baseName");
    }

    public NativeBuildTypeS getBuildType() {
        return new NativeBuildTypeS(get("buildType"));
    }
}
