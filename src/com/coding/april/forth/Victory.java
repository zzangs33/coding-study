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
        int answer = 0;
        List<Integer> list = new ArrayList<>();
        if(numbers.length() >= 1 &&numbers.length() <= 7)  list = mixNumber(list,"", numbers);
        for(int item : list) {
            boolean isPrime = true;
            for(int i=2; i*i<=item; i++) {
                if(item % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if(isPrime) {
                answer++;
            }
        }

        return answer;
    }

    private List<Integer> mixNumber(List<Integer> list, String prefix, String str) {
        int pre = prefix.length() != 0 ? Integer.parseInt(prefix) : 0;

        if(pre != 1 && pre != 0 && !list.contains(pre)) list.add(pre);

        int n = str.length();
        if (n == 0) {
            if(!list.contains(pre)) list.add(pre);
        }
        else {
            for (int i = 0; i < n; i++) {
                mixNumber(list,prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
            }
        }
        return list;
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
