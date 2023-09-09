package is.yot.gradle.shadows;

import is.yot.gradle.clsmng.Shadow;
import is.yot.gradle.clsmng.ShadowClass;

import java.util.Collection;

public class KotlinMultiplatformExtensionS extends Shadow {
    public static final ShadowClass CLASS = new ShadowClass("org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension");

    public KotlinMultiplatformExtensionS(Object data) {
        super(data, CLASS);
    }

    public Collection<KotlinNativeTargetS> getNativeTargets() {
        return getCollection("targets", KotlinNativeTargetS.CLASS, (sc, data) -> new KotlinNativeTargetS(data));
    }
}
