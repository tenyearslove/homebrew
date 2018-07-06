import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import okhttp3.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by siwon.sung on 2017-03-22.
 */
public class GetQuiz {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String StudyId = null;
    public static String StudentHistoryId = null;

    public static String BOOK_TYPE = "E";

    public static final String INFO_URL = "http://study6.readinggate.com/hp/asmx/wsBrPb.asmx/GetStudyInfo";

    public static final String DATA_URL = "http://study6.readinggate.com/hp/asmx/wsBrPb.asmx/GetQuizData";

    public static final String SAVE_RESULT_URL = "http://study6.readinggate.com/hp/asmx/wsBrPb.asmx/SaveTestResult";

    public static String title = "default";
    public static String ROOT = "/home/siwon.sung/Samba/Reading Gate/";
    public static String HOME = null;

    public static BufferedWriter bw = null;

    public static void byWho(String cridential) {
        GetSession.getSessionJsoup(StudyId, cridential);
        String BODY_INFO = getRequestBodyJson(null);
        String info = getData(INFO_URL, BODY_INFO);
        try {
            JsonObject jo = new JsonParser().parse(new JsonParser().parse(info).getAsJsonObject().get("d").getAsString()).getAsJsonArray().get(0).getAsJsonObject();
            title = jo.getAsJsonPrimitive("Title").getAsString().trim();
            File dir = new File(ROOT + "/" + title);
            dir.mkdir();
            HOME = dir.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            bw = new BufferedWriter(new FileWriter(new File(HOME + "/" + title + ".txt")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GivePoint.getStep(StudyId, StudentHistoryId);
        Step1.getStep(StudyId, StudentHistoryId);
        GetQuiz.writeOutput("\n\n");
        Step2.getStep(StudyId, StudentHistoryId);
        GetQuiz.writeOutput("\n\n");
        Step3.getStep(StudyId, StudentHistoryId);
        GetQuiz.writeOutput("\n\n");
        Step4.getStep(StudyId, StudentHistoryId);

        try {
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRequestBodyJson(String step) {
        JsonObject jo = new JsonObject();
        jo.add("pStudyId", new JsonPrimitive(StudyId));
        jo.add("pStudentHistoryId", new JsonPrimitive(StudentHistoryId));
        if (step != null)
            jo.add("pStep", new JsonPrimitive("" + step));

        return jo.toString();
    }

    public static String getData(String url, String step) {
        RequestBody body = RequestBody.create(JSON, step);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = HttpClient.client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeOutput(String str) {
        System.out.println(str);
        try {
            bw.write(str);
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSaveTestResultJson(
            String study_id,
            String student_history_id,
            String step,
            String quiz_id,
            String quiz_no,
            int current_quiz_no,
            String ox,
            String correct,
            String answer_count,
            boolean isLastQuiz,
            boolean isStudyEnd
    ) {
        JsonObject jo = new JsonObject();
        jo.add("study_id", new JsonPrimitive(study_id));
        jo.add("student_history_id", new JsonPrimitive(student_history_id));
        jo.add("study_type_code", new JsonPrimitive("001001"));
        jo.add("step", new JsonPrimitive(step));
        jo.add("quiz_id", new JsonPrimitive(quiz_id));
        jo.add("quiz_no", new JsonPrimitive(quiz_no));
        jo.add("current_quiz_no", new JsonPrimitive("" + current_quiz_no));
        jo.add("ox", new JsonPrimitive(ox));
        jo.add("temp_text", new JsonPrimitive(""));
        jo.add("penalty_word", new JsonPrimitive(""));
        jo.add("correct", new JsonPrimitive(correct));
        jo.add("student_answer", new JsonPrimitive(correct));
        jo.add("answer_count", new JsonPrimitive(answer_count));
        jo.add("score", new JsonPrimitive(isLastQuiz?"100":"0"));
        jo.add("save_type", new JsonPrimitive(""));
        jo.add("last_quiz_yn", new JsonPrimitive(isLastQuiz?"Y":"N"));
        jo.add("study_end_yn", new JsonPrimitive(isStudyEnd?"Y":"N"));
        jo.add("delete_penalty_yn", new JsonPrimitive("N"));
        jo.add("revision_yn", new JsonPrimitive("N"));


        JsonObject top = new JsonObject();
        top.add("jsonStr", new JsonPrimitive(jo.toString()));

        String retStr = top.toString();
        //System.out.println(retStr);

        return retStr;
    }
}
