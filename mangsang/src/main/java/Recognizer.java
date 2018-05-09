import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class Recognizer {
    public static void main(String[] args) {
        doOcr("sample.jpg");
    }

    public static String doOcr(String fileName) {
        File imageFile = new File(fileName);
        ITesseract instance = new Tesseract();

        String result = null;
        try {
            result = instance.doOCR(imageFile).trim();
            System.out.println(result);
//            imageFile.delete();
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        return result;
    }
}
