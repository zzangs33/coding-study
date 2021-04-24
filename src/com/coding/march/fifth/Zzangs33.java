package com.coding.march.fifth;

import java.util.*;
import java.util.stream.Collectors;

public class Zzangs33 implements Hashing {
    public static void main(String[] args) {

    }

    @Override
    public String theNotFinished(String[] participant, String[] completion) {
        String answer = "";
        Map<String, Integer> marathoners = new HashMap<>();

        for (String comp : completion) {
            Integer same = marathoners.get(comp);
            marathoners.put(comp, same == null ? 1 : ++same);
        }

        for (String part : participant) {
            Integer same = marathoners.get(part);

            if (same == null || same <= 0) return part;

            marathoners.put(part, --same);
        }

        return answer;
    }

    @Override
    public boolean telNumList(String[] phone_book) {
        Set<String> bookDic = new HashSet<>(Arrays.asList(phone_book));

        for (String number : phone_book) {
            bookDic.remove(number);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < number.length(); i++) {
                sb.append(number.charAt(i));

                if (bookDic.contains(sb.toString())) return false;
            }
            bookDic.add(number);
        }
        return true;
    }

    @Override
    public int camouflage(String[][] clothes) {
        Map<String, Integer> clothSet = new HashMap<>();
        for (String[] cloth : clothes) {
            String type = cloth[1];

            Integer num = clothSet.get(type);
            clothSet.put(type, num == null ? 1 : ++num);
        }

        return clothSet.values().stream().map(i -> ++i).reduce(1, (integer, integer2) -> integer * integer2) - 1;
    }

    @Override
    public int[] bestAlbum(String[] genres, int[] plays) {
        Map<String, Genre> genreMap = new HashMap<>();
        for (int i = 0; i < genres.length; i++) {
            String curGenre = genres[i];
            int curPlays = plays[i];

            Genre genre = genreMap.get(curGenre);

            if (genre == null) {
                genre = new Genre();
                genre.totalPlay = curPlays;
                genre.first = i;
            } else {
                genre.totalPlay += curPlays;
                if (curPlays > plays[genre.first]) {
                    genre.second = genre.first;
                    genre.first = i;
                } else if (genre.second == -1 || curPlays > plays[genre.second]) genre.second = i;
            }

            genreMap.put(curGenre, genre);
        }

        List<Genre> result = genreMap.values().stream()
                .sorted((a, b) -> a.totalPlay >= b.totalPlay ? -1 : 1)
                .collect(Collectors.toList());

        List<Integer> answer = new ArrayList<>();
        for (Genre genre : result) {
            answer.add(genre.first);
            if (genre.second != -1) {
                answer.add(genre.second);
            }
        }

        return answer.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private static class Genre {
        int totalPlay;
        int first = -1;
        int second = -1;
    }
}
