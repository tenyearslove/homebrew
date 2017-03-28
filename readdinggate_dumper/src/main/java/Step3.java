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
        String[] answer = new String[size];
        for (int p = 0; p < size; p++) {
            gObject = ja.get(p).getAsJsonObject();
            String question = v("Question");

            GetQuiz.writeOutput(String.format("%s", question));
        }
    }

    public static String v(String key) {
        return gObject.get(key).getAsString();
    }
}
