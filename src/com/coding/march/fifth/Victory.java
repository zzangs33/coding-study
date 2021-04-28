package com.coding.march.fifth;

import java.util.*;
import java.util.stream.Collectors;

public class Victory implements Hashing {

  public static void main(String[] args) {
    Victory vic = new Victory();
//    vic.telNumList(new String[]{"123", "456", "789"});
//    vic.bestAlbum(new String[]{"classic", "pop", "classic", "classic", "pop"},new int[]{500, 600, 150, 800, 2500});
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
    HashMap<String, Integer> map = new HashMap<>();

    for(String[] StrArr: clothes) {
      String kinds = StrArr[1];
      Integer no = map.get(kinds);
      if(no == null) {
        map.put(kinds, 1);
      } else {
        map.put(kinds, no+1);
      }

    }

    //TODO 경우의 수 계산
      return 0;
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

    List<Genre> sortedGenreList = map.values().stream().sorted(
            (genre1, genre2) -> -Integer.compare(genre1.totalPlay, genre2.totalPlay)
    ).collect(Collectors.toList());


    List<Integer> resultList = new ArrayList<>();

    for(Genre sortedGenre : sortedGenreList) {
      System.out.println("sortedGenre : " + sortedGenre.toString());
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

    Genre() { }
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
    Song () {};
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
