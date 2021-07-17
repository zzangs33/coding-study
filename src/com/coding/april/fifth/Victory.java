package com.coding.april.fifth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Victory implements Greedy {

    public static void main(String[] args) {
        Victory vic = new Victory();
        vic.exe("gymSuit");
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
        return 0;
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
