package com.coding.april.fifth;

import java.util.ArrayList;
import java.util.List;

public class Victory implements Greedy {

    public static void main(String[] args) {
        Victory vic = new Victory();
        vic.exe("gymSuit");
    }
    @Override
    public int gymSuit(int n, int[] lost, int[] reserve) {
        List<Integer> reserveList = new ArrayList<>();
        List<Integer> lostList = new ArrayList<>();

        for (int no : reserve) reserveList.add(no);
        for (int no : lost) {
            if(reserveList.contains(no)) reserveList.remove((Integer) no);
            else lostList.add(no);
        }
        System.out.println("1st lostList :" + lostList);
        System.out.println("1st reserveList :" + reserveList);
        List<Boolean> canDoPhy = new ArrayList<>();

        boolean alreadyChange = false;
        for(Integer no : lostList) {
            if(reserveList.size() == 0) break;
            if(reserveList.contains(no-1)) {
                canDoPhy.add(true);
                reserveList.remove(reserveList.indexOf(no-1));
            }
            if(reserveList.size() >0 && reserveList.contains(no+1)) {
                canDoPhy.add(true);
                reserveList.remove(reserveList.indexOf(no+1));
            }
            else canDoPhy.add(false);
            System.out.println("reserveList :" + reserveList);
        }


        return (int) (n - canDoPhy.stream().filter(is-> !is).findAny().stream().count());
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
