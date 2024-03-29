package com.coding.y2023.march.forth;

import com.coding.testcase.TestCaseExecutor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Kms27420 implements Kakao2023, Backend {
    public static void main(String[] args) {
        TestCaseExecutor.execute(new Kms27420());
    }

    @Override
    public int[] privacyInfo(String today, String[] terms, String[] privacies) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date todayDate = sdf.parse(today);
        Calendar cal = Calendar.getInstance();
        Map<String, Long> typeExpireMap = new HashMap<>();
        for (String term : terms) {
            String[] typeMonth = term.split(" ");
            cal.setTime(todayDate);
            cal.add(Calendar.MONTH, -Integer.parseInt(typeMonth[1]));
            typeExpireMap.put(typeMonth[0], cal.getTimeInMillis());
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < privacies.length; i++) {
            String[] dateType = privacies[i].split(" ");
            if (typeExpireMap.get(dateType[1]) >= sdf.parse(dateType[0]).getTime()) {
                result.add(i + 1);
            }
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public long delivery(int cap, int n, int[] deliveries, int[] pickups) {
        return 0L;
    }

    @Override
    public int[] emoticon(int[][] users, int[] emoticons) {
        return new int[0];
    }

    @Override
    public int[] binaryTree(long[] numbers) {
        return Arrays.stream(numbers)
                .mapToInt(number -> isPerfectBinaryTree(toFullBinary(number), true) ? 1 : 0)
                .toArray();
    }

    private static String toFullBinary(long number) {
        StringBuilder result = new StringBuilder(Long.toBinaryString(number));
        int nodeLength = 1;
        int nodeLevelCnt = 1;
        while (nodeLength < result.length()) {
            nodeLevelCnt *= 2;
            nodeLength += nodeLevelCnt;
        }
        int repeat = nodeLength - result.length();
        for (int i = 0; i < repeat; i++) {
            result.insert(0, "0");
        }
        return result.toString();
    }

    private static boolean isPerfectBinaryTree(String binary, boolean first) {
        if (binary.isEmpty()) {
            return true;
        }
        int root = binary.length() / 2;
        String left = binary.substring(0, root);
        String right = binary.substring(root + 1);
        if (binary.charAt(root) == '0') {
            return !first && isZeroNode(left) && isZeroNode(right);
        }
        return isPerfectBinaryTree(left, false) && isPerfectBinaryTree(right, false);
    }

    private static boolean isZeroNode(String binary) {
        if (binary.isEmpty()) {
            return true;
        }
        int root = binary.length() / 2;
        String left = binary.substring(0, root);
        String right = binary.substring(root + 1);
        return binary.charAt(root) == '0' && isZeroNode(left) && isZeroNode(right);
    }

    @Override
    public String[] mergeTable(String[] commands) {
        return new String[0];
    }

    @Override
    public String escape(int n, int m, int x, int y, int r, int c, int k) {
        return null;
    }

    @Override
    public int[] oneTwoThree(int[][] edges, int[] target) {
        return new int[0];
    }

    @Override
    public int[] teethBrush(String[] enroll, String[] referral, String[] seller, int[] amount) {
        Map<String, List<Integer>> nameAmountListMap = new HashMap<>();
        for (int i = 0; i < seller.length; i++) {
            nameAmountListMap.computeIfAbsent(seller[i], k -> new ArrayList<>()).add(amount[i] * 100);
        }
        PhaseNode node = new PhaseNode();
        for (int i = 0; i < enroll.length; i++) {
            node.addChild(referral[i], enroll[i], nameAmountListMap.computeIfAbsent(enroll[i], k -> Collections.emptyList()));
        }
        Map<String, Integer> nameProfitMap = node.getNameProfitMap();
        return Arrays.stream(enroll).mapToInt(nameProfitMap::get).toArray();
    }

    private static class PhaseNode {
        private final String name;
        private int profit;

        private final PhaseNode referral;
        private final List<PhaseNode> children = new ArrayList<>();

        private PhaseNode(PhaseNode referral, String name, List<Integer> amountList) {
            this.referral = referral;
            this.name = name;
            amountList.forEach(this::chargeProfit);
        }

        public PhaseNode() {
            this(null, "-", Collections.emptyList());
        }

        private void addChild(String referral, String name, List<Integer> amountList) {
            if (referral.equals(this.name)) {
                this.children.add(new PhaseNode(this, name, amountList));
            } else {
                for (PhaseNode child : this.children) {
                    child.addChild(referral, name, amountList);
                }
            }
        }

        private void chargeProfit(int profit) {
            this.profit += profit;
            if (this.referral != null) {
                int charge = profit / 10;
                this.profit -= charge;
                this.referral.chargeProfit(charge);
            }
        }

        private Map<String, Integer> getNameProfitMap() {
            Map<String, Integer> map = new HashMap<>();
            map.put(this.name, this.profit);
            this.children.forEach(child -> map.putAll(child.getNameProfitMap()));
            return map;
        }
    }

    @Override
    public int[] matrix(int rows, int columns, int[][] queries) {
        int[][] matrix = new int[rows][columns];
        int cnt = 1;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = cnt++;
            }
        }

        int[] result = new int[queries.length];
        int rIdx = 0;
        for (int[] query : queries) {
            List<int[]> pointList = new ArrayList<>();
            for (int col = query[1]; col <= query[3]; col++) {
                pointList.add(new int[] {query[0] - 1, col - 1});
            }
            for (int row = query[0] + 1; row <= query[2] - 1; row++) {
                pointList.add(new int[] {row - 1, query[3] - 1});
            }
            for (int col = query[3]; col >= query[1]; col--) {
                pointList.add(new int[] {query[2] - 1, col - 1});
            }
            for (int row = query[2] - 1; row >= query[0]; row--) {
                pointList.add(new int[] {row - 1, query[1] - 1});
            }
            int min = Integer.MAX_VALUE;
            int prev = - 1;
            for (int[] point : pointList) {
                int cur = matrix[point[0]][point[1]];
                if (prev > -1) {
                    matrix[point[0]][point[1]] = prev;
                    min = Math.min(min, prev);
                }
                prev = cur;
            }
            result[rIdx++] = min;
        }
        return result;
    }

    @Override
    public int[] lotto(int[] lottos, int[] win_nums) {
        Set<Integer> winSet = IntStream.of(win_nums).boxed().collect(Collectors.toSet());
        int best = 7;
        int worst = 7;
        for (int lotto : lottos) {
            if (winSet.contains(lotto)) {
                worst--;
                best--;
            } else if (lotto == 0) {
                best--;
            }
        }
        return new int[] {Math.min(6, best), Math.min(6, worst)};
    }
}
