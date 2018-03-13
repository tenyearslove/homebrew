import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class RegApi {
    public static final String API_URL = "http://www.mgtpcr.or.kr";

    private static RegApi instance = null;
    private HttpBinService service;

    /**
     * HttpBin.org service definition
     */
    public interface HttpBinService {
        @FormUrlEncoded
        @POST("/web/camping/readyreserv_reg.do")
        Call<String> postRegervationYuga(
                @Field("searchDate") String searchDate,
                @Field("facilityLength") String facilityLength,
                @Field("overLapCode") String overLapCode,
                @Field("facilityNum") String facilityNum,
                @Field("priceNum") String priceNum,
                @Field("charge") String charge,
                @Field("days") String days,
                @Field("deposit") String deposit,
                @Field("startDate") String startDate,
                @Field("endDate") String endDate,
                @Field("persons") String persons,
                @Field("personsMax") String personsMax,
                @Field("userName") String userName,
                @Field("account") String account,
                @Field("organization") String organization,
                @Field("eventName") String eventName,
                @Field("sido") String sido,
                @Field("gugun") String gugun,
                @Field("jusoName") String jusoName,
                @Field("usePurpose") String usePurpose,
                @Field("tel1") String tel1,
                @Field("tel2") String tel2,
                @Field("tel3") String tel3,
                @Field("emailid") String emailid,
                @Field("emaildomain") String emaildomain
        );

        @FormUrlEncoded
        @POST("/web/camping/regreservation.do?menuIdx=363")
        Call<String> postRegervationYugaPre(
                @Field("searchDate") String searchDate,
                @Field("facilityLength") String facilityLength,
                @Field("priceR") String priceR,
                @Field("priceNumR") String priceNumR,
                @Field("useDaysR") String useDaysR,
                @Field("useFacilityR") String useFacilityR,
                @Field("totPriceR") String totPriceR,
                @Field("totPriceDescR") String totPriceDescR,
                @Field("useFacilityArray") String useFacilityArray
        );

        @FormUrlEncoded
        @POST("/reserv/forest/readyreserv_reg.do")
        Call<String> postRegervationBuljung(
                @Field("searchDate") String searchDate,
                @Field("facilityLength") String facilityLength,
                @Field("overLapCode") String overLapCode,
                @Field("facilityNum") String facilityNum,
                @Field("priceNum") String priceNum,
                @Field("charge") String charge,
                @Field("days") String days,
                @Field("deposit") String deposit,
                @Field("startDate") String startDate,
                @Field("endDate") String endDate,
                @Field("persons") String persons,
                @Field("personsMax") String personsMax,
                @Field("userName") String userName,
                @Field("organization") String organization,
                @Field("eventName") String eventName,
                @Field("sido") String sido,
                @Field("gugun") String gugun,
                @Field("jusoName") String jusoName,
                @Field("tel1") String tel1,
                @Field("tel2") String tel2,
                @Field("tel3") String tel3,
                @Field("cashreceiptF") String cashreceiptF,
                @Field("cashType") String cashType,
                @Field("etcNum") String etcNum
        );
    }

    /**
     * Private constructor
     */
    private RegApi() {
        HttpLoggingInterceptor loggingLevelinterceptor = new HttpLoggingInterceptor();
        loggingLevelinterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor cookieInterceptor = new Interceptor() {
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Cookie", "JSESSIONID=oPks5cEOFblMazpMT7OxsautQpjSFHHYvSNDg3aRl1Q4i0C8Mc3FRtcUipOjJrS3.TUdIT01FX2RvbWFpbi9ob21lcGFnZQ==; counter_main_main_today=667; counter_main_main_yesterday=58; counter_main_main_total=10434; counter_main_main=TJb30fsa; COBEE_AUTH=C2aced1ac.A2c55723882d180_0");

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

        // Service setup
        service = retrofit.create(HttpBinService.class);
    }

    /**
     * Get the HttpApi singleton instance
     */
    public static RegApi getInstance() {
        if (instance == null) {
            instance = new RegApi();
        }
        return instance;
    }

    /**
     * Get the API service to execute calls with
     */
    public HttpBinService getService() {
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
