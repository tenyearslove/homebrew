import com.google.gson.*;

import java.io.File;
import java.util.*;

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
            gObject = getQuizByQuizNo(ja, p+1);
            String quizNo = "" + (p+1);
            String question = v("Question");
            String correctText = v("CorrectText");
            List<String> example = new ArrayList<String>();
            example.add(v("Example1"));
            example.add(v("Example2"));
            example.add(v("Example3"));
            example.add(v("Example4"));
            if (example.get(3) == null) {
                example.remove(3);
            }
            Collections.shuffle(example);

            for (int i = 0 ; i < example.size() ; i++) {
                if (example.get(i) != null && example.get(i).equals(correctText)) {
                    answer[p] = "" + (i + 1);
                }
            }
            GetQuiz.writeOutput(String.format("[%s] %s", quizNo, question));
            for (int i = 0 ; i < example.size() ; i++) {
                if (example.get(i) != null) {
                    GetQuiz.writeOutput(String.format("  %d. %s", (i + 1), example.get(i)));
                }
            }
            GetQuiz.writeOutput("");
        }
        GetQuiz.writeOutput("\n\nStep1 - Answer " + "(" + GetQuiz.title + ")\n");
        for (int p = 0 ; p < size ; p++) {
            GetQuiz.writeOutput(String.format("%s - %s", (p+1), answer[p]));
        }
    }

    public static JsonObject getQuizByQuizNo(JsonArray ja, int x) {
        int size = ja.size();
        for (int i = 0 ; i < size ; i++) {
            JsonObject jo = ja.get(i).getAsJsonObject();
            JsonElement element = jo.get("QuizNo");
            int quizNo = element.getAsInt();
            if (x == quizNo)
                return jo;
        }
        return null;
    }

    public static String v(String key) {
        String retValue = null;
        if (gObject.has(key)) {
            JsonElement element = gObject.get(key);
            if (element.isJsonNull() == false) {
                retValue = element.getAsString();
            }
        }
        if (retValue != null && retValue.equals("null")) {
            retValue = null;
        }
        return retValue;
    }
}
