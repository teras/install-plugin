package is.yot.gradle.clsmng;

import java.util.Objects;

public class MethodArg {
    public final Class<?> argType;
    public final Object argValue;

    public MethodArg(Class<?> argType, Object argValue) {
        this.argType = Objects.requireNonNull(argType, "Argument type can't be null");
        this.argValue = argValue;
    }

    public static MethodArg arg(Class<?> argType, Object argValue) {
        return new MethodArg(argType, argValue);
    }
}
