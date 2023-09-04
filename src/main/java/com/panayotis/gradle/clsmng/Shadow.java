package com.panayotis.gradle.clsmng;

import com.panayotis.gradle.InstallException;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Shadow {
    private final Object data;
    private final ShadowClass cls;

    public Shadow(Object data, ShadowClass shadowClass) {
        if (shadowClass == null)
            throw new InstallException("Class shouuln't be missing");
        else
            this.cls = shadowClass;
        if (data == null)
            throw new InstallException("Unable to find item of class " + cls.getName());
        if (!cls.isInstance(data))
            throw new InstallException("Item of class " + data.getClass().getName() + " is not of type " + cls.getName());
        this.data = data;
    }

    public ShadowClass getShadowClass() {
        return cls;
    }

    protected <T> T get(String property) {
        return cls.callMethod(data, "get" + capitalize(property));
    }

    protected <T extends Shadow> Collection<T> getCollection(String property, ShadowClass otherCls, BiFunction<ShadowClass, Object, T> constructor) {
        return ((Collection<Object>) get(property))
                .stream()
                .map(t -> {
                    if (t == null) return null;
                    T instance = otherCls.isInstance(t) ? constructor.apply(otherCls, t) : null;
                    if (instance == null) return null;
                    if (!instance.getShadowClass().equals(otherCls))
                        throw new InstallException("Constructor of Shadow " + otherCls.getName() + " produces items of wrong type: " + instance.getShadowClass().getName());
                    return instance;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static String capitalize(String txt) {
        if (txt == null) return null;
        if (txt.length() <= 1) return txt.toUpperCase();
        return txt.substring(0, 1).toUpperCase() + txt.substring(1);
    }
}
