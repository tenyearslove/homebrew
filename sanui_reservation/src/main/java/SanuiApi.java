import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SanuiApi {
    public static final String API_URL = "http://after.sanui.es.kr";

    private OkHttpClient httpClient = null;
    private SanuiService service;
    private String cookie = null;

    public interface SanuiService {
        @FormUrlEncoded
        @POST("/segio/online/online_apply_edit.php?shell=/index.shell:67")
        Call<String> postSubmit(
                @Field("mode") String mode,
                @Field("no") String no,
                @Field("uno") String uno,
                @Field("user_id") String user_id,
                @Field("u_edit_name") String u_edit_name,
                @Field("t_name") String t_name,
                @Field("t_grade") String t_grade,
                @Field("t_group") String t_group,
                @Field("t_number") String t_number,
                @Field("u_phone") String u_phone,
                @Field("chk_sms") String chk_sms
        );
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public SanuiApi() {
        HttpLoggingInterceptor loggingLevelinterceptor = new HttpLoggingInterceptor();
        loggingLevelinterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        Interceptor cookieInterceptor = new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                if (cookie != null) {
                    builder.addHeader("Cookie", cookie);
                }

                return chain.proceed(builder.build());
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(loggingLevelinterceptor)
                .addInterceptor(cookieInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS);

        httpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        service = retrofit.create(SanuiService.class);
    }

    public SanuiService getService() {
        return service;
    }
}
