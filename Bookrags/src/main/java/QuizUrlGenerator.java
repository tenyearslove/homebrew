import java.io.File;
import java.util.Arrays;

public class QuizUrlGenerator {
    static String PATH = "/home/siwon.sung/Phytoncide/bookrags";
    static String BASE_URL = "http://www.bookrags.com/studyguide-%s/free-quiz.html";
    public static void main(String[] args) {
        File path = new File(PATH);
        File[] fileList = path.listFiles();
        Arrays.sort(fileList);
        for (File file : fileList) {
            if (file.isDirectory() == false && file.getName().contains("-lessonplan")) {
                String title = file.getName().replaceAll("-lessonplan\\.pdf", "");
                System.out.println(String.format("<li><a href='" + BASE_URL + "'>" + title + "</a></li>", title));
            }
        }
    }
}
