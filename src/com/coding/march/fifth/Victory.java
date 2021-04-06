package com.coding.march.fifth;

import java.util.*;

public class Victory implements Problem{

  public static void main(String[] args) {

  }

  @Override
  public String theNotFinished(String[] participant, String[] completion) {
    Arrays.sort(participant);
    Arrays.sort(completion);

    int i = 0;
    for(i=0; i<completion.length; i++) {
      if(!completion[i].equals(participant[i])){
        return participant[i];
      }
    }
    return participant[i];
  }

  @Override
  public boolean telNumList(String[] phone_book) {
    boolean answer = true;
    List<String> list =Arrays.asList(phone_book);
    HashSet<String> set = new HashSet<>(list);
    for(String number : list) {
      if(set.stream().filter(no -> no.startsWith(number) && !no.equals(number))
              .findFirst().isPresent()) {
        answer = false;
        return answer;
      }
    }

    return answer;
  }

  @Override
  public int camouflage(String[][] clothes) {
    return 0;
  }

  @Override
  public int[] bestAlbum(String[] genres, int[] plays) {
    return new int[0];
  }
}
