package com.coding.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.temporal.TemporalAccessor;
import java.util.*;

public class JsonUtil {
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("\"yyyy-MM-dd HH:mm:ss\"");

    private JsonUtil() {
    }

    public static String stringify(Object obj) {
        if (obj == null) return "null";
        if (obj instanceof Number || obj instanceof Boolean) return obj.toString();
        if (obj instanceof String) return "\"" + obj + "\"";
        if (obj instanceof Date || obj instanceof TemporalAccessor) {
            if (obj instanceof Date) return dateFormatter.format((Date) obj);
            return dateFormatter.format(obj);
        }
        if (obj.getClass().isArray()) {
            List<Object> list = new ArrayList<>();
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++)
                list.add(Array.get(obj, i));
            obj = list;
        }
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        if (obj instanceof Iterable) {
            builder.append("[");
            for (Object o : ((Iterable) obj)) {
                if (first) first = false;
                else builder.append(", ");
                builder.append(stringify(o));
            }
            builder.append("]");
        } else {
            builder.append("{");
            if (obj instanceof Map) {
                for (Object entry : ((Map) obj).entrySet()) {
                    if (first) first = false;
                    else builder.append(", ");
                    Object key = ((Map.Entry) entry).getKey();
                    if (key == null) key = "";
                    key = "\"" + key + "\": ";
                    Object value = stringify(((Map.Entry) entry).getValue());
                    builder.append(key).append(value);
                }
            } else {
                try {
                    String mName, key;
                    for (Method m : obj.getClass().getMethods()) {
                        mName = m.getName();
                        if (mName.startsWith("get") && mName.length() > 3)
                            try {
                                key = "\"" + mName.substring(3, 4).toLowerCase() + mName.substring(4) + "\": ";
                                builder.append(key).append(m.invoke(obj));
                            } catch (Exception ignored) {
                            }
                    }
                } catch (SecurityException ignored) {
                }
            }
            builder.append("}");
        }
        return builder.toString();
    }

    public static Object parse(String source) {
        if (source == null || source.isEmpty()) return null;
        if (source.matches("^\".*\"$")) {
            source = source.substring(1, source.length() - 1);
            if (source.matches("^[0-9]{4}[-.][0-9]{2}[-.][0-9]{2}([ :,.][0-2][0-9][ :][0-5][0-9][ :][0-5][0-9])?$"))
                try {
                    source = source.replaceAll("[-. :,.]", "");
                    if (source.length() == 8) source += "000000";
                    String yyyy = source.substring(0, 4);
                    String MM = source.substring(4, 6);
                    String dd = source.substring(6, 8);
                    String HH = source.substring(8, 10);
                    String mm = source.substring(10, 12);
                    String ss = source.substring(12);
                    return dateFormatter.parse('"' + yyyy + '-' + MM + '-' + dd + ' ' + HH + ':' + mm + ':' + ss + '"').getTime();
                } catch (java.text.ParseException e) {
                    throw new ParseException("Cannot parse the date format. source: " + source, e);
                }
            return source;
        }
        if (source.matches("^\\{.*}$") || source.matches("^\\[.*]$")) {
            boolean isObj = source.charAt(0) == '{';
            Map<String, Object> obj = isObj ? new HashMap<>() : null;
            List<Object> list = !isObj ? new ArrayList<>() : null;
            source = source.substring(1, source.length() - 1).trim();
            String key = null;
            int openIdx = -1, openCnt = 0;
            char closeChar = ' ';
            boolean ignore = false;
            for (int i = 0; i < source.length(); i++) {
                char c = source.charAt(i);
                if (ignore) ignore = false;
                else if (c == '\\') ignore = true;
                else if (isObj && key == null) {
                    if (c == '"') {
                        if (openCnt == 0) {
                            openIdx = i;
                            openCnt = 1;
                        } else {
                            key = source.substring(openIdx + 1, i);
                            openIdx = -1;
                            openCnt = 0;
                            char next = source.charAt(i + 1);
                            while (next == ' ' || next == ':') {
                                next = source.charAt(++i + 1);
                            }
                        }
                    }
                } else if (openIdx == -1) {
                    openIdx = i;
                    if (c >= '0' && c <= '9' || c == '-') {
                        Character next = null;
                        try {
                            next = source.charAt(i + 1);
                            while (next >= '0' && next <= '9' || next == '.') {
                                next = source.charAt(++i + 1);
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            i = source.length() - 1;
                        }
                        Object val = parse(source.substring(openIdx, i + 1));
                        if (next != null)
                            while (next == ',' || next == ' ') {
                                next = source.charAt(++i + 1);
                            }
                        if (obj != null) obj.put(key, val);
                        else list.add(val);

                        openIdx = -1;
                        key = null;
                    } else {
                        closeChar = c == '[' ? ']' : c == '{' ? '}' : '"';
                        openCnt = 1;
                    }
                } else {
                    if (c == closeChar) {
                        openCnt -= 1;
                        if (openCnt == 0) {
                            Object val = parse(source.substring(openIdx, i + 1));
                            if (obj != null) obj.put(key, val);
                            else list.add(val);
                            try {
                                char next = source.charAt(i + 1);
                                while (next == ',' || next == ' ') {
                                    next = source.charAt(++i + 1);
                                }
                            } catch (StringIndexOutOfBoundsException e) {
                                i = source.length() - 1;
                            }
                            openIdx = -1;
                            key = null;
                        }
                    } else if (c == source.charAt(openIdx)) openCnt += 1;
                }
            }
            return isObj ? obj : list;
        }
        try {
            if (source.indexOf('.') > -1) return Double.parseDouble(source);
            return Long.parseLong(source);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid format. source: " + source, e);
        }
    }

    public static final class ParseException extends RuntimeException {
        private ParseException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
}
