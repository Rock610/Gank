package com.rock.android.gank.util;

import android.os.Handler;

import java.lang.reflect.Field;

public class FieldUtil {
    public static Object getField(Class<?> clazz, Object o, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    public static Field getField(Class<?> clazz, String name) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(name);
            field.setAccessible(true);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return field;
    }

    public static void setField(Class<Handler> clazz, Object target, String name, Object value) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
