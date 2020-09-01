package com.hoyalias.homebrew.mungyung.api;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class RegApi {
    public static final String API_URL = "http://www.mgtpcr.or.kr";

//    public static final String USERID = "coolove";
//    public static final String PASSWD = "ghdi0522!!";
//    public static final String USERNAME = "성시원";
//    public static final String TEL = "010-4013-9992";

//    public static final String USERID = "minjooama";
//    public static final String PASSWD = "minjooama1@";
//    public static final String USERNAME = "이정현";
//    public static final String TEL = "010-6528-9849";

    public static final String USERID = "avocadoj";
    public static final String PASSWD = "avo378717*";
    public static final String USERNAME = "김민정";
    public static final String TEL = "010-2528-5099";

//    private static RegApi instance = null;
    private HttpBinService service;
    private OkHttpClient httpClient = null;
    public String cookie = null;

    public String campingAgree = "";

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

        @GET("/web/campingForm.do")
        Call<String> campingForm(
                @Query("year") String year,
                @Query("month") String month,
                @Query("date") String date,
                @Query("menuIdx") String menuIdx,
                @Query("idx") String idx,
                @Query("useDay") String useDay
        );

        @FormUrlEncoded
        @POST("/web/campingRegister.do")
        Call<String> postRegisterYuga(
                @Field("year") String year,
                @Field("month") String month,
                @Field("date") String date,
                @Field("idx") String idx,
                @Field("startDate") String startDate,
                @Field("endDate") String endDate,
                @Field("useDay") String useDay,
                @Field("persons") String persons,
                @Field("userName") String userName,
                @Field("account") String account,
                @Field("organization") String organization,
                @Field("sido") String sido,
                @Field("gugun") String gugun,
                @Field("jusoName") String jusoName,
                @Field("reason") String reason,
                @Field("tel1") String tel1,
                @Field("tel2") String tel2,
                @Field("tel3") String tel3,
                @Field("emailid") String emailid,
                @Field("emaildomain") String emaildomain
        );
    }

    /**
     * Private constructor
     */
    private RegApi() {
        HttpLoggingInterceptor loggingLevelinterceptor = new HttpLoggingInterceptor();
        loggingLevelinterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        Interceptor cookieInterceptor = new Interceptor() {
            public Response intercept(Interceptor.Chain chain) throws IOException {
                if (RegApi.this.cookie != null) {
                    Request.Builder builder = chain.request().newBuilder();
                    builder.addHeader("Cookie", RegApi.this.cookie);
                    System.out.println("############# Cookie : " + RegApi.this.cookie);
                    return chain.proceed(builder.build());
                } else {
                    return chain.proceed(chain.request());
                }
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(loggingLevelinterceptor)
                .addInterceptor(cookieInterceptor);
//                .connectTimeout(60, TimeUnit.SECONDS);

        httpClient = builder.build();

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
//        if (instance == null) {
//            instance = new RegApi();
//        }
//        return instance;
        return new RegApi();
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

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public void setCookie2(String cookie2) {
        this.cookie += ("; " + cookie2);
    }

    public void setCookie3(String key, String value) {
        String newCookie = "";
        boolean isAdd = false;
        String[] cookies = this.cookie.split("; ");
        if (cookies != null) {
            for (String cookie : cookies) {
                String[] keyValue = cookie.split("=");
                if (keyValue[0].equals(key) == false) {
                    newCookie += (cookie);
                } else {
                    newCookie += (key + "=" + value);
                    isAdd = true;
                }

                newCookie += "; ";
            }

            if (isAdd == false) {
                newCookie += (key + "=" + value + "; ");
            }
        }
        this.cookie = newCookie;
    }
}
