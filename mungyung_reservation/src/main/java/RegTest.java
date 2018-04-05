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

        String userName = "이정현";
        String tel = "010-6528-9849";

//        String userName = "성시원";
//        String tel = "010-4013-9992";
        while (true) {
            execute("222", "2018-05-05", "280000", "15", userName, tel);
            execute("222", "2018-05-06", "280000", "15", userName, tel);

            execute("223", "2018-05-05", "280000", "15", userName, tel);
            execute("223", "2018-05-06", "280000", "15", userName, tel);

            execute("204", "2018-05-05", "220000", "14", userName, tel);
            execute("204", "2018-05-06", "220000", "14", userName, tel);
        }
    }

    public static void execute(final String room, final String regDate, final String price, String personMax, String userName, String tel) {
        try {
//            new Thread() {
//                public void run() {
                    //북두칠성
                    try {
                        Call<String> callYugaPre = getYugaPreCall(regDate, room, price);
                        Call<String> callYuga = getYugaCall(regDate, regDate, room, price, personMax, userName, tel);
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

    public static Call<String> getYugaCall(String startDate, String endDate, String room, String price, String personMax, String userName, String tel) {
        String[] telNum = tel.split("-");
        Call<String> callYuga = api.getService().postRegervationYuga(
                startDate,
                "1",
                "",
                room, //222, 223
                "0",
                price,
                "1",
                price,
                startDate, //"2016-10-29",
                endDate, //"2016-10-30",
                personMax,
                personMax,
                userName,
                userName,
                "",
                "",
                "",
                "",
                "",
                "",
                telNum[0],
                telNum[1],
                telNum[2],
                "",
                ""
        );

        return callYuga;
    }


    public static Call<String> getYugaPreCall(String startDate, String room, String price) {
        Call<String> callYuga = api.getService().postRegervationYugaPre(
                startDate,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "[{\"baseprice\":\"" + price + "\",\"priceNum\":\"0\",\"useDay\":\"1\",\"useFacility\":\"" + room + "\",\"totPrice\":\"" + price + "\",\"totPriceDesc\":\"\"}]"
        );

        return callYuga;
    }
}
