import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Story {

    static JsonObject gObject = null;

    public static void main(String[] args) {
        try {
            String qzJson = new Scanner(new File("step4.json")).useDelimiter("\\Z").next();
            getStep(qzJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getStep(String qzJson) {
        JsonObject root = new JsonParser().parse(qzJson).getAsJsonObject();
        JsonPrimitive d = (JsonPrimitive) root.get("d");
        JsonArray ja = new JsonParser().parse(d.getAsString()).getAsJsonArray();
        int size = ja.size();
        for (int p = 0 ; p < size ; p++) {
            gObject = ja.get(p).getAsJsonObject();
            String imagePath = v("ImagePath");
            String page = v("Page");
            int pageNumber = Integer.parseInt(page);
            String filename = String.format("%s-%02d.jpg", GetQuiz.title, pageNumber);
            saveUrl(filename, imagePath);
        }
    }

    public static String v(String key) {
        String retValue = null;
        if (gObject.has(key)) {
            retValue = gObject.get(key).getAsString();
        }
        return retValue;
    }

    public static void saveUrl(final String filename, final String urlString) {
        try {
            BufferedInputStream in = null;
            FileOutputStream fout = null;
            try {
                in = new BufferedInputStream(new URL(urlString).openStream());
                fout = new FileOutputStream(filename);

                final byte data[] = new byte[1024];
                int count;
                while ((count = in.read(data, 0, 1024)) != -1) {
                    fout.write(data, 0, count);
                }
            } finally {
                if (in != null) {
                    in.close();
                }
                if (fout != null) {
                    fout.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
