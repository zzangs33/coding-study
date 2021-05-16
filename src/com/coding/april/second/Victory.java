package com.coding.april.second;

import java.util.*;
import java.util.stream.Collectors;

public class Victory implements Heap {

    public static void main(String[] args) {
        Victory vic = new Victory();
        vic.exe("diskController");
    }
    @Override
    public int hotter(int[] scoville, int K) {
        Queue<Integer> hotQ = new PriorityQueue<>(Arrays.stream(scoville).boxed().collect(Collectors.toList()));
        int cnt = 0;
        int notHot;
        while(!hotQ.isEmpty()) {
            notHot = hotQ.poll();
            if(notHot >= K) {
                return cnt;
            } else {
                if(hotQ.isEmpty()) return -1;
                int next = hotQ.poll();
                hotQ.offer(notHot + (next*2));
                cnt ++;
            }
        }

        return -1;
    }

    @Override
    public int diskController(int[][] jobs) {
        Queue<Integer> answerQ = new PriorityQueue<>();
        List<Task> taskList = new ArrayList<>();
        //들어온 순서대로 add
        for(int i=0; i<jobs.length; i++) {
            int[] taskArr = jobs[i];
            taskList.add(new Task(taskArr[0], i, taskArr[1]));
        }
        taskList.sort((t1, t2) -> t1.request - t2.request);
        System.out.println(taskList);

        Task before = taskList.get(0);
        int answer = before.duration + before.request;
        //들어온 순서대로 실행시켜서 계산
        for(int i=1; i<taskList.size(); i++) {
            Task next = taskList.get(i);
            next.start = before.duration + before.request;
            next.end = next.start + next.duration;
            answer += next.request + next.end;
            before = next;
        }
        answerQ.offer(answer/jobs.length);
        return answerQ.remove();

    }
//
//        ---------------------
//            -------
//       -----------------
//      --
//    ---
//   -----

    private static class Task {
        Task(){};
        Task(int request, int index, int duration) {
            this.request = request;
            this.index = index;
            this.duration = duration;
        }
        int request;
        int index;
        int duration;
        int start;
        int end;

        @Override
        public String toString() {
            return "Task{" +
                    "request=" + request +
                    ", index=" + index +
                    ", duration=" + duration +
                    ", start=" + start +
                    ", end=" + end +
                    '}';
        }
    }

    @Override
    public int[] dualPriorityQueue(String[] operations) {
        return new int[0];
    }
}
