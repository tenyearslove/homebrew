import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckLoop {
    public static void main(String[] args) throws Exception {
        String URL = "https://www.memrise.com/course/1584772/big-voca/%d";

        for (int i = 1; i <= 80; i++) {
            try {
                Document doc = Jsoup.connect(String.format(URL, i)).get();


                Elements words = doc.getElementsByClass("things clearfix");
                for (Element word : words) {
                    String english = word.getElementsByClass("text").get(0).val();
                    String korean = word.getElementsByClass("text").get(0).val();

                    System.out.println(english + "|" + korean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
