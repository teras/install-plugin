package is.yot.gradle.shadows;

import is.yot.gradle.clsmng.Shadow;
import is.yot.gradle.clsmng.ShadowClass;

public class KonanTargetS extends Shadow {
    public static final ShadowClass CLASS = new ShadowClass("org.jetbrains.kotlin.konan.target.KonanTarget");

    public <T> KonanTargetS(Object data) {
        super(data, CLASS);
    }

    public String getName() {
        return get("name");
    }
}
