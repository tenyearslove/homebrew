import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;

public class CheckLoop {
    private static final int ROW = 6;
    private static final int COL = 3;
    private static final int BASE = ROW * COL;
    private static final int WPP = 100;
    public static void main(String[] args) throws Exception {
        String URL = "https://www.memrise.com/course/1584772/big-voca/%d";

        String[] englishBuffer = new String[BASE];
        String[] koreanBuffer = new String[BASE];

        int NUMBER = 1;
        for (int i = 0 ; i < 1; i++) {
            try {
                int set = i + 1;
                FileWriter fileWriter = new FileWriter(String.format("Form_BigVoca_All.txt", set));
                fileWriter.write("Number|word\n");
                Document doc = Jsoup.connect(String.format(URL, set)).get();
                Elements words = doc.getElementsByClass("thing text-text");
                for (int j = 0 ; j < words.size() ; j++) {
                    if (j != 0 && j%BASE == 0) {
                        plushWords(fileWriter, englishBuffer, koreanBuffer, (j/BASE)-1);
                    }
                    Element word = words.get(j);
                    Element englishElement = word.getElementsByClass("text").get(1);
                    Element koreanElement = word.getElementsByClass("text").get(3);

                    String english = englishElement.text().trim();
                    String korean = koreanElement.text().trim();

                    englishBuffer[j%BASE] = english;
                    koreanBuffer[j%BASE] = korean;

//                    String tableRow = j+1 + "|" + english + "|" + korean;
//                    System.out.println(tableRow);
//                    fileWriter.write(tableRow + "\n");
                }
                plushWords(fileWriter, englishBuffer, koreanBuffer, 0);
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void plushWords(FileWriter fileWriter, String[] englishBuffer, String[] koreanBuffer, int page) throws Exception {
        for (int i = 0 ; i < BASE ; i++) {
            if (englishBuffer[i] != null && englishBuffer[i].length() > 0) {
                fileWriter.write("" + (page*BASE + i+1) + "|" + englishBuffer[i] + "\n");
                englishBuffer[i] = "";
            }
        }

        for (int i = 0 ; i < ROW ; i++) {
            for (int j = COL-1 ; j >= 0 ; j--) {
                int index = i*COL + j;
                if (koreanBuffer[index] != null && koreanBuffer[index].length() > 0) {
                    fileWriter.write("" + (page*BASE + index+1) + "|" + koreanBuffer[index] + "\n");
                    koreanBuffer[index] = "";
                }
            }
        }
    }
}
