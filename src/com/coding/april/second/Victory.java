package com.coding.april.second;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class Victory implements Heap {

    int fi;
    public static void main(String[] args) {
        Victory vic = new Victory();
        vic.exe("hotter");
    }
    @Override
    public int hotter(int[] scoville, int K) {
        Queue<Integer> hotQ = new PriorityQueue<>(Arrays.stream(scoville).boxed().collect(Collectors.toList()));
        int cnt = 0;
        int notHot;
        while(!hotQ.isEmpty()) {
            notHot = hotQ.poll();
            if(notHot >= K) {
                return cnt;
            } else {
                if(hotQ.isEmpty()) return -1;
                int next = hotQ.poll();
                hotQ.offer(notHot + (next*2));
                cnt ++;
            }
        }

        return -1;
    }

    @Override
    public int diskController(int[][] jobs) {
        return 0;
    }

    @Override
    public int[] dualPriorityQueue(String[] operations) {
        return new int[0];
    }
}
