import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegTest {

    public static RegApi api;
    public static void main(String[] args) {
        testInstance();
    }

    private static void testInstance() {
        try {
            api = RegApi.getInstance();

            Call<String> call;
            Response<String> response;

            System.out.println("START");

            while (true) {
                Call<String> callYugaPre = getYugaPreCall("2017-10-04");
                Call<String> callYuga = getYugaCall("2017-10-04","2017-10-06", "223");
                System.out.println(new Date());

                callYugaPre.execute();
                callYuga.execute();
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Call<String> getYugaCall(String startDate, String endDate, String room) {
        Call<String> callYuga = api.getService().postRegervationYuga(
                startDate,
                "1",
                "",
                room, //222, 223
                "0",
                "560000",
                "2",
                "560000",
                startDate, //"2016-10-29",
                endDate, //"2016-10-30",
                "15",
                "15",
                "김민주",
                "김민주",
                "",
                "",
                "",
                "",
                "",
                "",
                "010",
                "4514",
                "9849",
                "",
                ""
        );

        return callYuga;
    }



    public static Call<String> getYugaPreCall(String startDate) {
        Call<String> callYuga = api.getService().postRegervationYugaPre(
                startDate,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "[{\"baseprice\":\"280000\",\"priceNum\":\"0\",\"useDay\":\"2\",\"useFacility\":\"223\",\"totPrice\":\"560000\",\"totPriceDesc\":\"\"}]"
        );

        return callYuga;
    }
}
