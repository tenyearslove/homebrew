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
    public static String StudentHistoryId = "000077C2018000136";

    public static String BOOK_TYPE = "E";

    public static final String INFO_URL = "http://study6.readinggate.com/hp/asmx/wsBrPb.asmx/GetStudyInfo";

    public static final String DATA_URL = "http://study6.readinggate.com/hp/asmx/wsBrPb.asmx/GetQuizData";

    public static String title = "default";

    public static BufferedWriter bw = null;

    public static void main(String args[]) {
        GetSession.getSession(StudyId);
        String BODY_INFO = getRequestBodyJson(null);
        String info = getData(INFO_URL, BODY_INFO);
        try {
            JsonObject jo = new JsonParser().parse(new JsonParser().parse(info).getAsJsonObject().get("d").getAsString()).getAsJsonArray().get(0).getAsJsonObject();
            title = jo.getAsJsonPrimitive("Title").getAsString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            bw = new BufferedWriter(new FileWriter(new File("/home/siwon.sung/Samba/Reading Gate/" + title + ".txt")));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        GetQuiz.writeOutput(title);
//        GetQuiz.writeOutput("");



//        String BODY_STORY = "{\"StudyId\":\"" + StudyId + "\",\"pStudentHistoryId\":\"000077C2017000043\",\"pStep\":\"story\"}";
        String BODY_STEP1 = getRequestBodyJson(1);
        String BODY_STEP2 = getRequestBodyJson(2);
        String BODY_STEP3 = getRequestBodyJson(3);
        String BODY_STEP4 = getRequestBodyJson(4);

        //Story.getStep(getData(DATA_URL, BODY_STORY));

        GivePoint.getStep(StudyId, StudentHistoryId);

        Step1.getStep(StudyId, StudentHistoryId, getData(DATA_URL, BODY_STEP1));
        GetQuiz.writeOutput("\n\n");
        Step2.getStep(getData(DATA_URL, BODY_STEP2));
        GetQuiz.writeOutput("\n\n");
        Step3.getStep(getData(DATA_URL, BODY_STEP3));
        GetQuiz.writeOutput("\n\n");
        Step4.getStep(getData(DATA_URL, BODY_STEP4), title);

        try {
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRequestBodyJson(Integer step) {
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
}
