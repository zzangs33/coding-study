package com.coding.april.forth;

import java.util.ArrayList;
import java.util.List;

public class Victory implements BruteForce {

    public static void main(String[] args) {
        Victory vic = new Victory();
        vic.exe("carpet");
    }
    @Override
    public int[] virtualTest(int[] answers) {
        int[] a = {1, 2, 3, 4, 5};
        int[] b = {2, 1, 2, 3, 2, 4, 2, 5};
        int[] c = {3, 3, 1, 1, 2, 2, 4, 4, 5, 5};
        int aCnt = 0;
        int bCnt = 0;
        int cCnt = 0;
        for(int i=0; i<answers.length; i++) {
            int answer = answers[i];
            if(answer == a[i%a.length]) {aCnt++;}
            if(answer == b[i%b.length]) {bCnt++;}
            if(answer == c[i%c.length]) {cCnt++;}
        }

//        int maxScore = Math.max(aCnt, Math.max(bCnt, cCnt));

        int maxScore = aCnt >= bCnt ? (aCnt >= cCnt ? aCnt : cCnt) : (bCnt>= cCnt ? bCnt : cCnt);


        List<Integer> list = new ArrayList<>();
        if(maxScore == aCnt) {list.add(1);}
        if(maxScore == bCnt) {list.add(2);}
        if(maxScore == cCnt) {list.add(3);}

        return list.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int findPrimeNumber(String numbers) {
        return 0;
    }

    @Override
    public int[] carpet(int brown, int yellow) {
        int total = brown + yellow;
        int height = 3;
        int width;
        while(true) {
            if((total % height) == 0) {
                width = total / height;
                if((width-2) * (height-2) == yellow) return new int[]{width, height};
            }
            height++;
        }
    }
}
