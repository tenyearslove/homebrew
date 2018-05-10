import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Reservation {
    public static final String EUC_KR = "EUC-KR";
    public static final String AUTH_NAME = "auth_name.jpg";
    public static final String INDEX_URL = "http://m.campingkorea.or.kr/pages/index.htm";
    public static final String LOGIN_URL = "http://m.campingkorea.or.kr/pages/login.htm";
    public static final String BOOKING_URL = "http://m.campingkorea.or.kr/pages/booking.htm";
    public static final String MAIN_URL = "http://m.campingkorea.or.kr/pages/main.htm";

    public static void main(String[] args) {
        doReservation();
    }

    public static void doReservation() {
        try {
            String[] session = doLogin();
            Thread.sleep(5000);
            doIndex(session);
            Thread.sleep(5000);
            doMain(session);
            nullDownload("http://m.campingkorea.or.kr/img/mv_ico01.png", session);
            nullDownload("http://m.campingkorea.or.kr/img/mv_ico02.png", session);
            Thread.sleep(5000);
            doBooking(session);
            Thread.sleep(5000);
            doAgree(session);
            Thread.sleep(5000);
            doStep01(session);
            Thread.sleep(5000);
            doStep02(session);
            Thread.sleep(5000);
            List<Connection.KeyVal> formdata = doStep03(session);
            Thread.sleep(5000);
            doReserve(session, formdata);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doReserve(String[] session, List<Connection.KeyVal> formData) {
        try {
            for (Connection.KeyVal keyVal : formData) {
                String key = keyVal.key();

                if (key.equals("auth_name")) {
                    String auth_name_value = doAuth(session);
                    keyVal.value(auth_name_value);
                }
            }
            formData.add(HttpConnection.KeyVal.create("agreement", "1"));
            Connection connection = Jsoup.connect("http://m.campingkorea.or.kr/bbs/res_write_ok.php")
                    .method(Connection.Method.POST)
                    .cookie(session[0], session[1])
                    .data(formData);
            addDefaultHeader(connection, BOOKING_URL);
            Connection.Response response = connection.execute();
            response.charset(EUC_KR);

            for (Connection.KeyVal keyVal : formData) {
                System.out.println(keyVal.key() + " : " + keyVal.value());
            }


            System.out.println(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String doAuth(String[] session) {
        try {
            Connection connection = Jsoup.connect("http://m.campingkorea.or.kr/bbs/bbs_gdlibrary.inc.php")
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1])
                    .ignoreContentType(true);
            addDefaultHeader(connection, BOOKING_URL);
            connection.header("Accept", "*/*");
            connection.header("Upgrade-Insecure-Requests", null);
            Connection.Response resultImageResponse = connection.execute();

            FileOutputStream out = (new FileOutputStream(new java.io.File(AUTH_NAME)));
            out.write(resultImageResponse.bodyAsBytes());
            out.close();

            String auth_name = Recognizer.doOcr(AUTH_NAME);
            Thread.sleep(10000);
            return auth_name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Connection.KeyVal> doStep03(String[] session) {
        try {
            Connection connection = Jsoup.connect(BOOKING_URL)
                    .method(Connection.Method.POST)
                    .cookie(session[0], session[1])
                    .data("res_Day", "2018-05-31")
                    .data("res_For", "1")
                    .data("home", "ms")
                    .data("mode", "step03")
                    .data("room_Code", "cabin_b");
            addDefaultHeader(connection, BOOKING_URL);
            Connection.Response response = connection.execute();
            response.charset(EUC_KR);
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
            Connection connection = Jsoup.connect(BOOKING_URL)
                    .method(Connection.Method.POST)
                    .cookie(session[0], session[1])
                    .data("res_For", "1")
                    .data("res_Day", "2018-05-31")
                    .data("home", "ms")
                    .data("mode", "step02");
            addDefaultHeader(connection, BOOKING_URL);
            Connection.Response response = connection.execute();
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void doStep01(String[] session) {
        try {
            Connection connection = Jsoup.connect("http://m.campingkorea.or.kr/ajax/booking_step1.htm")
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1])
                    .data("home", "ms")
                    .data("date", "2018-05-31");
            addDefaultHeader(connection, BOOKING_URL);
            connection.header("Accept", "*/*")
                    .header("X-Requested-With", "XMLHttpRequest");
            connection.header("Upgrade-Insecure-Requests", null);
            Connection.Response response = connection.execute();
            response.charset(EUC_KR);
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void doAgree(String[] session) {
        try {
            Connection connection = Jsoup.connect(BOOKING_URL)
                    .cookie(session[0], session[1])
                    .method(Connection.Method.POST)
                    .data("mode", "step1")
                    .data("policy", "ok");
            addDefaultHeader(connection, BOOKING_URL);
            Connection.Response response = connection.execute();
            response.charset(EUC_KR);
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doBooking(String[] session) {
        try {
            Connection connection = Jsoup.connect(BOOKING_URL)
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1]);
            addDefaultHeader(connection, MAIN_URL);
            Connection.Response response = connection.execute();
            response.charset(EUC_KR);
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void doMain(String[] session) {
        try {
            Connection connection = Jsoup.connect(MAIN_URL)
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1]);
            addDefaultHeader(connection, INDEX_URL);
            Connection.Response response = connection.execute();
            response.charset(EUC_KR);
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void doIndex(String[] session) {
        try {
            Connection connection = Jsoup.connect(INDEX_URL)
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1]);
            addDefaultHeader(connection, null);
            Connection.Response response = connection.execute();
            response.charset(EUC_KR);
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String[] doLogin() {
        String[] session = null;
        try {
            Connection indexConnection = Jsoup.connect(INDEX_URL)
                    .method(Connection.Method.GET);
            Reservation.addDefaultHeader(indexConnection, null);
            Connection.Response indexResponse = indexConnection.execute();

            session = processSession(indexResponse);

            nullDownload("http://m.campingkorea.or.kr/img/mbg.gif", session);
            nullDownload("http://m.campingkorea.or.kr/img/index_ico02.png", session);
            nullDownload("http://m.campingkorea.or.kr/img/index_arr.png", session);
            nullDownload("http://m.campingkorea.or.kr/img/index_ico01.png", session);

            Thread.sleep(5000);

            Connection loginConnection = Jsoup.connect(LOGIN_URL)
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1]);
            Reservation.addDefaultHeader(loginConnection, INDEX_URL);
            loginConnection.execute();

            nullDownload("http://m.campingkorea.or.kr/img/bg_bl_10.png", session);

            Thread.sleep(5000);

            String[] credential = loadCredential();
            Connection connection = Jsoup
                    .connect("http://m.campingkorea.or.kr/bbs/member_login_ok.htm")
                    .method(Connection.Method.POST)
                    .cookie(session[0], session[1])
                    .data("login", "1")
                    .data("userid", credential[0])
                    .data("passwd", credential[1]);
            Reservation.addDefaultHeader(connection, LOGIN_URL);
            Connection.Response response = connection.execute();
            
            response.charset(EUC_KR);
            System.out.println(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return session;
    }

    private static String[] processSession(Connection.Response response) {
        String cookie = response.header("Set-Cookie");
        String[] splitedCookie = cookie.split(";");

        String[] session = splitedCookie[0].split("=");
        System.out.println(session[0] + "=" + session[1]);

        return session;
    }

    private static String[] loadCredential() {
        Properties prop = new Properties();
        String[] credential = new String[2];
        try {
            prop.load(new FileInputStream("credential.properties"));
            credential[0] = (String) prop.get("userid");
            credential[1] = (String) prop.get("passwd");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return credential;
    }

    private static Connection addDefaultHeader(Connection connection, String referer) {
        connection
                .header("Host", "m.campingkorea.or.kr")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "keep-alive")
                .header("Upgrade-Insecure-Requests", "1");

        if (referer != null) {
            connection.header("Referer", referer);
        }

        return connection;
    }

    private static void nullDownload(String url, String[] session) {
        try {
            Connection connection = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1])
                    .ignoreContentType(true)
                    .header("Host", "m.campingkorea.or.kr")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0")
                    .header("Accept", "*/*c")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Connection", "keep-alive");
            Connection.Response resultImageResponse = connection.execute();

            FileOutputStream out = (new FileOutputStream(new java.io.File("/dev/null")));
            out.write(resultImageResponse.bodyAsBytes());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
