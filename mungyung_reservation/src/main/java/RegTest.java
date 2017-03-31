import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RegTest {

    public static RegApi api;
    public static void main(String[] args) {
        testInstance();
    }

    private static void testInstance() {
        try {
            api = RegApi.getInstance();

            Callback<ResponseBody> callback= new Callback<ResponseBody>() {
                /**
                 * onResponse is called when any kind of response has been received.
                 */
                public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                    // http response status code + headers
                    System.out.println("Response status code: " + response.code());

                    // isSuccess is true if response code => 200 and <= 300
                    if (!response.isSuccess()) {
                        // print response body if unsuccessful
                        try {
                            System.out.println(response.errorBody().string());
                        } catch (IOException e) {
                            // do nothing
                        }
                        return;
                    }

                    // if parsing the JSON body failed, `response.body()` returns null
                    try {
                        String decodedResponse = response.body().string();

                        if (decodedResponse == null) return;

                        // at this point the JSON body has been successfully parsed
                        System.out.println("Response (contains request infos):");
                        System.out.println(decodedResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                /**
                 * onFailure gets called when the HTTP request didn't get through.
                 * For instance if the URL is invalid / host not reachable
                 */
                public void onFailure(Throwable t) {
                    System.out.println("onFailure");
                    System.out.println(t.getMessage());
                }
            };

            System.out.println("START");

            while (true) {
//                Call<ResponseBody> callYuga1 = getYugaCall("2016-10-29","2016-10-30", "223");
//                Call<ResponseBody> callYuga2 = getYugaCall("2016-10-22","2016-10-23", "223");
//                Call<ResponseBody> callYuga3 = getYugaCall("2016-10-15","2016-10-16", "223");
//
//                Call<ResponseBody> callYuga4 = getYugaCall("2016-10-29","2016-10-30", "222");
//                Call<ResponseBody> callYuga5 = getYugaCall("2016-10-22","2016-10-23", "222");
//                Call<ResponseBody> callYuga6 = getYugaCall("2016-10-15","2016-10-16", "222");

                Call<ResponseBody> callBuljung1 = getBuljungCall("2017-04-05","2017-04-07");
//                Call<ResponseBody> callBuljung2 = getBuljungCall("2017-04-22","2017-04-23");
//                Call<ResponseBody> callBuljung3 = getBuljungCall("2017-04-29","2017-04-30");

                System.out.println(new Date());

//                callYuga1.enqueue(callback);
//                Thread.sleep(1000);
//                callYuga2.enqueue(callback);
//                Thread.sleep(1000);
//                callYuga3.enqueue(callback);
//                Thread.sleep(1000);
//
//                callYuga4.enqueue(callback);
//                Thread.sleep(1000);
//                callYuga5.enqueue(callback);
//                Thread.sleep(1000);
//                callYuga6.enqueue(callback);
//                Thread.sleep(1000);

                callBuljung1.enqueue(callback);
                Thread.sleep(1000);
//                callBuljung2.enqueue(callback);
//                Thread.sleep(1000);
//                callBuljung3.enqueue(callback);
//                Thread.sleep(2000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Call<ResponseBody> getYugaCall(String startDate, String endDate, String room) {
        Call<ResponseBody> callYuga = api.getService().postRegervationYuga(
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                "1",
                "",
                room, //222, 223
                "0",
                "280000",
                "2",
                "280000",
                startDate, //"2016-10-29",
                endDate, //"2016-10-30",
                "15",
                "15",
                "성시원",
                "",
                "",
                "",
                "",
                "",
                "010",
                "4013",
                "9992",
                "Y",
                "Y",
                "010-4013-9992"
        );

        return callYuga;
    }

    private static Call<ResponseBody> getBuljungCall(String startDate, String endDate) {
        Call<ResponseBody> callBuljung = api.getService().postRegervationBuljung(
                startDate,
                "1",
                "",
                "204",
                "0",
                "440000",
                "2",
                "132000.0",
                startDate, //"2016-10-29",
                endDate, //"2016-10-30",
                "14",
                "14",
                "성시원",
                "",
                "",
                "",
                "",
                "",
                "010",
                "4013",
                "9992",
                "Y",
                "Y",
                "010-4013-9992"
        );

        return callBuljung;
    }
}
