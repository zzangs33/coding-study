package com.coding.utils;

import java.lang.reflect.Array;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CastUtil {
    private CastUtil() {
    }

    public static Object cast(Object obj, Class<?> type) throws ClassCastException {
        if (obj == null) return null;
        if (isBasicType(type)) {
            if (type.equals(obj.getClass()) || !isJavaBasicType(type)) return type.cast(obj);
            if (instanceOf(type, int.class))
                return ((Number) obj).intValue();
            if (instanceOf(type, char.class))
                return ((Character) obj).charValue();
            if (instanceOf(type, short.class))
                return ((Number) obj).shortValue();
            if (instanceOf(type, long.class))
                return ((Number) obj).longValue();
            if (instanceOf(type, float.class))
                return ((Number) obj).floatValue();
            if (instanceOf(type, double.class))
                return ((Number) obj).doubleValue();
            if (instanceOf(type, boolean.class))
                return ((Boolean) obj).booleanValue();
            throw new ClassCastException("Cannot cast obj: " + obj.getClass() + " to type: " + type);
        }
        if (!instanceOf(obj, Iterable.class) && !obj.getClass().isArray()
                && !instanceOf(type, Iterable.class) && !type.isArray())
            throw new ClassCastException("Cannot cast obj: " + obj.getClass() + " to type: " + type);
        List<Object> list = new ArrayList<>();
        try {
            for (Object item : (Iterable) obj)
                list.add(cast(item, type.getComponentType()));
        } catch (ClassCastException e) {
            for (int i = 0; i < Array.getLength(obj); i++)
                list.add(cast(Array.get(obj, i), type.getComponentType()));
        }
        if (type.isArray()) {
            Object arr = Array.newInstance(type.getComponentType(), list.size());
            for (int i = 0; i < list.size(); i++)
                Array.set(arr, i, list.get(i));
            return type.cast(arr);
        }
        return type.cast(list);
    }

    private static boolean isJavaBasicType(Object objOrClz) {
        if (objOrClz == null) return false;
        return instanceOf(objOrClz, int.class) ||
                instanceOf(objOrClz, char.class) ||
                instanceOf(objOrClz, short.class) ||
                instanceOf(objOrClz, long.class) ||
                instanceOf(objOrClz, float.class) ||
                instanceOf(objOrClz, double.class) ||
                instanceOf(objOrClz, boolean.class);
    }

    private static boolean isBasicType(Object objOrClz) {
        if (objOrClz == null) return false;
        return isJavaBasicType(objOrClz) ||
                instanceOf(objOrClz, Number.class) ||
                instanceOf(objOrClz, Character.class) ||
                instanceOf(objOrClz, Boolean.class) ||
                instanceOf(objOrClz, String.class) ||
                instanceOf(objOrClz, Date.class) ||
                instanceOf(objOrClz, TemporalAccessor.class);
    }

    private static boolean instanceOf(Object objOrClz, Object superObjOrClz) {
        if (objOrClz == null || superObjOrClz == null) return false;
        Class<?> clz = objOrClz instanceof Class ? (Class) objOrClz : objOrClz.getClass();
        Class<?> superClz = superObjOrClz instanceof Class ? (Class) superObjOrClz : superObjOrClz.getClass();
        return clz.equals(superClz) || superClz.equals(clz.getSuperclass()) || instanceOf(clz.getSuperclass(), superClz);
    }
}
