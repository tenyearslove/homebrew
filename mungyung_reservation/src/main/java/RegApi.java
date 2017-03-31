import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public class RegApi {
    public static final String API_URL = "http://reserv.mgtpcr.or.kr";

    private static RegApi instance = null;
    private HttpBinService service;

    /**
     * HttpBin.org service definition
     */
    public interface HttpBinService {
        @FormUrlEncoded
        @POST("/reserv/camping/readyreserv_reg.do")
        Call<ResponseBody> postRegervationYuga(
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

        @FormUrlEncoded
        @POST("/reserv/forest/readyreserv_reg.do")
        Call<ResponseBody> postRegervationBuljung(
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
        // Http interceptor to add custom headers to every request
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.networkInterceptors().add(new Interceptor() {
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();

                return chain.proceed(builder.build());
            }
        });

        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(httpClient)
                .build();

        // Service setup
        service = retrofit.create(HttpBinService.class);
    }

    /**
     * Get the HttpApi singleton instance
     */
    public static RegApi getInstance() {
        if(instance == null) {
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
}
