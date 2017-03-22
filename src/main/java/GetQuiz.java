import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
import okhttp3.*;

import java.io.IOException;

/**
 * Created by siwon.sung on 2017-03-22.
 */
public class GetQuiz {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String SESSION_ID = "pr5zohlcqohfpj2hkt3ju4ih";
    public static final String STUDY_ID = "000077C2017000773";

    public static final String INFO_URL = "http://study6.readinggate.com/hp/asmx/wsBrPb.asmx/GetStudyInfo";
    public static final String BODY_INFO = "{\"pStudyId\":\"" + STUDY_ID + "\",\"pStudentHistoryId\":\"000077C2017000043\"}";

    public static final String DATA_URL = "http://study6.readinggate.com/hp/asmx/wsBrPb.asmx/GetQuizData";
    public static final String BODY_STEP1 = "{\"pStudyId\":\"" + STUDY_ID + "\",\"pStudentHistoryId\":\"000077C2017000043\",\"pStep\":\"1\"}";
    public static final String BODY_STEP2 = "{\"pStudyId\":\"" + STUDY_ID + "\",\"pStudentHistoryId\":\"000077C2017000043\",\"pStep\":\"2\"}";
    public static final String BODY_STEP3 = "{\"pStudyId\":\"" + STUDY_ID + "\",\"pStudentHistoryId\":\"000077C2017000043\",\"pStep\":\"3\"}";
    public static final String BODY_STEP4 = "{\"pStudyId\":\"" + STUDY_ID + "\",\"pStudentHistoryId\":\"000077C2017000043\",\"pStep\":\"4\"}";

    public static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                public Response intercept(Chain chain) throws IOException {
                    final Request original = chain.request();

                    final Request authorized = original.newBuilder()
                            .addHeader("Cookie", "ASP.NET_SessionId=" + SESSION_ID)
                            .build();

                    return chain.proceed(authorized);
                }
            })
            .build();

    public static void main(String args[]) {
        String info = getData(INFO_URL, BODY_INFO);
        JsonObject jo = new JsonParser().parse(new JsonParser().parse(info).getAsJsonObject().get("d").getAsString()).getAsJsonArray().get(0).getAsJsonObject();
        String title = jo.getAsJsonPrimitive("Title").getAsString();
        System.out.println(title);
        System.out.println();

        Step1.getStep(getData(DATA_URL, BODY_STEP1));
        System.out.println("\n\n");
        Step2.getStep(getData(DATA_URL, BODY_STEP2));
        System.out.println("\n\n");
        Step3.getStep(getData(DATA_URL, BODY_STEP3));
        System.out.println("\n\n");
        Step4.getStep(getData(DATA_URL, BODY_STEP4), title);
    }

    public static String getData(String url, String step) {
        RequestBody body = RequestBody.create(JSON, step);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
