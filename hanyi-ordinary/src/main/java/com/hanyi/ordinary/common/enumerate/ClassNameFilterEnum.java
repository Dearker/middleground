package com.hanyi.ordinary.common.enumerate;

import org.springframework.util.ClassUtils;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 10:31 2020/7/25
 */
public enum ClassNameFilterEnum {

    /**
     * 当前
     */
    PRESENT {
        @Override
        public boolean matches(String className, ClassLoader classLoader) {
            return isPresent(className, classLoader);
        }
    },

    /**
     * 不存在
     */
    MISSING {
        @Override
        public boolean matches(String className, ClassLoader classLoader) {
            return !isPresent(className, classLoader);
        }

    };

    public abstract boolean matches(String className, ClassLoader classLoader);

    public static boolean isPresent(String className, ClassLoader classLoader) {
        if (classLoader == null) {
            classLoader = ClassUtils.getDefaultClassLoader();
        }
        try {
            forName(className, classLoader);
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }

    private static Class<?> forName(String className, ClassLoader classLoader) throws ClassNotFoundException {
        if (classLoader != null) {
            return classLoader.loadClass(className);
        }
        return Class.forName(className);
    }

}
