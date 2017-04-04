import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
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

    public static  String STUDY_ID = "000077C2017000959";

    public static String BOOK_TYPE = "E";

    public static final String INFO_URL = "http://study6.readinggate.com/hp/asmx/wsBrEb.asmx/GetStudyInfo";

    public static final String DATA_URL = "http://study6.readinggate.com/hp/asmx/wsBrEb.asmx/GetQuizData";

    public static String title = "default";

    public static BufferedWriter bw = null;

    public static void main(String args[]) {
        GetSession.getSession(STUDY_ID);
        String BODY_INFO = "{\"pStudyId\":\"" + STUDY_ID + "\",\"pStudentHistoryId\":\"000077C2017000043\"}";
        String info = getData(INFO_URL, BODY_INFO);
        try {
            JsonObject jo = new JsonParser().parse(new JsonParser().parse(info).getAsJsonObject().get("d").getAsString()).getAsJsonArray().get(0).getAsJsonObject();
            title = jo.getAsJsonPrimitive("Title").getAsString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            bw = new BufferedWriter(new FileWriter(new File("E:/Reading Gate/" + title + ".txt")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        GetQuiz.writeOutput(title);
        GetQuiz.writeOutput("");


        String BODY_STORY = "{\"pStudyId\":\"" + STUDY_ID + "\",\"pStudentHistoryId\":\"000077C2017000043\",\"pStep\":\"story\"}";
        String BODY_STEP1 = "{\"pStudyId\":\"" + STUDY_ID + "\",\"pStudentHistoryId\":\"000077C2017000043\",\"pStep\":\"1\"}";
        String BODY_STEP2 = "{\"pStudyId\":\"" + STUDY_ID + "\",\"pStudentHistoryId\":\"000077C2017000043\",\"pStep\":\"2\"}";
        String BODY_STEP3 = "{\"pStudyId\":\"" + STUDY_ID + "\",\"pStudentHistoryId\":\"000077C2017000043\",\"pStep\":\"3\"}";
        String BODY_STEP4 = "{\"pStudyId\":\"" + STUDY_ID + "\",\"pStudentHistoryId\":\"000077C2017000043\",\"pStep\":\"4\"}";

        //Story.getStep(getData(DATA_URL, BODY_STORY));

        Step1.getStep(getData(DATA_URL, BODY_STEP1));
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
