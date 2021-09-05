package com.coding.april.fifth;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Kms27420 implements Greedy {
    public static void main(String[] args) {
        Kms27420 instance = new Kms27420();
        instance.exe("gymSuit");
//        instance.exe("joyStick");
//        instance.exe("makeABigNumber");
//        instance.exe("lifeboat");
//        instance.exe("linkingIslands");
//        instance.exe("speedTrap");
    }

    @Override
    public int gymSuit(int n, int[] lost, int[] reserve) {
        Set<Integer> reserveSet = Arrays.stream(reserve)
                .boxed()
                .collect(Collectors.toSet());
        List<Integer> lostList = Arrays.stream(lost)
                .boxed()
                .filter(l -> !reserveSet.remove(l))
                .sorted()
                .collect(Collectors.toList());

        int lostCnt = lostList.size();

        for (Integer l : lostList) {
            for (int i : new int[]{l - 1, l + 1}) {
                if (reserveSet.remove(i)) {
                    lostCnt -= 1;
                    break;
                }
            }
        }

        return n - lostCnt;
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
