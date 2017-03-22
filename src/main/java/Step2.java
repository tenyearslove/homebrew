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
        System.out.println("STEP 2\n");

        JsonObject root = new JsonParser().parse(qzJson).getAsJsonObject();
        JsonPrimitive d = (JsonPrimitive) root.get("d");
        JsonArray ja = new JsonParser().parse(d.getAsString()).getAsJsonArray();
        int size = ja.size();
        String[] answer = new String[size];
        for (int p = 0 ; p < size ; p++) {
            gObject = ja.get(p).getAsJsonObject();
            String vocabulary = v("Vocabulary");
            String korean = v("Korean");
            String britannica = v("Britannica");
            String seq = v("Seq");
            System.out.println(String.format("[%s] %s", seq, vocabulary));
            System.out.println(String.format(" - %s", korean));
            System.out.println(String.format(" - %s", britannica));

            System.out.println("");
        }
    }

    public static String v(String key) {
        return gObject.get(key).getAsString();
    }
}
