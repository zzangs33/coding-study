package com.coding.y2021.april.fifth;

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

        List<Integer> notAList = new ArrayList<>();
        for(int i = 0; i<charArr.length; i++) {
            if(charArr[i] != 'A') notAList.add(i);
        }
        System.out.println("notAList : " + notAList);

        if(notAList.size() == 0) return answer; //AAAAAA
        if(notAList.size() == 1) { // ABAAAA
            int idx = notAList.get(0);
            answer += idx; // index 값 만큼 이동
            answer += calculateAnswer(charArr[idx]); // char 계산
            return answer;
        }

        for(Integer idx : notAList) {
            answer += calculateAnswer(charArr[idx]);
        }
        answer += charArr.length - 1; // 전체 자리 이동 수
        int first = notAList.get(0);
        int last = notAList.get(notAList.size()-1);
        answer = answer - Math.max(first,(charArr.length - last - 1));

        if(first == 0)
            answer = answer - (notAList.get(1) - first -1);

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
