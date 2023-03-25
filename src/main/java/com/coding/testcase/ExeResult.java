package com.coding.testcase;

import lombok.Builder;

import java.util.List;

@Builder
public class ExeResult {
    public final long start;
    public final long end;
    public final List<Object> testCase;
    public final Object result;
    public final List<Exception> exceptionList;

    public long getExeTime() {
        return this.end - this.start;
    }
}
