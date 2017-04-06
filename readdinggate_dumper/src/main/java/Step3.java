import com.google.gson.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Step3 {

    static JsonObject gObject = null;

    public static void main(String[] args) {
        try {
            String qzJson = new Scanner(new File("step3.json")).useDelimiter("\\Z").next();
            getStep(qzJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getStep(String qzJson) {
        GetQuiz.writeOutput(String.format("%s (%s)", "STEP 3", GetQuiz.title));

        JsonObject root = new JsonParser().parse(qzJson).getAsJsonObject();
        JsonPrimitive d = (JsonPrimitive) root.get("d");
        JsonArray ja = new JsonParser().parse(d.getAsString()).getAsJsonArray();
        int size = ja.size();
        List<String> orderQuestion = new ArrayList<>();
        String[] answer = null;
        for (int p = 0; p < size; p++) {
            gObject = ja.get(p).getAsJsonObject();
            String question = v("Question");
            String correctText = v("CorrectText");
            String correct = v("Correct");
            String seq = v("Seq");

            if (correct == null)
                orderQuestion.add(question);
//                GetQuiz.writeOutput(String.format("%s", question));
            else {
                if (answer == null) {
                    answer = new String[size];
                }
                GetQuiz.writeOutput(String.format("%s. %s\n                                                                      [ True  |  False ]", seq, question));
                answer[p] = String.format("%s. %s [%s]", seq, correct.equals("1")?"True":"False", correctText);
            }
        }

        if (orderQuestion.size() > 0) {
            Collections.shuffle(orderQuestion);
            for (String question : orderQuestion) {
                GetQuiz.writeOutput(String.format("%s", question));
            }
        }

        if (answer != null) {
            GetQuiz.writeOutput("\n\nStep3 - Answer " + "(" + GetQuiz.title + ")\n");
            for (int p = 0; p < size; p++) {
                GetQuiz.writeOutput(String.format("%s", answer[p]));
            }
        }
    }

    public static String v(String key) {
        String retValue = null;
        if (gObject.has(key)) {
            JsonElement element = gObject.get(key);
            if (element.isJsonNull() == false) {
                retValue = element.getAsString();
            }
        }
        return retValue;
    }
}
