package com.coding.testcase;

import com.coding.utils.CastUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * instance 클래스에 TestCase annotation 의 value (없을 시 클래스 경로와 동일한 경로내의 testcase.json) 파일을 읽어,
 * 선언된 method 들을 모두 실행해주는 유틸
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestCaseExecutor {
    public static void execute(Object instance) {
        try {
            List<Method> methodList = Arrays.asList(instance.getClass().getDeclaredMethods());
            Map<String, List<List<Object>>> testCaseJson = getTestCaseMap(instance.getClass());
            for (Map.Entry<String, List<List<Object>>> entry : testCaseJson.entrySet()) {
                String methodName = entry.getKey();
                List<List<Object>> testCaseList = entry.getValue();
                List<Method> matchMethodList = methodList.stream()
                        .filter(m -> m.getName().equals(methodName))
                        .collect(Collectors.toList());
                if (matchMethodList.isEmpty()) {
                    new NoSuchMethodException(methodName).printStackTrace();
                } else {
                    int no = 1;
                    System.out.println("\n--------------------------------------------------");
                    System.out.println("The test cases of the method \"" + methodName + "\"");
                    for (List<Object> testCase : testCaseList) {
                        try {
                            new Invoker(instance, matchMethodList, testCase).invoke(no++);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("--------------------------------------------------\n");
                }
            }
        } catch (Exception e) {
            throw new TestCaseRuntimeException(e);
        }
    }

    private static Map<String, List<List<Object>>> getTestCaseMap(InputStream is) throws IOException {
        Map<String, Object> testCaseJson = new ObjectMapper().readValue(is, Map.class);
        Map<String, List<List<Object>>> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : testCaseJson.entrySet()) {
            if (!(entry.getValue() instanceof List)) {
                throw new IOException("test case json file type error");
            }
            List<List<Object>> resultArgsList = new ArrayList<>();
            List<Object> argsList = (List<Object>) entry.getValue();
            for (Object args : argsList) {
                resultArgsList.add(args instanceof List ? (List<Object>) args : Collections.singletonList(args));
            }
            result.put(entry.getKey(), resultArgsList);
        }
        return result;
    }

    private static Map<String, List<List<Object>>> getTestCaseMap(Class<?> clz) throws IOException {
        List<String> clzPathCandidates = new ArrayList<>();
        TestCase testCase = clz.getDeclaredAnnotation(TestCase.class);
        if (testCase != null) {
            clzPathCandidates.add(testCase.value());
        }
        clzPathCandidates.add("./testcase.json");
        for (String classPath : clzPathCandidates) {
            try (InputStream is = clz.getResourceAsStream(classPath)) {
                if (is != null) {
                    return getTestCaseMap(is);
                }
            } catch (NullPointerException ignored) {
            }
        }
        throw new FileNotFoundException("class path list: " + clzPathCandidates);
    }

    private static class Invoker {
        private final Object instance;
        private final Method method;
        private final Object[] args;

        private Invoker(Object instance, List<Method> methodList, List<Object> testCase) throws NoSuchMethodException {
            Method matchedMethod = null;
            Object[] castedArgs = new Object[testCase.size()];
            for (Method method : methodList) {
                Class<?>[] types = method.getParameterTypes();
                if (types.length == testCase.size()) {
                    try {
                        for (int i = 0; i < types.length; i++) {
                            castedArgs[i] = CastUtil.cast(testCase.get(i), types[i]);
                        }
                        matchedMethod = method;
                    } catch (Exception ignored) {
                    }
                }
            }
            if (matchedMethod == null) {
                throw new NoSuchMethodException(methodList.get(0).getName());
            }
            this.instance = instance;
            this.method = matchedMethod;
            this.args = castedArgs;
        }

        private void invoke(int no) throws JsonProcessingException {
            System.out.println("# Test case " + no);
            System.out.println("- Arguments: " + new ObjectMapper().writeValueAsString(this.args));
            System.out.println("- Task logging: ");
            try {
                long start = System.currentTimeMillis();
                Object result = this.method.invoke(this.instance, this.args);
                System.out.print("- result: ");
                System.out.println(new ObjectMapper().writeValueAsString(result));
                System.out.println("- execution time: " + (System.currentTimeMillis() - start) + " ms\n");
            } catch (InvocationTargetException | IllegalAccessException e) {
                System.out.print("- result: ");
                e.printStackTrace();
            }
        }
    }
}
