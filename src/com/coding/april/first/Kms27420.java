package com.coding.april.first;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Kms27420 implements QueueNStack {
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Kms27420 instance = new Kms27420();
        instance.exe("truckThroughBridge");
        instance.exe("stockValue");
        instance.exe("developModule");
        instance.exe("printer");
    }

    @Override
    public int truckThroughBridge(int bridge_length, int weight, int[] truck_weights) {
        return 0;
    }

    @Override
    public int[] stockValue(int[] prices) {
        return new int[0];
    }

    @Override
    public int[] developModule(int[] progresses, int[] speeds) {
        return new int[0];
    }

    @Override
    public int printer(int[] priorities, int location) {
        return 0;
    }
}
