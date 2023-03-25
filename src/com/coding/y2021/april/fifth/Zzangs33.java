package com.coding.y2021.april.fifth;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Zzangs33 implements Greedy {
    public static void main(String[] args) {
        Zzangs33 zzangs33 = new Zzangs33();
//        zzangs33.exe("gymSuit");
        zzangs33.exe("joyStick");
    }

    @Override
    public int gymSuit(int n, int[] lost, int[] reserve) {
        Set<Integer> hasLost = new HashSet<>();
        Queue<Integer> hasReserve = new PriorityQueue<>();
        for (int l : lost) {
            hasLost.add(l);
        }
        for (int r : reserve) {
            if (hasLost.contains(r)) hasLost.remove(r);
            else hasReserve.add(r);
        }
        for (int r : hasReserve) {
            boolean hasSmall = hasLost.contains(r - 1);
            boolean hasLarge = hasLost.contains(r + 1);

            if (hasSmall) hasLost.remove(r - 1);
            else if (hasLarge) hasLost.remove(r + 1);
        }

        return n - hasLost.size();
    }

    @Override
    public int joyStick(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) sb.append('A');

        String current = sb.toString();
        int[] cnts = new int[current.length()];

        for (int i = 0; i < current.length(); i++) {
            int ordi = Math.abs(current.charAt(i) - name.charAt(i));
            int rev = Math.abs('Z' - name.charAt(i) + (current.charAt(i) - 'A' + 1));
            cnts[i] = Math.min(ordi, rev);
        }

        int cur = 0;
        int total = 0;
        while (!current.equals(name)) {
            int[] alias = move(cnts, cur);
            sb.setCharAt(cur, name.charAt(cur));
            current = sb.toString();
            total += cnts[cur];
            cnts[cur] = 0;

            cur = minIdx(alias);
            total += alias[cur];
        }
        return total;
    }

    private int[] move(int[] cnts, int idx) {
        int[] toMove = new int[cnts.length];
        for (int i = 0; i < cnts.length; i++) {
            int ordi = Math.abs(i - idx);
            int rev = Math.abs(cnts.length - i + idx);
            toMove[i] = cnts[i] == 0 ? 0 : Math.min(ordi, rev);
        }
        return toMove;
    }

    private int minIdx(int[] cnts) {
        int min = 99999999;
        int res = 0;
        for (int i = 1; i < cnts.length; i++) {
            if (cnts[i] != 0 && cnts[i] < min) {
                res = i;
                min = cnts[i];
            }
        }
        return res;
    }


    @Override
    public String makeBigNumber(String number, int k) {
        // 졸려서 포기

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

