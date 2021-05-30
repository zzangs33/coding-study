package com.coding.april.third;

import java.util.*;
import java.util.stream.Collectors;

public class Victory implements Sorting {

    public static void main(String[] args) {
        Victory vic = new Victory();
//        System.out.println(Arrays.toString("6223".toCharArray()));
        vic.exe("hIndex");
    }
    @Override
    public int[] kthNumber(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        int answerIndex = 0;
        //주어진 명령어 하나씩 돌린다..
        for(int[] comm : commands) {
            //명령어 1 인덱스와 0인덱스를 빼고 1 을 더해야 추출해내는 배열의 크기가 나온다..
            int endIndex = comm[1]-1;
            int beginIndex = comm[0]-1;
            int targetIndex = comm[2]-1;
            int[] temp = new int[endIndex - beginIndex + 1];
            int index = 0;

            //주어진 범위만큼 잘라서 temp 배열에 넣는다.
            for(int i=beginIndex; i<=endIndex; i++) {
                temp[index++] = array[i];
            }

            Arrays.sort(temp);

            answer[answerIndex++] = temp[targetIndex];
        } // 큰 for..

        return answer;
    }

    @Override
    public String theBiggestNumber(int[] numbers) {
        List<String> tmpList = new ArrayList<>();
        boolean hasNotZero = false;
        for(int no : numbers) {
            if(no != 0) hasNotZero = true;
            tmpList.add(String.valueOf(no));
        }
        if(!hasNotZero) return "0";

        Collections.sort(tmpList, (s1, s2) -> Integer.parseInt(s2+s1) - Integer.parseInt(s1+s2));

        System.out.println(tmpList);
        StringBuilder builder = new StringBuilder();
        for(String str : tmpList) {
            builder.append(str);
        }
        return builder.toString();
    }

    @Override
    public int hIndex(int[] citations) {
        List<Integer> list = new ArrayList<>();
        for(int no : citations) list.add(no);

        list.sort(Comparator.reverseOrder());
        System.out.println(" list : " + list);
        Integer before = list.get(0);
        if(before == 0) return 0;
        if(list.size() > 1) {
            for(int i= 1; i<list.size(); i++) {
                Integer after = list.get(i);
                if(after == 0) return list.get(i-1) != 1 ? 0 : 1;  // [2,0]
                if(after == i || after == i+1)
                    return after;
                if(after < i) return i;
            }
            return citations.length;
        } else
            return before == 1 ? before : 0; // [1]
    }
}
