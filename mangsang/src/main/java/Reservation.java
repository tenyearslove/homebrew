import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Reservation {
    public static final String AUTH_NAME = "auth_name.jpg";
    static String BOOKING_URL = "http://m.campingkorea.or.kr/pages/booking.htm";

    public static void main(String[] args) {
        doReservation();
    }

    public static void doReservation() {
        String[] session = null;//Login.doLogin();
//        doIndex(session);
//        doMain(session);
//        doBooking(session);
//        doAgree(session);
//        doStep01(session);
//        doStep02(session);
        List<Connection.KeyVal> formdata = doStep03(session);
        doReserve(session, formdata);
    }

    private static void doReserve(String[] session, List<Connection.KeyVal> formdata) {
        try {
            for (Connection.KeyVal keyVal : formdata) {
                String key = keyVal.key();

                if (key.equals("auth_name")) {
                    String auth_name_value = doAuth(session);
                    keyVal.value(auth_name_value);
                }
            }
            formdata.add(HttpConnection.KeyVal.create("agreement", "1"));
            Connection connection = Jsoup.connect("http://m.campingkorea.or.kr/bbs/res_write_ok.php")
                    .method(Connection.Method.POST)
                    .cookie(session[0], session[1])
                    .header("Accept", "text/html, application/xhtml+xml, */*")
                    .header("Referer", "http://m.campingkorea.or.kr/pages/booking.htm")
                    .header("Accept-Language", "ko-KR")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Pragma", "no-cache")
                    .data(formdata);
            for (Connection.KeyVal keyval : formdata) {
                System.out.println(keyval.key() + " : " + keyval.value());
            }
            Connection.Response response = connection.execute();
            response.charset("EUC-KR");
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String doAuth(String[] session) {
        try {
            Connection.Response resultImageResponse = Jsoup.connect("http://m.campingkorea.or.kr/bbs/bbs_gdlibrary.inc.php")
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1])
                    .ignoreContentType(true)
//                    .header("Host", "m.campingkorea.or.kr")
//                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0")
//                    .header("Accept", "*/*")
//                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3")
//                    .header("Accept-Encoding", "gzip, deflate")
//                    .header("Referer", "http://m.campingkorea.or.kr/pages/booking.htm")
//                    .header("Connection", "keep-alive")
                    .execute();

// output here
            FileOutputStream out = (new FileOutputStream(new java.io.File(AUTH_NAME)));
            out.write(resultImageResponse.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
            out.close();

            String auth_name = Recognizer.doOcr(AUTH_NAME);
            return auth_name;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Connection.KeyVal> doStep03(String[] session) {
        try {
            Connection.Response response = Jsoup.connect(BOOKING_URL)
                    .method(Connection.Method.POST)
                    .cookie(session[0], session[1])
                    .data("res_Day", "2018-05-28")
                    .data("res_For", "3")
                    .data("home", "ms")
                    .data("mode", "step03")
                    .data("room_Code", "cabin_a")
                    .execute();
            response.charset("EUC-KR");
            System.out.println(response.body());
            Document doc = response.parse();
            FormElement form03 = (FormElement) doc.getElementById("res_form03");
            List<Connection.KeyVal> formData = form03.formData();
            return formData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void doStep02(String[] session) {
        try {
            Connection.Response response = Jsoup.connect(BOOKING_URL)
                    .method(Connection.Method.POST)
                    .cookie(session[0], session[1])
                    .data("res_For", "3")
                    .data("res_Day", "2018-05-28")
                    .data("home", "ms")
                    .data("mode", "step02")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void doStep01(String[] session) {
        try {
            Connection.Response response = Jsoup.connect("http://m.campingkorea.or.kr/ajax/booking_step1.htm")
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1])
                    .data("home", "ms")
                    .data("date", "2018-05-28")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void doIndex(String[] session) {
        try {
            Connection.Response response = Jsoup.connect("http://m.campingkorea.or.kr/pages/index.htm")
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1])
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void doMain(String[] session) {
        try {
            Connection.Response response = Jsoup.connect("http://m.campingkorea.or.kr/pages/main.htm")
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1])
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void doBooking(String[] session) {
        try {
            Connection.Response response = Jsoup.connect(BOOKING_URL)
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1])
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void doAgree(String[] session) {
        try {
            Connection.Response response = Jsoup.connect(BOOKING_URL)
                    .cookie(session[0], session[1])
                    .method(Connection.Method.POST)
                    .data("mode", "step1")
                    .data("policy", "ok")
                    .execute();
            response.charset("EUC-KR");
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
