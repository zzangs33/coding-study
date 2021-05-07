package com.coding.march.fifth;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Victory implements Hashing {

  public static void main(String[] args) {
    Victory vic = new Victory();
//    vic.camouflage(new String[][]{
//            {"yellow_hat", "headgear"},
//            {"bluesunglasses", "eyewear"},
//            {"green_turban","headgear"},
//            {"red_skirt", "pants"},
//            {"jean", "pants"},
//            {"slacks", "pants"},
//            {"grandma","face"},
//            {"grandfa", "face"}});
//    vic.telNumList(new String[]{"123", "456", "789"});
    vic.bestAlbum(new String[]{"classic", "pop", "classic", "classic", "pop"},new int[]{500, 600, 150, 800, 2500});
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
    //완료 한 애를 먼저 때려넣고 parti 하면 result를 stream 안해도 됨.

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

        if(set.contains(buffer.toString())) { // HashSet이어서 그렇다고 함. 랜덤 접근이 가능해서 성능이 좋음.
          return false;
        };
      }
      set.add(phone);
    }
    return answer;


//시간 초과 예제제
//   boolean answer = true;
//    for(int i =0; i<phone_book.length-1; i++) {
//      for(int j=i+1; j< phone_book.length; j++) {
//        if(phone_book[i].startsWith(phone_book[j])) return false;
//        if(phone_book[j].startsWith(phone_book[i])) return false;
//      }
//    }
//    return answer;

  }
  int totalCnt = 0;
  @Override
  public int camouflage(String[][] clothes) {
    HashMap<String, Integer> map = new HashMap<>();

    for(String[] StrArr: clothes) {
      String kinds = StrArr[1];
      Integer no = map.get(kinds);
      map.put(kinds, no == null ? 1 : no +1);
    }

    System.out.println("map :" + map.toString());

    List<Integer> numList = new ArrayList<>(map.values());

    for(int i=1; i<=numList.size(); i++) {
        int[] arr = numList.stream().mapToInt(no->no).toArray();
        combination(arr, new boolean[arr.length], 0, i);  //1번 예제 시간 초과....
    }

      return totalCnt;
  }

  public void combination(int[] arr, boolean[] visited, int depth, int remainNo) {
    if(remainNo == 0) {
      System.out.println("arr : " + Arrays.toString(arr) +  " : " +Arrays.toString(visited));
      totalCnt += calculate(arr, visited);
      return;
    }
    if(depth == arr.length) {
      return;
    }
    else {
      visited[depth] = true;
      combination(arr, visited, depth+1, remainNo-1);

      visited[depth] = false;
      combination(arr, visited, depth+1, remainNo);
    }
  };

  public int calculate(int[] arr, boolean[] visited) {
    int temp = 1;
    for(int i=0; i<arr.length; i++) {
      temp *= visited[i] ? arr[i] : 1;
    }
    System.out.println("calculate : " + temp);
    return temp;
  }

  @Override
  public int[] bestAlbum(String[] genres, int[] plays) {
    HashMap<String, Genre> map = new HashMap<>();

    for(int i =0; i<genres.length; i++) {
      String genre = genres[i];
      int playNumber = plays[i];
      Genre genreObj = map.get(genre);

      if(genreObj != null) {
        genreObj.totalPlay += playNumber;
        Song theFirst = genreObj.theFirst;
        Song theSecond = genreObj.theSecond;

        if(theFirst != null && theSecond != null) {
          int firstPlay = theFirst.playNumber;
          int secondPlay = theSecond.playNumber;

          if(playNumber > firstPlay) {
            theSecond.playNumber = firstPlay;
            theSecond.index = theFirst.index;

            theFirst.playNumber = playNumber;
            theFirst.index = i;
          } else if(playNumber > secondPlay) {
            theSecond.playNumber = playNumber;
            theSecond.index = i;
          }
        }  else if(theSecond == null) {
          Song newSong = new Song(playNumber, i);
          if (playNumber > theFirst.playNumber) {
            genreObj.theSecond = new Song(theFirst.playNumber, theFirst.index);
            genreObj.theFirst = newSong;
          } else {
            genreObj.theSecond = newSong;
          }
        }
      } else {
        Song theFirst = new Song(playNumber, i);
        Genre newGenre = new Genre(playNumber, theFirst);
        map.put(genre, newGenre);
      }
    }
    System.out.println("map : " +  map.toString());

    List<Genre> sortedGenreList = map.values().stream().sorted(
            (genre1, genre2) -> genre2.totalPlay - genre1.totalPlay // 내림차순
    ).collect(Collectors.toList());

    System.out.println(sortedGenreList);

    List<Integer> resultList = new ArrayList<>();

    for(Genre sortedGenre : sortedGenreList) {
      Song theFirst = sortedGenre.theFirst;;
      Song theSecond = sortedGenre.theSecond;

      resultList.add(theFirst.index);
      if(theSecond != null) {
        resultList.add(theSecond.index);
      }
    }

    return resultList.stream().mapToInt(i->i).toArray();
  }

  class Genre {
    int totalPlay = 0;
    Song theFirst = null;
    Song theSecond = null;

    Genre(int totalPlay, Song theFirst) {
      this.totalPlay = totalPlay;
      this.theFirst = theFirst;
    }

    @Override
    public String toString() {
      return "Genre{" +
              "totalPlay=" + totalPlay +
              ", theFirst=" + theFirst +
              ", theSecond=" + theSecond +
              '}';
    }
  }
  class Song {
    Song(int playNumber, int index) {
      this.playNumber = playNumber;
      this.index = index;
    }
    int playNumber = 0;
    int index = -1;

    @Override
    public String toString() {
      return "Song{" +
              "playNumber=" + playNumber +
              ", index=" + index +
              '}';
    }
  }
}
