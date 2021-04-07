package com.coding.march.fifth;

import java.util.*;

public class Victory implements Problem{

  public static void main(String[] args) {

  }

  @Override
  public String theNotFinished(String[] participant, String[] completion) {
    HashMap<String, Integer> map = new HashMap<>();

    for(String partiPerson : participant) {
      Integer no = map.get(partiPerson);
      map.put(partiPerson, no == null ? 1 : ++no);
    }

    for(String completePerson : completion) {
      Integer no = map.get(completePerson);
      if(no == null) return completePerson;
      else if(no == 1) map.remove(completePerson);
      else map.put(completePerson, --no);
    }

    return map.keySet().stream().findAny().get();

  }

  @Override
  public boolean telNumList(String[] phone_book) {
    boolean answer = true;

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
