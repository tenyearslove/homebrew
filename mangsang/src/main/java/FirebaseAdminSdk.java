import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.firebase.FirebaseApp;
import okhttp3.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class FirebaseAdminSdk {
    private static final String BASE_URL = "https://fcm.googleapis.com/v1/projects/firebase-homebrew/messages:send";
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    /** OAuth 2.0 scopes. */
    private static final List<String> SCOPES = Arrays.asList(
            "https://www.googleapis.com/auth/firebase.messaging",
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/userinfo.email");

    public static void init() {
        try {
//            FileInputStream serviceAccount =
//                    new FileInputStream("src/firebase-homebrew-firebase-adminsdk-5cu7l-2e77e0753f.json");

//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .setDatabaseUrl("https://fir-homebrew.firebaseio.com")
//                    .build();
//
//            FirebaseApp.initializeApp(options);
            FirebaseApp.initializeApp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getAccessToken() throws IOException {
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(new FileInputStream("src/firebase-homebrew-firebase-adminsdk-5cu7l-2e77e0753f.json"))
                .createScoped(SCOPES);
        googleCredential.refreshToken();
        String accessToken = googleCredential.getAccessToken();

//        System.out.println(accessToken);
        return accessToken;
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static String a =
            "{\n" +
            "  \"message\":{\n" +
            "    \"token\" : \"c6e0zU3eX2M:APA91bH_BKYCrFyfM92OgP_YOTt56Kk5Vy_X8hxFeGL6M7bj84RE-Hf25hZtoE2QCx2vLnQBRFPfM6IV5lNIVUFiuONALfGMByT1HAOlbTucGQwrApwhm1_G8y_Z9_wP5gq5U4VaC4JR\",\n" +
            "    \"notification\" : {\n" +
            "      \"body\" : \"go campingkorea.or.kr right now!\",\n" +
            "      \"title\" : \"FCM Message\",\n" +
            "      }\n" +
            "   }\n" +
            "}\n";

    private static String b =
            "{\n" +
            "  \"message\":{\n" +
            "    \"topic\" : \"news\",\n" +
            "    \"notification\" : {\n" +
            "      \"body\" : \"This is a Firebase Cloud Messaging Topic Message!\",\n" +
            "      \"title\" : \"FCM Message\",\n" +
            "      }\n" +
            "   }\n" +
            "}";

    public static void request() throws Exception {
        RequestBody body = RequestBody.create(JSON, b);

        System.out.println(b);

        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("Content-type", "application/json")
                .addHeader("Authorization", "Bearer " + getAccessToken())
                .method("POST", body)
                .build();

        Call call = okHttpClient.newCall(request);
        Response response = call.execute();

        System.out.println(response.body().string());

    }

    public static void main(String args[]) throws Exception {
        try {
//            getAccessToken();
            request();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
