package com.coding.april.first;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class Victory implements QueueNStack {

    @Override
    public int truckThroughBridge(int bridge_length, int weight, int[] truck_weights) {
        int sec = 0;
        List<Integer> passedTrucks = new ArrayList<>();
        Queue<Integer> bridgeQ = new LinkedBlockingQueue<>(
                Arrays.stream(new int[bridge_length]).boxed().collect(Collectors.toList()));
        List<Integer> trucksList = Arrays.stream(truck_weights).boxed().collect(Collectors.toList());

        int totalWeight = 0;

        while(passedTrucks.size() < truck_weights.length) {
            if(bridgeQ.peek() == 0) {
                bridgeQ.poll();
            } else {
                totalWeight = totalWeight - bridgeQ.peek();
                passedTrucks.add(bridgeQ.poll());
            }

            if(trucksList.size() != 0) {
                if(weight >= totalWeight + trucksList.get(0)) {
                    bridgeQ.offer(trucksList.get(0));
                    totalWeight += trucksList.remove(0);
                } else {
                    bridgeQ.offer(0);
                }
            }

            sec++;
        } // while
        return sec;
    }

    @Override
    public int[] stockValue(int[] prices) {
        int length = prices.length;
        int[] answer = new int[length];

        for(int i=0; i<length-1;i++) {
            int cnt = 0;

            for(int j=i+1; j<length;j++) {
                if(prices[i] <= prices[j]) {
                    cnt++;
                    if(j == length-1) {
                        answer[i] = cnt;
                        break;
                    }
                } else {
                    cnt++;
                    answer[i] = cnt;
                    break;
                }
            } // inner
        } // outer

        return answer;
    }

    @Override
    public int[] developModule(int[] progresses, int[] speeds) {
        int[] days = new int[progresses.length];
        for(int i=0; i<progresses.length; i++) {
            days[i] = (int) Math.ceil((double)(100-progresses[i]) / speeds[i]);
        }

        List<Integer> countList = new ArrayList<>();
        int cnt = 1;
        outer : for(int i = 0; i < days.length; i++ ) {
            if(i == days.length-1) {
                countList.add(cnt);
                break ;
            }
            for(int j=i+1; j < days.length; j++) {
                if(days[i] >= days[j]) {
                    cnt ++;
                    if(j == days.length-1) {
                        countList.add(cnt);
                        break outer;
                    }
                } else {
                    countList.add(cnt);
                    i=j-1;
                    cnt = 1;
                    break;
                }
            } // inner
        } // outer

        return countList.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int printer(int[] priorities, int location) {
        int answer = 0;
        Queue<Integer> q = new LinkedBlockingQueue<>(
                Arrays.stream(priorities).boxed().collect(Collectors.toList()));

        while(!q.isEmpty()) {
            int max = 0;
            for(int item : q) {
                if(max <item) max = item;
            }

            if(q.peek() != max) {
                q.offer(q.poll());
                if(location == 0) location = q.size() -1;
                else location--;
            } else {
                if(location == 0) {
                    answer++;
                    break;
                } else {
                    q.remove();
                    answer++;
                    location--;

                }
            }
        } //while
        return answer;
    }
}
