package is.yot.gradle.shadows;

import is.yot.gradle.clsmng.Shadow;
import is.yot.gradle.clsmng.ShadowClass;

import java.util.Collection;

public class KotlinNativeTargetS extends Shadow {

    public static final ShadowClass CLASS = new ShadowClass("org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget");

    public KotlinNativeTargetS(Object data) {
        super(data, CLASS);
    }

    public KonanTargetS getKonanTarget() {
        return new KonanTargetS(get("konanTarget"));
    }

    public Collection<ExecutableS> getBinaries() {
        return getCollection("binaries", ExecutableS.CLASS, (sc, data) -> new ExecutableS(data));
    }
}
