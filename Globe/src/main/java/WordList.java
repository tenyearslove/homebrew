import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class WordList {
    public static void main(String[] args) {
        process("index.html");
    }

    public static void process(String fileName) {
        try {
            Dictionary.init();

            Scanner scanner = new Scanner(new FileInputStream(fileName));
            Map<String, Integer> wordMap = new HashMap<>();
            while (scanner.hasNext()) {
                String word = trimWord(scanner.next());
                if (Dictionary.isWord(word) == false)
                    continue;
                Integer count = wordMap.get(word);
                if (count == null) {
                    wordMap.put(word, 1);
                } else {
                    wordMap.put(word, count + 1);
                }
            }
            List<Word> wordList = new ArrayList<Word>();
            Set<Map.Entry<String, Integer>> wordSet = wordMap.entrySet();
            for (Map.Entry<String, Integer> entry : wordSet) {
                wordList.add(new Word(entry.getKey(), entry.getValue()));
            }

            Collections.sort(wordList);

            for (Word word : wordList) {
                System.out.println(word.english);// + "|" + word.priority + "|");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Word implements Comparable<Word> {
        public Word(String english, Integer priority) {
            this.english = english;
            this.priority = priority;
        }
        String english;
        Integer priority;

        @Override
        public int compareTo(Word that) {
            if (this.priority == that.priority)
                return this.english.compareTo(that.english);
            return Integer.compare(that.priority, this.priority);
        }
    }

    private static String trimWord(String word) {
        return word.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }
}
