package com.coding.april.forth;

import com.coding.utils.JsonUtil;

import java.util.*;

public class Kms27420 implements BruteForce {
    public static void main(String[] args) {
        Kms27420 instance = new Kms27420();
//        instance.exe("virtualTest");
        instance.exe("findPrimeNumber");
//        instance.exe("carpet");
    }

    @Override
    public int[] virtualTest(int[] answers) {
        int[][] comps = new int[][]{{1, 2, 3, 4, 5}, {2, 1, 2, 3, 2, 4, 2, 5}, {3, 3, 1, 1, 2, 2, 4, 4, 5, 5}};
        int maxCnt = 0;
        int[] cnts = new int[comps.length];
        for (int ansI = 0; ansI < answers.length; ansI += 1)
            for (int compI = 0; compI < comps.length; compI += 1)
                if (comps[compI][ansI % comps[compI].length] == answers[ansI]) {
                    cnts[compI] += 1;
                    if (cnts[compI] > maxCnt) maxCnt = cnts[compI];
                }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < cnts.length; i += 1)
            if (cnts[i] == maxCnt) result.add(i + 1);
        return result.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public int findPrimeNumber(String numbers) {
        return (int) this.getAll(numbers).stream()
                .filter(this::isPrime)
                .count();
    }

    private final Map<String, Set<Integer>> cache = new HashMap<>();

    private Set<Integer> getAll(String numbers) {
        Set<Integer> set = new HashSet<>();
        if (numbers.length() < 2) {
            System.out.println(JsonUtil.stringify(set));
            set.add(this.parseInt(numbers));
        } else {
            for (int i = 0; i < numbers.length(); i += 1) {
                char c = numbers.charAt(0);
                Set<Integer> childSet = this.cache.computeIfAbsent(
                        numbers.substring(0, i) + numbers.substring(i + 1),
                        this::getAll
                );
                childSet.forEach(iNum -> {
                    set.add(iNum);
                    String sNum = iNum.toString();
                    for (int idx = 0; idx <= sNum.length(); idx += 1)
                        set.add(this.parseInt(sNum.substring(0, idx) + c + sNum.substring(idx)));
                });
            }
        }
        System.out.println(JsonUtil.stringify(set));
        return set;
    }

    private int parseInt(String num) {
        if (num == null || num.isEmpty()) return 0;
        while (num.length() > 0 && num.charAt(0) == '0')
            num = num.substring(1);
        return num.isEmpty() ? 0 : Integer.parseInt(num);
    }

    private final Map<Integer, Boolean> primeCache = new HashMap<>();

    private boolean isPrime(Integer number) {
        return this.primeCache.computeIfAbsent(number, (num) -> {
            for (int i = 2; i <= 3 || i < number / 3; i += 1)
                if (number % i == 0) return false;
            return num > 1;
        });
    }

    @Override
    public int[] carpet(int brown, int yellow) {
        int yellowX = yellow, yellowY = 1, cnt = 2 * (yellowX + yellowY + 2);
        while (cnt != brown) {
            int outerX = yellowX, outerY = yellowY, outerCnt;
            do {
                outerX = outerX + 2;
                outerY = outerY + 2;
                outerCnt = 2 * (outerX + outerY + 2);
            } while (outerCnt < brown);
            if (outerCnt == brown) break;
            do {
                yellowY += 1;
            } while (yellow % yellowY != 0 && yellowY < yellow);
            if (yellowY > yellow) break;
            yellowX = yellow / yellowY;
            cnt = 2 * (yellowX + yellowY + 2);
        }
        return new int[]{yellowX + 2, yellowY + 2};
    }
}
