import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class Step2 {

    static JsonObject gObject = null;

//    public static void main(String[] args) {
//        try {
//            String qzJson = new Scanner(new File("step2.json")).useDelimiter("\\Z").next();
//            getStep(qzJson);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void getPractice(String studyId, String studentHistoryId) {
        try {
            String BODY_STEP2P = GetQuiz.getRequestBodyJson("2P");
            String qzJson = GetQuiz.getData(GetQuiz.DATA_URL, BODY_STEP2P);

            JsonObject root = new JsonParser().parse(qzJson).getAsJsonObject();
            JsonPrimitive d = (JsonPrimitive) root.get("d");
            JsonArray ja = new JsonParser().parse(d.getAsString()).getAsJsonArray();
            int size = ja.size();
            for (int p = 0; p < size; p++) {
                gObject = ja.get(p).getAsJsonObject();
                for (int i = 1; i <= 2; i++) {
                    String quizId = v("QuizId");
                    String quizNo = v("QuizNo");
                    String vocabulary = v("Vocabulary");

                    String requestJson = GetQuiz.getSaveTestResultJson(studyId, studentHistoryId, "2P", quizId, quizNo, p + 1, "", vocabulary, "" + i, false, false);
                    String response = GetQuiz.getData(GetQuiz.SAVE_RESULT_URL, requestJson);
//                    System.out.println("###" + response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getStep(String studyId, String studentHistoryId) {
        getPractice(studyId, studentHistoryId);

        try {
            String BODY_STEP2 = GetQuiz.getRequestBodyJson("2");
            String qzJson = GetQuiz.getData(GetQuiz.DATA_URL, BODY_STEP2);
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

                String quizId = v("QuizId");
                String quizNo = v("QuizNo");

                String requestJson = GetQuiz.getSaveTestResultJson(studyId, studentHistoryId, "2", quizId, quizNo, p + 1, "1", vocabulary, "1", (p == size - 1), false);
                String response = GetQuiz.getData(GetQuiz.SAVE_RESULT_URL, requestJson);
//                System.out.println("###" + response);
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
