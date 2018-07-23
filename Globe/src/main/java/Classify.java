import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Scanner;

public class Classify {
    public static void main(String[] args) {

        doClassify("minions_script.txt");
    }

    public static void doClassify(String fileName) {
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            FileWriter hardFw = new FileWriter("hard.txt");
            FileWriter easyFw = new FileWriter("easy.txt");
            while (scanner.hasNext()) {
                String word = scanner.next();
                String splited[] = word.split("\\|");
//                if (splited.length == 3 && "z".equals(splited[2])) {
                    hardFw.write(splited[0] + "\n");
//                } else {
//                    easyFw.write(splited[0] + "\n");
//                }
            }
            hardFw.close();
            easyFw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
