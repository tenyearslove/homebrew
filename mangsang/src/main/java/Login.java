import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Login {
    public static final String LOGIN_URL = "http://m.campingkorea.or.kr/bbs/member_login_ok.htm";

    public static void main(String args[]) {
        doLogin();
    }

    public static String[] doLogin() {
        String[] session = null;
        try {
            String[] credential = loadCredential();
            Connection connection = Jsoup
                    .connect(LOGIN_URL)
                    .method(Connection.Method.POST)
                    .data("login", "1")
                    .data("userid", credential[0])
                    .data("passwd", credential[1]);
            Connection.Response response = connection.execute();
            response.charset("EUC-KR");
            String cookie = response.header("Set-Cookie");
            String[] splitedCookie = cookie.split(";");
            session = splitedCookie[0].split("=");
            System.out.println(session[0] + "=" + session[1]);
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

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
}
