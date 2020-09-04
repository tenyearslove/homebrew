package com.hoyalias.homebrew.mungyung.api;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;

import static java.lang.System.exit;

public class RegTest {

    public static RegApi api;
    public static boolean loop = false;
    public static String log = null;


    private static StringBuffer sb = null;

    public static void main(String[] args) {
        loop = true;
        testInstance(null, RegApi.USERID, RegApi.PASSWD, true);
    }

    public static void testInstance(RegTestWatcher watcher, String userid, String passwd, boolean autoRelogin) {
        try {
            api = RegApi.getInstance();

            doLogin(userid, passwd);

            Call<String> call;
            Response<String> response;

            System.out.println("START");

            // 222 북두칠성
            // 223 남두육성

            String userName = RegApi.USERNAME;
            String tel = RegApi.TEL;

            //        String userName = "성시원";
            //        String tel = "010-4013-9992";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            Date willDate = sdf.parse("20200901 12:00:00");

            int loopCount = 0;
            while (loop) {
                sb = new StringBuffer();
                sb.append(api.cookie + "\n");
                sb.append(loopCount++ + "\n");

//                newExecute("219", "2020-10-03", "2020-10-04", "6", userName, tel);
//
                newExecute("223", "2020-10-01", "2020-10-02", "15", userName, tel);
                newExecute("223", "2020-10-02", "2020-10-03", "15", userName, tel);
//
//                newExecute("222", "2020-10-01", "2020-10-02", "15", userName, tel);
//                newExecute("222", "2020-10-02", "2020-10-03", "15", userName, tel);

//                Date now = new Date();
//                if (now.after(willDate)) {
//                    newExecute("219", "2020-10-03", "2020-10-04", "6", userName, tel);
//
//                    newExecute("223", "2020-10-01", "2020-10-02", "15", userName, tel);
//                    newExecute("223", "2020-10-02", "2020-10-03", "15", userName, tel);
//
//                    newExecute("222", "2020-10-01", "2020-10-02", "15", userName, tel);
//                    newExecute("222", "2020-10-02", "2020-10-03", "15", userName, tel);
//                } else {
//                    System.out.println("Please wait.. " + sdf.format(now) + " " + sdf.format(willDate));
//                }

                log = sb.toString();
                if (watcher != null) {
                    watcher.onLog(log);
                }
                sb = null;

                if (autoRelogin && loopCount > 300) {
                    boolean success = doLogin(userid, passwd);
                    if (success)
                        loopCount = 0;
                    else
                        loopCount -= 10;
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void newExecute(final String room, final String startDate, String endDate, String persons, String userName, String tel) {
        try {
            try {
                System.out.println("########## new Execute : " + room + " " + startDate);
                Call<String> callCampingForm = campingForm(startDate, room);
                Call<String> callRegisterYuga = registerYuga(startDate, endDate, room, persons, userName, tel);
//                Response response0 = callCampingForm.execute();
//                String body0 = response0.body().toString();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String campingAgree = format.format(new Date());
                campingAgree = campingAgree.replaceAll(" ", "T");
                campingAgree = campingAgree.replaceAll(":", "S");
//                api.setCookie2("campingAgree=" + campingAgree);
                api.setCookie3("campingAgree", campingAgree);

                Response response = callRegisterYuga.execute();
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

    public static Call<String> campingForm(String startDate, String room) {
        String[] start = startDate.split("-");
        Call<String> callYuga = api.getService().campingForm(
                start[0],
                String.format("%d", Integer.parseInt(start[1])),
                String.format("%d", Integer.parseInt(start[2])),
                "363",
                room,
                "1"
        );

        return callYuga;
    }

    public static Call<String> registerYuga(String startDate, String endDate, String room, String personMax, String userName, String tel) {
        String[] telNum = tel.split("-");
        String[] start = startDate.split("-");
        Call<String> callYuga = api.getService().postRegisterYuga(
                start[0],
                String.format("%d", Integer.parseInt(start[1])),
                String.format("%d", Integer.parseInt(start[2])),
                room,
                startDate,
                endDate,
                "1",
                personMax,
                userName,
                userName,
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

    public static boolean doLogin(String user_id, String passwd) {
        try {
            OkHttpClient client = new OkHttpClient.Builder().build();

            System.out.println("DO LOGIN : " + user_id + " " + passwd);
            okhttp3.Request request1 = new okhttp3.Request.Builder()
                    .url("http://www.mgtpcr.or.kr/web/login.do?menuIdx=183")
                    .build();
            okhttp3.Call call1 = client.newCall(request1);
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
            okhttp3.Call call2 = client.newCall(request2);
            okhttp3.Response response2 = call2.execute();
            String body = response2.body().string();
            boolean correct = body.contains("로그인 시도 실패");
            if (correct == false) {
                System.out.println("DO LOGIN : " + cookie);
                api.setCookie(cookie);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public interface RegTestWatcher {
        public void onLog(String log);
    }
}
