import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.sun.xml.internal.fastinfoset.vocab.Vocabulary;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Step2 {

    static JsonObject gObject = null;

    public static void main(String[] args) {
        try {
            String qzJson = new Scanner(new File("step2.json")).useDelimiter("\\Z").next();
            getStep(qzJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getStep(String qzJson) {
        try {
            GetQuiz.writeOutput(String.format("%s (%s)", "STEP 2", GetQuiz.title));

            JsonObject root = new JsonParser().parse(qzJson).getAsJsonObject();
            JsonPrimitive d = (JsonPrimitive) root.get("d");
            JsonArray ja = new JsonParser().parse(d.getAsString()).getAsJsonArray();
            int size = ja.size();
            for (int p = 0; p < size; p++) {
                gObject = ja.get(p).getAsJsonObject();
                String vocabulary = v("Vocabulary");
                if (vocabulary == null) {
                    vocabulary = v("Word");
                }
                String korean = v("Korean");
                String britannica = v("Britannica");
                String seq = v("Seq");
                GetQuiz.writeOutput(String.format("[%s] %s", seq, vocabulary));
                GetQuiz.writeOutput(String.format(" - %s", korean));
                GetQuiz.writeOutput(String.format(" - %s", britannica));

                GetQuiz.writeOutput("");
            }

            GetQuiz.writeOutput("\nVocabulary List (" + GetQuiz.title + ")\n");
            for (int p = 0; p < size; p++) {
                gObject = ja.get(p).getAsJsonObject();

                String vocabulary = v("Vocabulary");
                if (vocabulary == null) {
                    vocabulary = v("Word");
                }
                String seq = v("Seq");
                GetQuiz.writeOutput(String.format("[%s]\t%s", seq, vocabulary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String v(String key) {
        String retValue = null;
        if (gObject.has(key)) {
            retValue = gObject.get(key).getAsString();
        }
        return retValue;
    }
}
