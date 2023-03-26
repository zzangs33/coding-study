package com.coding.y2023.march.forth;

import com.coding.testcase.TestCase;

/**
 * @link "https://school.programmers.co.kr/learn/challenges?order=recent&partIds=21366&page=1"
 */
@TestCase("/com/coding/y2023/march/forth/backend.json")
public interface Backend {
    int[] teethBrush(String[] enroll, String[] referral, String[] seller, int[] amount);

    int[] matrix(int rows, int columns, int[][] queries);
}
