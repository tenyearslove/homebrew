import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gson.Dic;
import gson.Phrase;
import gson.Word;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Globe {
    public static void main(String[] args) {
        doGlobe("hard.txt");
    }

    public static void doGlobe(String fileName) {
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            OkHttpClient client = new OkHttpClient.Builder().build();
            while (scanner.hasNext()) {
                String word = scanner.next();

                Dic dic = getDictionary(client, word);
                if (dic == null) continue;
                printDic(word, dic);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Dic getDictionary(OkHttpClient client, String word) throws IOException {
        try {
            Request request = new Request.Builder()
                    .url("https://glosbe.com/gapi/translate?from=eng&dest=kor&format=json&pretty=true&phrase=" + word)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonString = response.body().string();

            Gson gson = new Gson();
            Dic dic = gson.fromJson(jsonString, Dic.class);

            return dic;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void printDic(String word, Dic dic) {
        try {
            List<String> koreanList = new ArrayList<>();
            for (Word koreanWord : dic.getTuc()) {
                Phrase phrase = koreanWord.getPhrase();
                if (phrase != null) {
                    koreanList.add(phrase.getText());
                }
            }
            if (koreanList.size() == 0) {
                try {
                    String meaning = dic.getTuc()[0].getMeanings()[0].getText();
                    meaning.replaceAll("\\<i\\>", "").replaceAll("\\<\\/i\\>", "").replaceAll("\\[i\\]", "").replaceAll("\\[\\/i\\]", "");
                    koreanList.add(meaning);
                } catch (Exception e) {

                }
            }
            System.out.print(word + "|");
            String joined = String.join(", ", koreanList);
            System.out.println(joined);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
