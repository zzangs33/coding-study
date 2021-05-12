package com.coding;

import com.coding.utils.CastUtil;
import com.coding.utils.JsonUtil;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface TestCaseExe {
    default void exe(String methodName) throws TestCaseRuntimeException {
        try {
            Method[] methods = this.getClass().getMethods();
            if (methods.length == 0) throw new NoSuchMethodException();

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
                for (Method method : methods) {
                    Class<?>[] paramTypes = method.getParameterTypes();
                    if (paramTypes.length == testCase.size()) {
                        List<Object> args = new ArrayList<>();
                        int i = 0;
                        try {
                            for (Object arg : testCase)
                                args.add(CastUtil.cast(arg, paramTypes[i++]));
                        } catch (ClassCastException e) {
                            e.printStackTrace();
                        }
                        if (args.size() == paramTypes.length)
                            try (StringWriter logWriter = new StringWriter()) {
                                if (no == 1)
                                    logWriter
                                            .append("------------------------------------").append('\n')
                                            .append("The test cases of the method \"").append(methodName).append('\"').append('\n');
                                logWriter
                                        .append("# Test case ").append(String.valueOf(no++)).append('\n')
                                        .append("- Arguments: ").append(JsonUtil.stringify(args)).append('\n')
                                        .append("- result: ");
                                try {
                                    long start = System.currentTimeMillis();
                                    Object result = method.invoke(this, args.toArray());
                                    long exeTime = System.currentTimeMillis() - start;
                                    logWriter
                                            .append(JsonUtil.stringify(result)).append('\n')
                                            .append("- execution time: ").append(String.valueOf(exeTime)).append(" ms").append('\n');
                                } catch (InvocationTargetException e) {
                                    e.getCause().printStackTrace(new PrintWriter(logWriter));
                                } finally {
                                    logWriter
                                            .append(no == testCaseList.size() + 1 ? "------------------------------------" : "").append('\n');
                                    System.out.print(logWriter);
                                }
                                break;
                            }
                    }
                }
            }
        } catch (Exception e) {
            throw new TestCaseRuntimeException(e);
        }
    }
}
