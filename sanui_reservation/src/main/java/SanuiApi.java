import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

public class SanuiApi {
    public static final String API_URL = "http://after.sanui.es.kr";

    private static SanuiApi instance = null;
    private SanuiService service;

    /**
     * HttpBin.org service definition
     */
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

    /**
     * Private constructor
     */
    private SanuiApi() {
        HttpLoggingInterceptor loggingLevelinterceptor = new HttpLoggingInterceptor();
        loggingLevelinterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor cookieInterceptor = new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Cookie", "COBEE_AUTH=C282b835a.A2d21c747013fa0_0; PHPSESSID=865aca6250a12414a7f7e46b19fd8277");

                return chain.proceed(builder.build());
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(loggingLevelinterceptor)
                .addInterceptor(cookieInterceptor);
//                .connectTimeout(60, TimeUnit.SECONDS);

        OkHttpClient httpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(httpClient)
                .addConverterFactory(new StringConverterFactory())
                .build();

        // Service setup
        service = retrofit.create(SanuiService.class);
    }

    /**
     * Get the HttpApi singleton instance
     */
    public static SanuiApi getInstance() {
        if (instance == null) {
            instance = new SanuiApi();
        }
        return instance;
    }

    /**
     * Get the API service to execute calls with
     */
    public SanuiService getService() {
        return service;
    }

    public final class StringConverterFactory extends Converter.Factory {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            //noinspection EqualsBetweenInconvertibleTypes
            if (String.class.equals(type)) {
                return new Converter<ResponseBody, Object>() {
                    public Object convert(ResponseBody responseBody) throws IOException {
                        return responseBody.string();
                    }
                };
            }

            return null;
        }

        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                              Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            if (String.class.equals(type)) {
                return new Converter<String, RequestBody>() {
                    public RequestBody convert(String value) throws IOException {
                        return RequestBody.create(MediaType.parse("text/plain"), value);
                    }
                };
            }
            return null;
        }
    }
}
