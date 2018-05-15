import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;
import java.util.Properties;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Reservation {
    public static final String EUC_KR = "EUC-KR";
    public static final String AUTH_NAME = "auth_name.jpg";
    public static final String MAIN_URL = "http://www.campingkorea.or.kr/main/main.htm";
    public static final String LOGIN_URL = "http://www.campingkorea.or.kr/main/main.htm";
    public static final String BOOKING_URL = "http://m.campingkorea.or.kr/pages/booking.htm";

    public static void main(String[] args) {
        doReservation();
    }

    public static void doReservation() {
        try {
            String[] session = doLogin();
            doMain(session);
            do06(session);
            List<Connection.KeyVal> form01Data = do06_01(session);
            List<Connection.KeyVal> form02Data = do06_02(session, form01Data);
            List<Connection.KeyVal> form03Data = do06_03(session, form02Data);
            doReserve(session, form03Data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doReserve(String[] session, List<Connection.KeyVal> formData) {
        try {
//            Thread.sleep(60000);
            removeKeyVal(formData, "car1");
            removeKeyVal(formData, "car2");
            for (Connection.KeyVal keyVal : formData) {
                if (keyVal.key().equals("cartype")) {
                    keyVal.value("1");
                } else if (keyVal.key().equals("auth_name")) {
                    keyVal.value(doAuth(session));
                } else if (keyVal.key().equals("room_Name")) {
                    keyVal.value("캐빈A");
                } else if (keyVal.key().equals("res_Forname")) {
                    keyVal.value("3박4일");
                }
            }

            System.out.println("=== doReserve keyVal ===");
            for (Connection.KeyVal keyVal : formData) {
                System.out.println(keyVal.key() + " " + keyVal.value());
            }

            OkHttpClient client = new OkHttpClient.Builder().build();

            MultipartBody.Builder multiPartBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

            for (Connection.KeyVal keyVal : formData) {
                multiPartBuilder.addFormDataPart(keyVal.key(), keyVal.value());
            }

            RequestBody body = multiPartBuilder.build();


            Request request = new Request.Builder()
                    .url("http://www.campingkorea.or.kr/bbs/dh_res_write_ok.php")
                    .post(body)
                    .addHeader("Accept", "text/html, application/xhtml+xml, */*")
                    .addHeader("Cookie", session[0] + "=" + session[1] + "; POPUP_COOKIE_88=NO; POPUP_COOKIE_96=NO")
                    .addHeader("Referer", "http://www.campingkorea.or.kr/reservation/06_03.htm")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Proxy-Connection", "Keep-Alive")
                    .addHeader("Pragma", "no-cache")
                    .build();

            Response response = client.newCall(request).execute();
//            String responseBody = response.body().string();
            Charset charset = Charset.forName(EUC_KR);
            ByteBuffer byteBuffer = ByteBuffer.wrap(response.body().bytes());

            CharsetDecoder decoder = charset.newDecoder();
            CharBuffer charBuffer = decoder.decode(byteBuffer);

            String responseBody = charBuffer.toString();
            System.out.println("=== OKHttp response body ===");
            System.out.println(responseBody);


//            Connection connection = Jsoup.connect("http://www.campingkorea.or.kr/reservation/06_02.htm")
//                    .cookie(session[0], session[1])
//                    .method(Connection.Method.POST)
////                    .header("content-Type", "multipart/form-data")
//                    .data(formData);
////            addDefaultHeader(connection, BOOKING_URL);
//            Connection.Response response = connection.execute();
//            response.charset(EUC_KR);
//            System.out.println(response.body());
//
//            Document doc = Jsoup.parse(responseBody);//response.parse();
//            FormElement form03 = (FormElement) doc.getElementsByAttributeValue("name", "res_form03").get(0);
//            List<Connection.KeyVal> form03Data = form03.formData();
//
//            for (Connection.KeyVal keyVal : form03Data) {
//                System.out.println(keyVal.key() + " " + keyVal.value());
//            }
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
//            addDefaultHeader(connection, BOOKING_URL);
            connection.header("Accept", "*/s*");
            connection.header("Upgrade-Insecure-Requests", null);
            Connection.Response resultImageResponse = connection.execute();

            FileOutputStream out = (new FileOutputStream(new java.io.File(AUTH_NAME)));
            out.write(resultImageResponse.bodyAsBytes());
            out.close();

            String auth_name = Recognizer.doOcr(AUTH_NAME);
            return auth_name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Connection.KeyVal> do06_03(String[] session, List<Connection.KeyVal> formData) {
        try {
            formData.add(HttpConnection.KeyVal.create("res_Check", "1"));

            System.out.println("=== do06_03 keyVal ===");
            for (Connection.KeyVal keyVal : formData) {
                System.out.println(keyVal.key() + " " + keyVal.value());
            }

            OkHttpClient client = new OkHttpClient.Builder().build();

            MultipartBody.Builder multiPartBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

            for (Connection.KeyVal keyVal : formData) {
                multiPartBuilder.addFormDataPart(keyVal.key(), keyVal.value());
            }

            RequestBody body = multiPartBuilder.build();

            Request request = new Request.Builder()
                    .url("http://www.campingkorea.or.kr/reservation/06_03.htm")
                    .post(body)
                    .addHeader("Cookie", session[0] + "=" + session[1])
                    .addHeader("Referer", " http://www.campingkorea.or.kr/reservation/06_02.htm")
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

//            Charset charset = Charset.forName(EUC_KR);
//            ByteBuffer byteBuffer = ByteBuffer.wrap(responseBody.getBytes());
//
//            CharsetDecoder decoder = charset.newDecoder();
//            CharBuffer charBuffer = decoder.decode(byteBuffer);
//
//            String decodedBody = charBuffer.toString();
//            System.out.println("=== OKHttp response body ===");
//            System.out.println(decodedBody);

            Document doc = Jsoup.parse(responseBody);//response.parse();
            FormElement form03 = (FormElement) doc.getElementsByAttributeValue("name", "res_form03").get(0);
            List<Connection.KeyVal> form03Data = form03.formData();

            for (Connection.KeyVal keyVal : form03Data) {
                System.out.println(keyVal.key() + " " + keyVal.value());
            }

            return form03Data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Connection.KeyVal> do06_02(String[] session, List<Connection.KeyVal> formData) {
        try {
            formData.add(HttpConnection.KeyVal.create("res_For", "3"));
            removeKeyVal(formData, "id");
            removeKeyVal(formData, "Pricexp");
            formData.add(HttpConnection.KeyVal.create("Pricexp", "비수기/주중 240,000"));

            System.out.println("=== do06_02 keyVal ===");
            for (Connection.KeyVal keyVal : formData) {
                System.out.println(keyVal.key() + " " + keyVal.value());
            }

            OkHttpClient client = new OkHttpClient.Builder().build();

            MultipartBody.Builder multiPartBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

            for (Connection.KeyVal keyVal : formData) {
                multiPartBuilder.addFormDataPart(keyVal.key(), keyVal.value());
            }

            RequestBody body = multiPartBuilder.build();

            Request request = new Request.Builder()
                    .url("http://www.campingkorea.or.kr/reservation/06_02.htm")
                    .post(body)
                    .addHeader("Cookie", session[0] + "=" + session[1])
                    .addHeader("Referer", "http://www.campingkorea.or.kr/reservation/06_01.htm?type=cabin_a&today=2018-07-01&col=1")
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

//            Charset charset = Charset.forName(EUC_KR);
//            ByteBuffer byteBuffer = ByteBuffer.wrap(responseBody.getBytes());
//
//            CharsetDecoder decoder = charset.newDecoder();
//            CharBuffer charBuffer = decoder.decode(byteBuffer);
//
//            String decodedBody = charBuffer.toString();
//            System.out.println("=== OKHttp response body ===");
//            System.out.println(decodedBody);

            Document doc = Jsoup.parse(responseBody);//response.parse();
            FormElement form02 = (FormElement) doc.getElementsByAttributeValue("name", "res_form02").get(0);
            List<Connection.KeyVal> form02Data = form02.formData();

            for (Connection.KeyVal keyVal : form02Data) {
                System.out.println(keyVal.key() + " " + keyVal.value());
            }

            return form02Data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Connection.KeyVal> do06_01(String[] session) {
        System.out.println("do06_01");
        try {

            Connection connection = Jsoup.connect("http://www.campingkorea.or.kr/reservation/06_01.htm?type=cabin_a&today=2018-07-01&col=1")
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1]);
//            addDefaultHeader(connection, MAIN_URL);
            connection.header("Referer", "http://www.campingkorea.or.kr/reservation/06.htm?code=&year=2018&month=7");
            Connection.Response response = connection.execute();
            response.charset(EUC_KR);
            System.out.println(response.body());

            doDate(session);

            Document doc = response.parse();
            FormElement form01 = (FormElement) doc.getElementsByAttributeValue("name", "res_form01").get(0);
            List<Connection.KeyVal> form01Data = form01.formData();

            for (Connection.KeyVal keyVal : form01Data) {
                System.out.println(keyVal.key() + " " + keyVal.value());
            }

            return form01Data;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void doDate(String[] session) {
        try {
            Connection connection = Jsoup.connect("http://www.campingkorea.or.kr/reservation/06_01_img.htm?res_Day=2018-07-01&room_Code=cabin_b&site_date=1&click=")
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1]);
//            addDefaultHeader(connection, MAIN_URL);
            Connection.Response response = connection.execute();
            response.charset(EUC_KR);
            System.out.println(response.body());

            Connection connection2 = Jsoup.connect("http://www.campingkorea.or.kr/reservation/06_01_img.htm?res_Day=2018-07-01&room_Code=cabin_b&site_date=3&click=")
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1]);
//            addDefaultHeader(connection, MAIN_URL);
            Connection.Response response2 = connection2.execute();
            response2.charset(EUC_KR);
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void do06(String[] session) {
        try {
            Connection connection = Jsoup.connect("http://www.campingkorea.or.kr/reservation/06.htm")
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1]);
//            addDefaultHeader(connection, MAIN_URL);
            Connection.Response response = connection.execute();
            response.charset(EUC_KR);
            System.out.println(response.body());

            Connection connection2 = Jsoup.connect("http://www.campingkorea.or.kr/reservation/06.htm?code=&year=2018&month=6")
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1]);
//            addDefaultHeader(connection, MAIN_URL);
            Connection.Response response2 = connection2.execute();
            response2.charset(EUC_KR);
            System.out.println(response2.body());

            Connection connection3 = Jsoup.connect("http://www.campingkorea.or.kr/reservation/06.htm?code=&year=2018&month=7")
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1]);
//            addDefaultHeader(connection, MAIN_URL);
            Connection.Response response3 = connection3.execute();
            response3.charset(EUC_KR);
            System.out.println(response3.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void doMain(String[] session) {
        try {
            Connection connection = Jsoup.connect(MAIN_URL)
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
            Connection indexConnection = Jsoup.connect(MAIN_URL)
                    .method(Connection.Method.GET);
//            Reservation.addDefaultHeader(indexConnection, null);
            Connection.Response indexResponse = indexConnection.execute();

            session = processSession(indexResponse);

//            nullDownload("http://m.campingkorea.or.kr/img/mbg.gif", session);
//            nullDownload("http://m.campingkorea.or.kr/img/index_ico02.png", session);
//            nullDownload("http://m.campingkorea.or.kr/img/index_arr.png", session);
//            nullDownload("http://m.campingkorea.or.kr/img/index_ico01.png", session);

//            Thread.sleep(5000);

            Connection loginConnection = Jsoup.connect(LOGIN_URL)
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1]);
//            Reservation.addDefaultHeader(loginConnection, MAIN_URL);
            loginConnection.execute();

//            nullDownload("http://m.campingkorea.or.kr/img/bg_bl_10.png", session);

//            Thread.sleep(5000);

            String[] credential = loadCredential();
            Connection connection = Jsoup
                    .connect("https://www.campingkorea.or.kr/member/login_ck.htm")
                    .method(Connection.Method.POST)
                    .cookie(session[0], session[1])
                    .data("login", "1")
                    .data("login_go", "")
                    .data("userid", credential[0])
                    .data("passwd", credential[1]);
//            Reservation.addDefaultHeader(connection, LOGIN_URL);
            Connection.Response response = connection.execute();

            Connection loginOkConnection = Jsoup
                    .connect("https://www.campingkorea.or.kr/bbs/member_login_ok.php?login=1&login_go=")
                    .method(Connection.Method.GET)
                    .cookie(session[0], session[1]);
            Connection.Response loginOkResponse = loginOkConnection.execute();
            loginOkResponse.charset(EUC_KR);
            System.out.println(loginOkResponse.body());
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

    private static void removeKeyVal(List<Connection.KeyVal> formData, String key) {
        int index = -1;
        for (int i = 0; i < formData.size(); i++) {
            Connection.KeyVal keyVal = formData.get(i);
            if (keyVal.key().equals(key)) {
                index = i;
                break;
            }
        }
        if (index >= 0)
            formData.remove(index);
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
