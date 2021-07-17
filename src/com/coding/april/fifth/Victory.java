package com.coding.april.fifth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Victory implements Greedy {

    public static void main(String[] args) {
        Victory vic = new Victory();
        vic.exe("gymSuit");
//        System.out.println((int)'A');
//        System.out.println((int)'J');
//        System.out.println((int)'O');
//
//        for(char c : new char[] {'A','J','U','Z'}) {
//            System.out.println((int)c);
//        }
    }
    @Override
    public int gymSuit(int n, int[] lost, int[] reserve) {
        int[] students = new int[n+2];

        for(int l : lost) students[l] = -1;
        for(int re : reserve) students[re]++;

        System.out.println(Arrays.toString(students));

        for(int i = 1; i<students.length-1; i++) {
            if(students[i] == -1) {
                if(students[i-1] == 1) {
                    students[i-1] = 0;
                    students[i] = 0;
                } else if(students[i+1] == 1) {
                    students[i+1] = 0;
                    students[i] = 0;
                }
            }
        }

        return n - (int)(Arrays.stream(students).filter(no -> no ==-1)).count();
    }

    @Override
    public int joyStick(String name) {
        char[] charArr = name.toCharArray();
        int answer = 0;

        if(charArr.length == 1) {
            answer = calculateAnswer(charArr[0]);
            return answer;
        }

        List<Integer> list = new ArrayList<>();
        for(int i = 0; i<charArr.length; i++) {
            if(charArr[i] != 'A') list.add(i);
        }
        System.out.println("list : " + list);



        if(list.size() == 0) return answer;
        if(list.size() == 1) {
            int idx = list.get(0);
            answer += idx;
            answer += calculateAnswer(charArr[idx]);
            return answer;
        }

        answer += charArr.length - 1; // 전체 자리 이동 수

        for(Integer idx : list) {
            answer += calculateAnswer(charArr[idx]);
        }

        if(list.get(0) == 0)
            answer = answer -  (list.get(1) - list.get(0) - 1);

        return answer;
    }

    private int calculateAnswer(char c) {
        final int valueOfA = 65;
        final int numberofAlphabet = 26;
        return c <= 'N' ? c - valueOfA : numberofAlphabet - (c - valueOfA);
    }

    @Override
    public String makeBigNumber(String number, int k) {
        return null;
    }

    @Override
    public int lifeboat(int[] people, int limit) {
        return 0;
    }

    @Override
    public int linkingIslands(int n, int[][] costs) {
        return 0;
    }

    @Override
    public int speedTrap(int[][] routes) {
        return 0;
    }
}
