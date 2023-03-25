package com.coding.testcase;

import java.io.PrintStream;
import java.io.PrintWriter;

public class TestCaseRuntimeException extends RuntimeException {
    private Throwable cause;

    public TestCaseRuntimeException(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public synchronized Throwable getCause() {
        return this.cause;
    }

    @Override
    public synchronized Throwable initCause(Throwable cause) {
        return this.cause = cause;
    }

    @Override
    public String getMessage() {
        return this.getCause().getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return this.getCause().getLocalizedMessage();
    }

    @Override
    public String toString() {
        return this.getCause().toString();
    }

    @Override
    public void printStackTrace() {
        this.getCause().printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        this.getCause().printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        this.getCause().printStackTrace(s);
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return this.getCause().getStackTrace();
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.getCause().setStackTrace(stackTrace);
    }
}
