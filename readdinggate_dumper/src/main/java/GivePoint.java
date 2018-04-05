import com.google.gson.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GivePoint {

    public static final String DATA_URL = "http://study6.readinggate.com/hp/asmx/wsBrPb.asmx/InsertBookPreference";

    public static void getStep(String pStudyId, String pStudentHistoryId) {
        //{"StudyId":"000077C2018001371","pStudentHistoryId":"000077C2018000136","pPoint":50}
        JsonObject jo = new JsonObject();
        jo.add("pStudyId", new JsonPrimitive(pStudyId));
        jo.add("pStudentHistoryId", new JsonPrimitive(pStudentHistoryId));
        jo.add("pPoint", new JsonPrimitive("50"));

        String response = GetQuiz.getData(DATA_URL, jo.toString());
        System.out.println(response);
    }
}
