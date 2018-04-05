import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class CheckLoop {
    public static void main(String[] args) throws Exception {
        String URL = "http://m.campingkorea.or.kr/pages/booking.htm?res_For=1&res_Day=2018-05-05&home=ms&mode=step02";
        String URL2 = "http://m.campingkorea.or.kr/pages/booking.htm?res_For=1&res_Day=2018-05-20&home=ms&mode=step02";

        while (true) {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            try {
                Document doc = Jsoup.connect(URL)
                        .cookie("PHPSESSID", "5531299215f46af91070219dd0711013")
                        .get();


                Elements radio = doc.getElementsByAttributeValue("type", "radio");
                Iterator<Element> radioIter = radio.iterator();
                while (radioIter.hasNext()) {
                    Element room = radioIter.next();
                    String roomName = room.attr("id");
                    Attributes attr = room.attributes();
                    boolean isAvailable = !attr.hasKey("disabled");

                    if (roomName.equals("cabin_a") || roomName.equals("cabin_b") || roomName.equals("cortes_a")) {
                        System.out.println("5/5 " + roomName + " " + isAvailable);
                        if (isAvailable) {
                            FirebaseAdminSdk.request();
//                            return;


                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            try {
//                Document doc = Jsoup.connect(URL2)
//                        .cookie("PHPSESSID", "5531299215f46af91070219dd0711013")
//                        .get();
//
//
//                Elements radio = doc.getElementsByAttributeValue("type", "radio");
//                Iterator<Element> radioIter = radio.iterator();
//                while (radioIter.hasNext()) {
//                    Element room = radioIter.next();
//                    String roomName = room.attr("id");
//                    Attributes attr = room.attributes();
//                    boolean isAvailable = !attr.hasKey("disabled");
//
//                    if (roomName.equals("cabin_a") || roomName.equals("cabin_b") || roomName.equals("cortes_a")) {
//                        System.out.println("5/20 " + roomName + " " + isAvailable);
//                        if (isAvailable) {
//                            FirebaseAdminSdk.request();
//                            return;
//
//
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            System.out.println("=======");

            Thread.sleep(3000);
        }
    }
}
