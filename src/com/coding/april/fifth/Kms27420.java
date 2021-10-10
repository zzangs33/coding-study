package com.coding.april.fifth;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Kms27420 implements Greedy {
    public static void main(String[] args) {
        Kms27420 instance = new Kms27420();
//        instance.exe("gymSuit");
//        instance.exe("joyStick");
        instance.exe("makeBigNumber");
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
        int zeroCnt = 0;
        int[] cnts = new int[name.length()];
        for (int i = 0; i < cnts.length; i += 1) {
            int sub = name.charAt(i) - 'A';
            if (sub <= 13) {
                cnts[i] = sub;
            } else {
                cnts[i] = 13 - (sub % 13);
            }
            if (cnts[i] == 0) {
                zeroCnt += 1;
            }
        }
        int totalMove = 0;
        int cursor = 0;
        while (zeroCnt != name.length()) {
            if (cnts[cursor] > 0) {
                totalMove += cnts[cursor];
                cnts[cursor] = 0;
                zeroCnt += 1;
            } else {
                int movement = calcMovement(cursor, cnts);
                totalMove += Math.abs(movement);
                cursor = calcCursor(cursor, cnts, movement);
            }
        }
        return totalMove;
    }

    private static int calcCursor(int current, int[] cnts, int moveCnt) {
        int cursor = current + moveCnt;
        while (cursor < 0 || cursor >= cnts.length) {
            if (cursor < 0) {
                cursor += cnts.length;
            } else {
                cursor -= cnts.length;
            }
        }
        return cursor;
    }

    private static int calcMovement(int current, int[] cnts) {
        int left = -1;
        int cursor = calcCursor(current, cnts, left);
        while (left < 0 && cnts[cursor] <= 0) {
            cursor = calcCursor(current, cnts, --left);
            if (cursor == current) {
                left = 0;
            }
        }
        int right = 1;
        cursor = calcCursor(current, cnts, right);
        while (right > 0 && cnts[cursor] <= 0) {
            cursor = calcCursor(current, cnts, ++right);
            if (cursor == current) {
                right = 0;
            }
        }
        return -left < right ? left : right;
    }

    @Override
    public String makeBigNumber(String number, int k) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number.length(); i += 1) {
            int rest = (number.length() - k) - builder.length();
            if (rest > 0) {
                boolean remove = false;
                for (int compI = number.length() - rest; compI > i; compI -= 1) {
                    if (number.charAt(i) < number.charAt(compI)) {
                        remove = true;
                        break;
                    }
                }
                if (!remove) {
                    builder.append(number.charAt(i));
                }
            }
        }
        return builder.toString();
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
