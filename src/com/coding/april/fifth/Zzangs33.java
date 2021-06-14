package com.coding.april.fifth;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Zzangs33 implements Greedy {
    public static void main(String[] args) {
        Zzangs33 zzangs33 = new Zzangs33();
        zzangs33.exe("gymSuit");
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

            if (hasSmall || hasLarge) {
                if (hasSmall) hasLost.remove(r - 1);
                else hasLost.remove(r + 1);
            }
        }

        return n - hasLost.size();
    }

    @Override
    public int joyStick(String name) {
        return 0;
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

