package com.coding.y2021.april.forth;

import com.coding.testcase.TestCaseExecutor;

import java.util.*;

public class Kms27420 implements BruteForce {
    public static void main(String[] args) {
        TestCaseExecutor.execute(new Kms27420());
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
        return this.findPrimeNumberSet(numbers).size();
    }

    public Set<Integer> findPrimeNumberSet(String numbers) {
        return this.calcPrimeNumberSet(new HashSet<>(), "", numbers);
    }

    private Set<Integer> calcPrimeNumberSet(Set<Integer> result, String prefix, String rest) {
        try {
            int iValue = Integer.parseInt(prefix);
            if (this.isPrime(iValue)) result.add(iValue);
        } catch (NumberFormatException ignored) {
        }
        for (int i = 0; i < rest.length(); i += 1)
            this.calcPrimeNumberSet(result, prefix + rest.charAt(i), rest.substring(0, i) + rest.substring(i + 1));
        return result;
    }

    private final Map<Integer, Boolean> primeCache = new HashMap<>();

    private boolean isPrime(Integer number) {
        return this.primeCache.computeIfAbsent(number, num -> {
            if (num < 2) return false;
            if (num < 4) return true;
            for (int i = 2; i <= num / 2; i += 1)
                if (num % i == 0) return false;
            return true;
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
