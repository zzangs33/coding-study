package com.coding.march.fifth;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Kms27420 implements Hashing {
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Kms27420 instance = new Kms27420();
        instance.exe("theNotFinished");
        instance.exe("telNumList");
        instance.exe("camouflage");
        instance.exe("bestAlbum");
    }

    @Override
    public String theNotFinished(String[] participant, String[] completion) {
        final Map<String, Integer> nameCntMap = new HashMap<>();
        for (String name : participant)
            nameCntMap.compute(name, (key, value) -> value != null ? value + 1 : 1);

        for (String name : completion) {
            Integer cnt = nameCntMap.get(name);
            if (cnt != null) {
                cnt -= 1;
                if (cnt > 0) nameCntMap.put(name, cnt);
                else nameCntMap.remove(name);
            }
        }
        return nameCntMap.keySet().stream().findFirst().orElseThrow(NullPointerException::new);
    }

    @Override
    public boolean telNumList(String[] phone_book) {
        final Node node = new Node();
        for (String phone : phone_book)
            if (node.push(phone)) return false;
        return true;
    }

    private static final class Node {
        private Node[] nodes;
        private boolean done;

        public boolean push(String str) {
            if (this.done) return true;
            if (str == null || str.length() == 0) {
                this.done = true;
                return nodes != null;
            }
            if (this.nodes == null) this.nodes = new Node[10];
            int idx = str.charAt(0) - '0';
            if (this.nodes[idx] == null) this.nodes[idx] = new Node();
            return this.nodes[idx].push(str.substring(1));
        }
    }

    @Override
    public int camouflage(String[][] clothes) {
        final Map<String, Integer> clothCntMap = new HashMap<>();
        for (String[] clothType : clothes)
            clothCntMap.compute(clothType[1], (key, value) -> value == null ? 1 : value + 1);
        int result = clothCntMap.values().stream().reduce(0, Integer::sum);
        final Map<Integer, Integer> totalCache = new HashMap<>();
        totalCache.put(1, result);
        final Map<String, Integer> partCache = new HashMap<>(clothCntMap);
        for (int i = 2; i <= clothCntMap.size(); i++) {
            int beforeTotal = totalCache.get(i - 1);
            int sum = 0;
            int partSum = 0;
            for (Map.Entry<String, Integer> e : clothCntMap.entrySet()) {
                String type = e.getKey();
                int cnt = e.getValue();
                partSum += partCache.get(type);
                int partCnt = cnt * (beforeTotal - partSum);
                if (partCnt < 0) partCnt = 0;
                partCache.put(type, partCnt);
                sum += partCnt;
                result += partCnt;
            }
            totalCache.put(i, sum);
        }
        return result;
    }

    @Override
    public int[] bestAlbum(String[] genres, int[] plays) {
        final Map<String, SortedQueue> genreQueueMap = new HashMap<>();
        int idx = 0;
        for (int play : plays)
            genreQueueMap.computeIfAbsent(genres[idx], k -> new SortedQueue())
                    .push(idx++, play);
        final List<SortedQueue> list = new ArrayList<>(genreQueueMap.values());
        list.sort(Comparator.comparing(SortedQueue::getTotal).reversed());
        final List<Integer> result = new ArrayList<>();
        for (SortedQueue item : list)
            for (int i = 0; i < 2; i++) {
                int temp = item.pop();
                if (temp >= 0) result.add(temp);
            }
        return result.stream().mapToInt(i -> i).toArray();
    }

    private static final class SortedQueue {
        private SortedQueue next;
        private Integer idx, value, total = 0;

        public void push(Integer idx, Integer value) {
            if (this.value == null) {
                this.idx = idx;
                this.value = value;
                this.total = value;
            } else {
                this.total += value;
                if (this.next == null) this.next = new SortedQueue();
                if (this.value > value || this.value.equals(value) && this.idx < idx) this.next.push(idx, value);
                else {
                    this.next.push(this.idx, this.value);
                    this.idx = idx;
                    this.value = value;
                }
            }
        }

        public Integer pop() {
            Integer result = this.idx;
            if (this.next != null) {
                this.idx = this.next.idx;
                this.value = this.next.value;
                this.total = this.next.total;
                this.next = this.next.next;
            } else {
                this.idx = null;
                this.value = null;
                this.total = 0;
            }
            return result == null ? -1 : result;
        }

        public Integer getTotal() {
            return this.total;
        }
    }
}
