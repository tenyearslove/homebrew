import com.google.gson.JsonParser;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by John on 2017-03-27.
 */
public class GetSession {
    private static final String BASE_URL = "http://one.readinggate.com";
    private static final String LOGIN_URL = BASE_URL + "/Session/Login ";
    private static final String STUDY_URL = BASE_URL + "/Study/GetStudyStartParam";
    private static final String ASSIGNMENT_URL = BASE_URL + "/Study/Assignment";
    private static final String STUDY6_URL = "http://study6.readinggate.com/hp/index.aspx?info=";

    public static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
    public static final MediaType HTML = MediaType.parse("text/html, application/xhtml+xml");

    public static final String ID_PWD = "id=jullian&password=1251";
    public static final String STUDENT_ID = "000077C2014000113";

    public static void main(String args[]) {
        getSession(null);
    }

    public static String getSession(String specificStudyId) {
        try {
            Request request = new Request.Builder()
                    .url(BASE_URL)
                    .build();
            Response response = HttpClient.client.newCall(request).execute();
            String cookie = response.header("Set-Cookie");

            RequestBody body = RequestBody.create(FORM, ID_PWD);
            request = new Request.Builder()
                    .url(LOGIN_URL)
                    .post(body)
                    .addHeader("Cookie", cookie)
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .build();

            response = HttpClient.client.newCall(request).execute();
            String message = response.body().string();
//            GetQuiz.writeOutput(message);

            String studyId = specificStudyId;
            if (studyId == null) {
                request = new Request.Builder()
                        .url(ASSIGNMENT_URL)
                        .addHeader("Cookie", cookie)
                        .build();
                response = HttpClient.client.newCall(request).execute();
                message = response.body().string();
                //            GetQuiz.writeOutput(message);
                //            GetQuiz.writeOutput(STUDENT_ID);
                Scanner sc = new Scanner(message);
                String line = "";
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    if (line.contains("_" + STUDENT_ID)) break;
                }
                line = line.replaceAll("<div class=\"buttonLinkFree\" id=\"", "");
                line = line.replaceAll("\" style=\"width: 108px; height: 30px; padding-top: 10px;\">", "");
                line = line.replaceAll(" ", "");
                line = line.substring(0, line.indexOf('_'));
                studyId = line;
            }
            GetQuiz.StudyId = studyId;

            body = RequestBody.create(FORM, "studyId=" + studyId + "&studentHistoryId=" + GetQuiz.StudentHistoryId);
            request = new Request.Builder()
                    .url(STUDY_URL)
                    .post(body)
                    .addHeader("Cookie", cookie)
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .build();

            response = HttpClient.client.newCall(request).execute();
            message = response.body().string();
            String data = new JsonParser().parse(message).getAsJsonObject().get("data").getAsJsonPrimitive().getAsString().replaceAll("\"", "");
//            GetQuiz.writeOutput(data);

            request = new Request.Builder()
                    .url(STUDY6_URL + data)
                    .build();
            response = HttpClient.client.newCall(request).execute();
            cookie = response.header("Set-Cookie");
            HttpClient.COOKIE = cookie.split(";")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
