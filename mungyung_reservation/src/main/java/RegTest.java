import retrofit2.Call;
import retrofit2.Response;

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
                Call<String> callYugaPre = getYugaPreCall("2017-10-04", "223");
                Call<String> callYuga = getYugaCall("2017-10-04", "2017-10-06", "223");
                callYugaPre.execute();
                callYuga.execute();

                Call<String> callYugaPre2 = getYugaPreCall("2017-10-04", "222");
                Call<String> callYuga2 = getYugaCall("2017-10-04", "2017-10-06", "222");
                callYugaPre2.execute();
                callYuga2.execute();

                Thread.sleep(60000);
//                break;
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


    public static Call<String> getYugaPreCall(String startDate, String room) {
        Call<String> callYuga = api.getService().postRegervationYugaPre(
                startDate,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "[{\"baseprice\":\"280000\",\"priceNum\":\"0\",\"useDay\":\"2\",\"useFacility\":\"" + room + "\",\"totPrice\":\"560000\",\"totPriceDesc\":\"\"}]"
        );

        return callYuga;
    }
}
