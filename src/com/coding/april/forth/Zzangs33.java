package com.coding.april.forth;

import java.util.*;

public class Zzangs33 implements BruteForce {
    public static void main(String[] args) {
        Zzangs33 zzangs33 = new Zzangs33();
        zzangs33.exe("virtualTest");
        zzangs33.exe("findPrimeNumber");
        zzangs33.exe("carpet");
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

                    return Integer.compare(e1.getKey(), e2.getKey());
                })
                .map(Map.Entry::getKey).mapToInt(Integer::intValue).toArray();

        if (!correct.get(rank[0]).equals(correct.get(rank[1]))) return new int[]{rank[0]};
        else if (!correct.get(rank[1]).equals(correct.get(rank[2]))) return new int[]{rank[0], rank[1]};
        return rank;
    }

    Set<Integer> totalNumbers = new HashSet<>();
    @Override
    public int findPrimeNumber(String numbers) {
        String[] split = numbers.split("");
        boolean[] visited = new boolean[split.length];
        for (int i = 1; i <= split.length; i++)
            addNumbers(split, visited, "", i);

        return totalNumbers.size();
    }
    private void addNumbers(String[] split, boolean[] visited, String current, int targetSize) {
        if (current.length() == targetSize) {
            int res = Integer.parseInt(current);
            if (isPrime(res)) totalNumbers.add(res);
        }
        for (int i = 0; i < split.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                addNumbers(split, visited, current + split[i], targetSize);
                visited[i] = false;
            }
        }
    }
    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2 || n == 3) return true;
        for (int i = 2; i <= Math.sqrt(n); i++) if (n % i == 0) return false;
        return true;
    }

    @Override
    public int[] carpet(int brown, int yellow) {
        int result = brown + yellow;
        List<Integer> verticals = new ArrayList<>();
        for (int i = 1; i <= result / i; i++) {
            if (result % i == 0) verticals.add(i);
        }
        for (int vert : verticals) {
            int edge = 2 * vert + 2 * (result / vert) - 4;
            if (edge == brown) return new int[]{result / vert, vert};
        }

        return new int[0];
    }
}
