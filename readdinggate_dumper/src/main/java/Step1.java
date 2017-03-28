import com.google.gson.*;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Step1 {

    static JsonObject gObject = null;

    public static void main(String[] args) {
        try {
            String qzJson = new Scanner(new File("step1.json")).useDelimiter("\\Z").next();
            getStep(qzJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getStep(String qzJson) {
        GetQuiz.writeOutput(String.format("%s (%s)", "STEP 1", GetQuiz.title));

        JsonObject root = new JsonParser().parse(qzJson).getAsJsonObject();
        JsonPrimitive d = (JsonPrimitive) root.get("d");
        JsonArray ja = new JsonParser().parse(d.getAsString()).getAsJsonArray();
        int size = ja.size();
        String[] answer = new String[size];
        for (int p = 0 ; p < size ; p++) {
            gObject = ja.get(p).getAsJsonObject();
            String quizNo = v("Seq");
            String question = v("Question");
            String correctText = v("CorrectText");
            String[] example = new String[4];
            example[0] = v("Example1");
            example[1] = v("Example2");
            example[2] = v("Example3");
            example[3] = v("Example4");
            List exampleList = Arrays.asList(example);
            Collections.shuffle(exampleList);
            example = (String []) exampleList.toArray();

            for (int i = 0 ; i < example.length ; i++) {
                if (example[i].equals(correctText)) {
                    answer[p] = "" + (i + 1);
                }
            }
            GetQuiz.writeOutput(String.format("[%s] %s", quizNo, question));
            for (int i = 0 ; i < example.length ; i++) {
                GetQuiz.writeOutput(String.format("  %d. %s", (i+1), example[i]));
            }
            GetQuiz.writeOutput("");
        }
        GetQuiz.writeOutput("\n\nStep1 - Answer " + "(" + GetQuiz.title + ")\n");
        for (int p = 0 ; p < size ; p++) {
            GetQuiz.writeOutput(String.format("%s - %s", (p+1), answer[p]));
        }
    }

    public static String v(String key) {
        return gObject.get(key).getAsString();
    }
}
