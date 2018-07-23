package com.hoyalias.homebrew.mungyung.api;

import retrofit2.Call;
import retrofit2.Response;

import static java.lang.System.exit;

public class RegTest {

    public static RegApi api;
    public static boolean loop = false;
    public static String log = null;


    private static StringBuffer sb = null;

    public static void main(String[] args) {
        testInstance(null, null, null);
    }

    public static void testInstance(RegTestWatcher watcher, String userid, String passwd) {
        api = RegApi.getInstance();

        if (userid == null || userid.length() == 0) {
            userid = "coolove";
            passwd = "ghdi0522!";
        }

        String cookie = doLogin(userid, passwd);
        api.setCookie(cookie);

        Call<String> call;
        Response<String> response;

        System.out.println("START");

        // 222 북두칠성
        // 223 남두육성

        String userName = "김연정";
        String tel = "010-4001-7077";

//        String userName = "성시원";
//        String tel = "010-4013-9992";

        int loopCount = 0;
        while (loop) {
            sb = new StringBuffer();
            sb.append(api.cookie + "\n");
            sb.append(loopCount++ + "\n");

            execute("222", "2018-09-24", "280000", "15", userName, tel);
            execute("222", "2018-09-25", "280000", "15", userName, tel);

            execute("223", "2018-09-24", "280000", "15", userName, tel);
            execute("223", "2018-09-25", "280000", "15", userName, tel);

            log = sb.toString();
            if (watcher != null) {
                watcher.onLog(log);
            }
            sb = null;
        }
    }

    public static void execute(final String room, final String regDate, final String price, String personMax, String userName, String tel) {
        try {
            try {
                Call<String> callYugaPre = getYugaPreCall(regDate, room, price);
                Call<String> callYuga = getYugaCall(regDate, regDate, room, price, personMax, userName, tel);
                callYugaPre.execute();
                Response response = callYuga.execute();
                String body = response.body().toString();

                sb.append(body);
            } catch (Exception e) {
                System.out.println(e.getMessage());
//                        e.printStackTrace();
            }
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

    public static String doLogin(String user_id, String passwd) {
        try {
            okhttp3.Request request1 = new okhttp3.Request.Builder()
                    .url("http://www.mgtpcr.or.kr/web/login.do?menuIdx=183")
                    .build();
            okhttp3.Call call1 = api.getHttpClient().newCall(request1);
            okhttp3.Response response1 = call1.execute();
            String cookie = response1.header("Set-Cookie");

            okhttp3.RequestBody formBody = new okhttp3.FormBody.Builder()
                    .add("user_id", user_id)
                    .add("passwd", passwd)
                    .build();
            okhttp3.Request request2 = new okhttp3.Request.Builder()
                    .url("http://www.mgtpcr.or.kr/web/login.do?menuIdx=183")
                    .header("Cookie", cookie)
                    .post(formBody)
                    .build();
            okhttp3.Call call2 = api.getHttpClient().newCall(request2);
            okhttp3.Response response2 = call2.execute();

            return cookie;

        } catch (Exception e) {
            e.printStackTrace();
        }

        exit(1);
        return null;
    }

    public interface RegTestWatcher {
        public void onLog(String log);
    }
}
