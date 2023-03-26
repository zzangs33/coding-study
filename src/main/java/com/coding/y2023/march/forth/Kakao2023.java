package com.coding.y2023.march.forth;

import com.coding.testcase.TestCase;

import java.text.ParseException;

/**
 * @link "https://school.programmers.co.kr/learn/challenges?order=recent&partIds=37527"
 */
@TestCase("/com/coding/y2023/march/forth/kakao2023.json")
public interface Kakao2023 {
    int[] privacyInfo(String today, String[] terms, String[] privacies) throws ParseException;

    long delivery(int cap, int n, int[] deliveries, int[] pickups);

    int[] emoticon(int[][] users, int[] emoticons);

    int[] binaryTree(long[] numbers);

    String[] mergeTable(String[] commands);

    String escape(int n, int m, int x, int y, int r, int c, int k);

    int[] oneTwoThree(int[][] edges, int[] target);
}
