package com.coding.april.forth;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Zzangs33 implements BruteForce {
    public static void main(String[] args) {
        Zzangs33 zzangs33 = new Zzangs33();
        System.out.println(Arrays.toString(zzangs33.virtualTest(new int[]{1, 2, 3, 4, 5})));
        System.out.println(Arrays.toString(zzangs33.virtualTest(new int[]{1, 3, 2, 4, 2})));
    }

    @Override
    public int[] virtualTest(int[] answers) {
        Map<Integer, Integer> correct = new HashMap<>();
        int[][] myWay = new int[][]{{5, 1, 2, 3, 4}, {5, 2, 1, 2, 3, 2, 4, 2}, {5, 3, 3, 1, 1, 2, 2, 4, 4, 5}};
        correct.put(1, 0);
        correct.put(2, 0);
        correct.put(3, 0);

        for (int i = 1; i <= answers.length; i++) {
            int answer = answers[i - 1];
            if (myWay[0][i % 5] == answer) {
                Integer val = correct.get(1);
                correct.put(1, ++val);
            }
            if (myWay[1][i % 8] == answer) {
                Integer val = correct.get(2);
                correct.put(2, ++val);
            }
            if (myWay[2][i % 10] == answer) {
                Integer val = correct.get(3);
                correct.put(3, ++val);
            }
        }

        int[] rank = correct.entrySet().stream()
                .sorted((e1, e2) -> {
                    if (e1.getValue() < e2.getValue()) return 1;
                    else if (e1.getValue() > e2.getValue()) return -1;

                    return e1.getKey() < e2.getKey() ? -1 : 1;
                })
                .map(Map.Entry::getKey).mapToInt(Integer::intValue).toArray();

        if (!correct.get(rank[0]).equals(correct.get(rank[1]))) return new int[]{rank[0]};
        else if (!correct.get(rank[1]).equals(correct.get(rank[2]))) return new int[]{rank[0], rank[1]};
        return rank;
    }

    @Override
    public int findPrimeNumber(String numbers) {
        return 0;
    }

    @Override
    public int[] carpet(int brown, int yellow) {
        return new int[0];
    }
}
