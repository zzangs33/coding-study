package com.coding.april.fifth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Victory implements Greedy {

    public static void main(String[] args) {
        Victory vic = new Victory();
        vic.exe("joyStick");
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
        int answer = n; // 3
        int[] students = new int[n+2]; // [0, 1, -1, 2, 0]
        final int firstIdx = 1;
        for(int no : reserve) students[no] = 2;
        for(int no : lost) students[no]--;


        System.out.println("1st : " + Arrays.toString(students));
        for(int no = firstIdx; no <= n; no ++) {
            if(students[no] != 0) {
                if(students[no] == -1 && students[no+1] >= 1) {
                    students[no] = 0;
                    students[no+1] = students[no+1] == 2 ? 0: -1;
                } else if(students[no+1] == -1) {
                    if(students[no] == 2) {
                        students[no] = 0;
                        students[no+1] = 0;
                    } else if(students[no] == 1) {
                        students[no] = students[no-1] == 2? 0 : -1;
                        students[no+1] = 0;
                    }
                }
            }

        }

        System.out.println("2nd : " + Arrays.toString(students));

        return answer - (int) (Arrays.stream(students).filter(no -> no == -1).count());
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
    public String makeABigNumber(String number, int k) {
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
