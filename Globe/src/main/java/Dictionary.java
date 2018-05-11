import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Dictionary {
    static Map<String, Integer> dictionary = new HashMap<>();

    public static void init() throws Exception {
        Scanner scanner = new Scanner(new FileInputStream("wordnetWords.txt"));
        while (scanner.hasNext()) {
            String word = scanner.next();
            dictionary.put(word, 0);
        }
    }

    public static boolean isWord(String str) {
        if (dictionary.isEmpty())
            throw new IllegalStateException();

        return dictionary.get(str) != null;
    }
}
