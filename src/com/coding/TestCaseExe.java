package com.coding;

import com.coding.utils.CastUtil;
import com.coding.utils.JsonUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface TestCaseExe {
    default void exe(String methodName) throws TestCaseRuntimeException {
        try {
            List<Method> methodList = new ArrayList<>();
            for (Method method : this.getClass().getMethods())
                if (method.getName().equals(methodName))
                    methodList.add(method);
            if (methodList.isEmpty()) throw new NoSuchMethodException();

            List<Object> testCaseList;
            InputStream is = this.getClass().getResourceAsStream("./testcase.json");
            if (is == null) throw new FileNotFoundException("File name: testcase.json");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                Map<String, Object> testCaseMap = (Map<String, Object>) JsonUtil.parse(br.lines().collect(Collectors.joining()));
                testCaseList = (List<Object>) testCaseMap.get(methodName);
                if (testCaseList.isEmpty())
                    throw new FileNotFoundException("Cannot find the test cases from the testcase.json file.");
            }

            int no = 1;
            for (Object item : testCaseList) {
                List<Object> testCase = item instanceof List ? (List<Object>) item : new ArrayList<>();
                if (!(item instanceof List)) testCase.add(item);
                long exeTime = 0;
                Object result = null;
                List<Object> arguments = null;
                for (Method method : methodList) {
                    Class<?>[] paramTypes = method.getParameterTypes();
                    if (paramTypes.length == testCase.size()) {
                        List<Object> args = new ArrayList<>();
                        int i = 0;
                        try {
                            for (Object arg : testCase)
                                args.add(CastUtil.cast(arg, paramTypes[i++]));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (args.size() == paramTypes.length) {
                            long start = System.currentTimeMillis();
                            result = method.invoke(this, args.toArray());
                            exeTime = System.currentTimeMillis() - start;
                            arguments = args;
                            break;
                        }
                    }
                }
                if (arguments == null) throw new NoSuchMethodException();
                if (no == 1)
                    System.out.println("------------------------------------\nThe test cases of the method \"" + methodName + "\"");
                System.out.println("# Test case " + no++);
                System.out.println("- Arguments: " + JsonUtil.stringify(arguments));
                System.out.println("- result: " + JsonUtil.stringify(result));
                System.out.println("- execution time: " + exeTime + " ms");
                if (no == testCaseList.size() + 1) System.out.println("------------------------------------\n");
                else System.out.println();
            }
        } catch (InvocationTargetException e) {
            throw new TestCaseRuntimeException(e.getCause());
        } catch (Exception e) {
            throw new TestCaseRuntimeException(e);
        }
    }
}
