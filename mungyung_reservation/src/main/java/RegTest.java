import retrofit2.Call;
import retrofit2.Response;

public class RegTest {

    public static RegApi api;

    public static void main(String[] args) {
        testInstance();
    }

    private static void testInstance() {
        api = RegApi.getInstance();

        Call<String> call;
        Response<String> response;

        System.out.println("START");

        // 222 북두칠성
        // 223 남두육성

        while (true) {
            execute("222", "2018-05-05");
            execute("222", "2018-05-06");
            execute("222", "2018-05-07");

            execute("223", "2018-05-05");
            execute("223", "2018-05-06");
            execute("223", "2018-05-07");
        }
    }

    public static void execute(final String room, final String regDate) {
        try {
//            new Thread() {
//                public void run() {
                    //북두칠성
                    try {
                        Call<String> callYugaPre = getYugaPreCall(regDate, room);
                        Call<String> callYuga = getYugaCall(regDate, regDate, room);
                        callYugaPre.execute();
                        callYuga.execute();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
//                        e.printStackTrace();
                    }
//                }
//            }.start();
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
                "280000",
                "1",
                "280000",
                startDate, //"2016-10-29",
                endDate, //"2016-10-30",
                "15",
                "15",
                "성시원",
                "성시원",
                "",
                "",
                "",
                "",
                "",
                "",
                "010",
                "4013",
                "9992",
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
                "[{\"baseprice\":\"280000\",\"priceNum\":\"0\",\"useDay\":\"1\",\"useFacility\":\"" + room + "\",\"totPrice\":\"280000\",\"totPriceDesc\":\"\"}]"
        );

        return callYuga;
    }
}
