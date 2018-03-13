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

            // 222 북두칠성
            // 333 남두육성

            while (true) {
                Call<String> callYugaPre = null;
                Call<String> callYuga = null;
                String regDate = null;
                String room = null;

                //북두칠성
                room = "222";

                regDate = "2018-05-05";
                callYugaPre = getYugaPreCall(regDate, room);
                callYuga = getYugaCall(regDate, regDate, room);
                callYugaPre.execute();
                callYuga.execute();

                regDate = "2018-05-06";
                callYugaPre = getYugaPreCall(regDate, room);
                callYuga = getYugaCall(regDate, regDate, room);
                callYugaPre.execute();
                callYuga.execute();

                regDate = "2018-05-07";
                callYugaPre = getYugaPreCall(regDate, room);
                callYuga = getYugaCall(regDate, regDate, room);
                callYugaPre.execute();
                callYuga.execute();

                //////
                regDate = "2018-05-19";
                callYugaPre = getYugaPreCall(regDate, room);
                callYuga = getYugaCall(regDate, regDate, room);
                callYugaPre.execute();
                callYuga.execute();

                regDate = "2018-05-20";
                callYugaPre = getYugaPreCall(regDate, room);
                callYuga = getYugaCall(regDate, regDate, room);
                callYugaPre.execute();
                callYuga.execute();

                regDate = "2018-05-21";
                callYugaPre = getYugaPreCall(regDate, room);
                callYuga = getYugaCall(regDate, regDate, room);
                callYugaPre.execute();
                callYuga.execute();

                //남두육성========================================
                room = "223";

                regDate = "2018-05-05";
                callYugaPre = getYugaPreCall(regDate, room);
                callYuga = getYugaCall(regDate, regDate, room);
                callYugaPre.execute();
                callYuga.execute();

                regDate = "2018-05-06";
                callYugaPre = getYugaPreCall(regDate, room);
                callYuga = getYugaCall(regDate, regDate, room);
                callYugaPre.execute();
                callYuga.execute();

                regDate = "2018-05-07";
                callYugaPre = getYugaPreCall(regDate, room);
                callYuga = getYugaCall(regDate, regDate, room);
                callYugaPre.execute();
                callYuga.execute();

                //////
                regDate = "2018-05-19";
                callYugaPre = getYugaPreCall(regDate, room);
                callYuga = getYugaCall(regDate, regDate, room);
                callYugaPre.execute();
                callYuga.execute();

                regDate = "2018-05-20";
                callYugaPre = getYugaPreCall(regDate, room);
                callYuga = getYugaCall(regDate, regDate, room);
                callYugaPre.execute();
                callYuga.execute();

                regDate = "2018-05-21";
                callYugaPre = getYugaPreCall(regDate, room);
                callYuga = getYugaCall(regDate, regDate, room);
                callYugaPre.execute();
                callYuga.execute();

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
