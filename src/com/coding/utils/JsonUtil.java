package com.coding.utils;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonUtil {
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("\"yyyy-MM-dd HH:mm:ss\"");

    private JsonUtil() {
    }

    public static String stringify(Object obj) {
        if (obj == null) return "null";
        if (obj instanceof Date || obj instanceof TemporalAccessor) {
            if (obj instanceof Date) return dateFormatter.format((Date) obj);
            return dateFormatter.format(obj);
        }
        if (obj instanceof CharSequence) return "\"" + obj + "\"";
        if (CastUtil.isBasicType(obj)) return obj + "";
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

    public static Object parse(String source) throws ParseException {
        String origin = source;
        if (source == null) return null;
        source = source.trim();
        if (source.isEmpty()) return null;
        boolean isLengthBiggerThanOne = source.length() > 1;
        char firstChar = source.charAt(0), lastChar = source.charAt(source.length() - 1);
        boolean isString = isLengthBiggerThanOne && firstChar == '"' && lastChar == '"';
        boolean isObject = isLengthBiggerThanOne && (firstChar == '{' && lastChar == '}' || firstChar == '[' && lastChar == ']');
        if (isString) {
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
                    throw new ParseException("Cannot parse the date format. source: " + origin, e);
                }
            return source;
        }
        if (isObject) {
            boolean isObj = source.charAt(0) == '{';
            Map<String, Object> obj = isObj ? new HashMap<>() : null;
            List<Object> list = !isObj ? new ArrayList<>() : null;
            source = source.substring(1, source.length() - 1).trim();
            String key = null;
            int openIdx = -1, openCnt = 0;
            char closeChar = ' ';
            for (int i = 0; i < source.length(); i++) {
                char c = source.charAt(i);
                if (isObj && key == null) {
                    if (c == '"') {
                        if (openCnt == 0) {
                            openIdx = i;
                            openCnt = 1;
                        } else {
                            key = source.substring(openIdx + 1, i);
                            openIdx = -1;
                            openCnt = 0;
                            i = findBeforeNext(source, i, next -> Character.isWhitespace(next) || next == ':');
                        }
                    }
                } else if (openIdx == -1) {
                    boolean isOpenChar = c == '[' || c == '{' || c == '"';
                    boolean isAvailableChar = c >= '0' && c <= '9' || c == '-' || c == 't' || c == 'f' || c == 'n';
                    if (!isOpenChar && !isAvailableChar)
                        throw new ParseException("Invalid format. source: " + origin);
                    openIdx = i;
                    if (isOpenChar) {
                        closeChar = c == '[' ? ']' : c == '{' ? '}' : '"';
                        openCnt = 1;
                    } else {
                        Object val;
                        if (c >= '0' && c <= '9' || c == '-') {
                            i = findBeforeNext(source, i, next -> next >= '0' && next <= '9' || next == '.');
                            val = parse(source.substring(openIdx, i + 1));
                        } else
                            try {
                                val = parse(source.substring(openIdx, (i += 3) + 1));
                            } catch (StringIndexOutOfBoundsException e) {
                                throw new ParseException("Invalid format. source: " + origin);
                            }
                        i = findBeforeNext(source, i, next -> Character.isWhitespace(next) || next == ',');
                        if (obj != null) obj.put(key, val);
                        else list.add(val);

                        openIdx = -1;
                        key = null;
                    }
                } else {
                    if (c == closeChar) {
                        openCnt -= 1;
                        if (openCnt == 0) {
                            Object val = parse(source.substring(openIdx, i + 1));
                            if (obj != null) obj.put(key, val);
                            else list.add(val);
                            i = findBeforeNext(source, i, next -> Character.isWhitespace(next) || next == ',');
                            openIdx = -1;
                            key = null;
                        }
                    } else if (c == source.charAt(openIdx)) openCnt += 1;
                }
            }
            return isObj ? obj : list;
        }
        if ("true".equals(source)) return true;
        if ("false".equals(source)) return false;
        try {
            if (source.indexOf('.') > -1) return Double.parseDouble(source);
            return Long.parseLong(source);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid format. source: " + origin, e);
        }
    }

    public static Object parse(Reader reader) throws IOException, NullPointerException, ParseException {
        try (BufferedReader br = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader)) {
            return parse(br.lines().collect(Collectors.joining()));
        }
    }

    public static Object parse(InputStream inputStream) throws IOException, NullPointerException, ParseException {
        return parse(new InputStreamReader(inputStream));
    }

    public static Object parse(Class<?> sourceClz, String classPath) throws IOException, NullPointerException, ParseException {
        InputStream is = sourceClz.getResourceAsStream(classPath);
        if (is == null) throw new FileNotFoundException("classPath: " + classPath);
        return parse(is);
    }

    public static final class ParseException extends RuntimeException {
        private ParseException(String msg, Throwable cause) {
            super(msg, cause);
        }

        private ParseException(String msg) {
            super(msg);
        }
    }

    private static int findBeforeNext(String source, int start, Function<Character, Boolean> exclude) {
        try {
            char next = source.charAt(start + 1);
            while (exclude.apply(next)) {
                next = source.charAt(++start + 1);
            }
            return start;
        } catch (StringIndexOutOfBoundsException e) {
            return source.length() - 1;
        }
    }
}
