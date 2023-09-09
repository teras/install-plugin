package is.yot.gradle.clsmng;

import is.yot.gradle.InstallException;

public final class ShadowClass {
    private final Class<?> cls;

    public ShadowClass(String className) {
        try {
            this.cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new InstallException("Unable to locate class " + className);
        }
    }

    public boolean isInstance(Object data) {
        return cls.isAssignableFrom(data.getClass());
    }

    public void dumpHierarchy() {
        Class<?> current = cls;
        while (current != null) {
            System.out.println(current.getName());
            current = current.getSuperclass();
        }
    }

    public String getName() {
        return cls.getName();
    }

    public <T> T callMethod(Object data, String methodName, MethodArg... arguments) {
        Class<?>[] types = new Class<?>[arguments == null ? 0 : arguments.length];
        Object[] values = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = arguments[i].argType;
            values[i] = arguments[i].argValue;
        }
        try {
            return (T) cls.getMethod(methodName, types).invoke(data, values);
        } catch (Exception e) {
            throw new InstallException("Unable to call method " + methodName + " in class " + cls.getName());
        }
    }
}
