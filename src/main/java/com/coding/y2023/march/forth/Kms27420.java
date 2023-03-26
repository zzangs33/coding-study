package com.coding.y2023.march.forth;

import com.coding.testcase.TestCaseExecutor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        return new int[0];
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
        return new int[0];
    }
}
