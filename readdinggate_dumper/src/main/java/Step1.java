import com.google.gson.*;

import java.io.File;
import java.util.*;

public class Step1 {

    static JsonObject gObject = null;

    public static void main(String[] args) {
        try {
            String qzJson = new Scanner(new File("step1.json")).useDelimiter("\\Z").next();
//            getStep(qzJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getStep(String studyId, String studentHistoryId, String qzJson) {
        GetQuiz.writeOutput(String.format("%s (%s)", "STEP 1\n", GetQuiz.title));

        JsonObject root = new JsonParser().parse(qzJson).getAsJsonObject();
        JsonPrimitive d = (JsonPrimitive) root.get("d");
        JsonArray ja = new JsonParser().parse(d.getAsString()).getAsJsonArray();
        int size = ja.size();
        String[] answer = new String[size];
        for (int p = 0 ; p < size ; p++) {
            gObject = getQuizByQuizNo(ja, p+1);
//            System.out.println(gObject.toString());
            String quizNumber = "" + (p+1);
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
            GetQuiz.writeOutput(String.format("[%s] %s", quizNumber, question));
            for (int i = 0 ; i < example.size() ; i++) {
                if (example.get(i) != null) {
                    GetQuiz.writeOutput(String.format("  %d. %s", (i + 1), example.get(i)));
                }
            }
            GetQuiz.writeOutput("");

            String quizId = v("QuizId");
            String quizNo = v("QuizNo");

            String requestJson = getSaveTestResultJson(studyId, studentHistoryId, "1", quizId, quizNo);

//            String response = GetQuiz.getData("http://study6.readinggate.com/hp/asmx/wsBrPb.asmx/SaveTestResult", requestJson);
//            System.out.println("###" + response);

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

    public static String getSaveTestResultJson(
            String study_id,
            String student_history_id,
            String step,
            String quiz_id,
            String quiz_no
//            String current_quiz_no,
//            String correct,
//            String student_answer
    ) {
        //jsonStr={ study_id :"000077C2018001371" , student_history_id :"000077C2018000136" , study_type_code :"001001" ,
        // step :"1" , quiz_id :"12372" , quiz_no :"1" , current_quiz_no :"1" , ox :"1" , temp_text :"" , penalty_word :"" ,
        // correct :"2" , student_answer :"2" , answer_count :"1" , score :"0" , save_type :"" , last_quiz_yn :"N" , study_end_yn :"N" ,
        // delete_penalty_yn :"N" , revision_yn :"N" }

        //{"jsonStr":"{ study_id :"000077C2018001371" , student_history_id :"000077C2018000136" , study_type_code :"001001" ,
        // step :"1" , quiz_id :"12375" , quiz_no :"4" , current_quiz_no :"4" , ox :"1" , temp_text :"" , penalty_word :"" ,
        // correct :"4" , student_answer :"4" , answer_count :"1" , score :"0" , save_type :"" , last_quiz_yn :"N" , study_end_yn :"N" ,
        // delete_penalty_yn :"N" , revision_yn :"N" }"}
        
        //{"jsonStr":{"study_id":"000077C2018001371","student_history_id":"000077C2018000136","study_type_code":"000077C2018000136","step":"1","quiz_id":"12391","quiz_no":"20","current_quiz_no":"20","ox":"1","temp_text":"","penalty_word":"","correct":"1","student_answer":"1","answer_count":"1","score":"0","save_type":"","last_quiz_yn":"N","study_end_yn":"N","delete_penalty_yn":"N","revision_yn":"N"}}
        JsonObject jo = new JsonObject();
        jo.add("study_id", new JsonPrimitive(study_id));
        jo.add("student_history_id", new JsonPrimitive(student_history_id));
        jo.add("study_type_code", new JsonPrimitive(student_history_id));
        jo.add("step", new JsonPrimitive(step));
        jo.add("quiz_id", new JsonPrimitive(quiz_id));
        jo.add("quiz_no", new JsonPrimitive(quiz_no));
        jo.add("current_quiz_no", new JsonPrimitive(quiz_no));
        jo.add("ox", new JsonPrimitive("1"));
        jo.add("temp_text", new JsonPrimitive(""));
        jo.add("penalty_word", new JsonPrimitive(""));
        jo.add("correct", new JsonPrimitive("1"));
        jo.add("student_answer", new JsonPrimitive("1"));
        jo.add("answer_count", new JsonPrimitive("1"));
        jo.add("score", new JsonPrimitive("0"));
        jo.add("save_type", new JsonPrimitive(""));
        jo.add("last_quiz_yn", new JsonPrimitive("N"));
        jo.add("study_end_yn", new JsonPrimitive("N"));
        jo.add("delete_penalty_yn", new JsonPrimitive("N"));
        jo.add("revision_yn", new JsonPrimitive("N"));


        JsonObject top = new JsonObject();
        top.add("jsonStr", new JsonPrimitive(jo.toString()));

        String retStr = top.toString();
//        System.out.println(retStr);

        return retStr;
    }
}
