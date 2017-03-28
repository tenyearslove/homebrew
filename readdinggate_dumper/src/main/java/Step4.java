import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Step4 {

    static JsonObject gObject = null;

    public static void main(String[] args) {
        try {
            String qzJson = new Scanner(new File("step4.json")).useDelimiter("\\Z").next();
            getStep(qzJson, "TITLE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getStep(String qzJson, String title) {
        GetQuiz.writeOutput(String.format("%s (%s)", "STEP 4", GetQuiz.title));

        JsonObject root = new JsonParser().parse(qzJson).getAsJsonObject();
        JsonPrimitive d = (JsonPrimitive) root.get("d");
        JsonArray ja = new JsonParser().parse(d.getAsString()).getAsJsonArray();
        int size = ja.size();
        String[] answer = new String[size];
        for (int p = 0 ; p < size ; p++) {
            gObject = ja.get(p).getAsJsonObject();
            String quizNo = v("QuizNo");
            String question = v("Question");
            String correct = v("Correct");

            String soundPath = v("SoundPath");
            String contentsId = v("ContentsId");
            saveUrl(title + "-" + quizNo + ".mp3", soundPath);

            GetQuiz.writeOutput(String.format("[%s] %s", quizNo, question.replaceAll("┒", "[          ]")));

            answer[p] = correct.replaceAll("┒", ", ");
        }
        GetQuiz.writeOutput("\n\nStep4 - Answer " + "(" + GetQuiz.title + ")\n");
        for (int p = 0 ; p < size ; p++) {
            GetQuiz.writeOutput(String.format("%s - %s", (p+1), answer[p]));
        }
    }

    public static String v(String key) {
        return gObject.get(key).getAsString();
    }

    public static void saveUrl(final String filename, final String urlString) {
        try {
            BufferedInputStream in = null;
            FileOutputStream fout = null;
            try {
                in = new BufferedInputStream(new URL(urlString).openStream());
                fout = new FileOutputStream(filename);

                final byte data[] = new byte[1024];
                int count;
                while ((count = in.read(data, 0, 1024)) != -1) {
                    fout.write(data, 0, count);
                }
            } finally {
                if (in != null) {
                    in.close();
                }
                if (fout != null) {
                    fout.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
