package com.coding.march.fifth;

import java.util.*;

public class Victory implements Hashing {

  public static void main(String[] args) {
    Victory vic = new Victory();
    vic.telNumList(new String[]{"123", "456", "789"});
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
    HashSet<String> set = new HashSet<>(Arrays.asList(phone_book));

    for(String phone : phone_book) {
      StringBuffer buffer = new StringBuffer();

      set.remove(phone);
      for(int i=0; i< phone.length(); i++) {
        buffer.append(phone.charAt(i));

        if(set.contains(buffer.toString())) {
          return false;
        };
      }
      set.add(phone);
    }
    return answer;


  }

  @Override
  public int camouflage(String[][] clothes) {
    HashMap<String, List<String>> map = new HashMap<>();

    for(String[] StrArr: clothes) {
      List<String> list = map.get(StrArr[1]);
      if(list != null) {
        List<String> newList = new ArrayList<>();
        newList.add(StrArr[0]);
        map.put(StrArr[1], newList);
      } else {
        list.add(StrArr[0]);
      }
    }
    //TODO 경우의 수 계산
      return 0;
  }

  @Override
  public int[] bestAlbum(String[] genres, int[] plays) {
    return new int[0];
  }
}
