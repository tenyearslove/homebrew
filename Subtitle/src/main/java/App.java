import org.fredy.jsrt.api.SRT;
import org.fredy.jsrt.api.SRTInfo;
import org.fredy.jsrt.api.SRTReader;
import org.fredy.jsrt.api.SRTTimeFormat;
import org.fredy.jsrt.api.SRTWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {
    private static final int CHUNK = 1;
    private static String title = null;
    private static String ext = null;
    private static String MOVIE = null;
    private static String RESOLUTION = null;
    private static String ext_regex = "^\\S+.(?i)(mp4|mkv|avi)$";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final long Minus9Hour = -9 * 60 * 60 * 1000;
    private static int audipo_id = 50000;
    private static int shift = 0;

    public static void main(String[] args) {
        App app = new App();

        if (title == null) {
            File dir = new File("Movie");
            File[] fileList = dir.listFiles();
            for (File src : fileList) {
                String srcName = src.getName();
                if (srcName.matches(ext_regex)) {
                    int idx = srcName.lastIndexOf('.');
                    title = srcName.substring(0, idx);
                    ext = srcName.substring(idx);
                    MOVIE = title + ext;
                    RESOLUTION = app.getResoltion(MOVIE);

//                    app.trim2(title);
                    app.chunking(1);
//                    app.chunking(2);
                }
            }
        } else {
//            app.trim2(title);
//            app.chunking(1);
//            app.chunking(2);
        }
    }

    private static final String[] Be_Verb = {"am", "are", "is", "was", "were"};
    private static final String[] Pronoun = {"i", "you", "he", "she", "it", "we", "you", "they", "me", "him", "her", "us", "them", "my", "your", "his", "its", "our", "your", "their", "mine", "yours", "hers", "ours", "yours", "theirs"};
    private static final String[] Relatives = {"who", "when", "where", "why", "which", "how", "that", "whose", "whom"};
    private static final String[] Auxiliary = {"will", "would", "can", "could", "shall", "should"};
    private static final String[] PrePosition = {"in", "at", "on", "for", "to", "up"};
    private static final String[] Interjaction = {"oh", "ah", "wow", "oops", "uh", "hey", "yes", "no"};
    private static final String[] Article = {"a", "an", "the"};

    private static final String[][] Exceptions = {Be_Verb, Pronoun, Relatives, Auxiliary, PrePosition, Interjaction, Article};

    public String makeDictation(String s) {
        String[] splited = s.split(" ");
        int last = -100;
        for (int k = 0; k < splited.length; k++) {
            String token = splited[k];
            if (token.indexOf("'") >= 0) continue;
            if (token.indexOf(",") >= 0) continue;
            if (Character.isUpperCase(token.charAt(0))) continue;
            if (token.startsWith("[")) continue;
            if (token.endsWith("]")) continue;
            if (token.startsWith("<")) continue;
            if (token.endsWith(">")) continue;
            if (token.length() == 1) continue;
            ;

            String lowerToken = token.toLowerCase();

            boolean isExceptionWord = false;
            loops:
            for (String[] wordClass : Exceptions) {
                for (int i = 0; i < wordClass.length; i++) {
                    if (lowerToken.equals(wordClass[i])) {
                        isExceptionWord = true;
                        break loops;
                    }
                }
            }

            if (isExceptionWord == false) {
                splited[k] = replaceWithUnderscore(token.length());
                last = k;
            }
        }

        return String.join(" ", splited);
    }

    public String replaceWithUnderscore(int len) {
        StringBuffer sb = new StringBuffer();
        len = (int) (len * 2);
        while (len-- > 0) {
//            if (sb.length() > 0) sb.append(" ");
            sb.append("_");
        }

        return sb.toString();
    }

    public String getResoltion(String filename) {
        String command = String.format("ffprobe -v error -select_streams v:0 -show_entries stream=width,height -of csv=s=x:p=0 Movie/%s", filename);

        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(command);

            BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                Pattern pat = Pattern.compile("\\d{1}.*x.\\d{1}.*");
                Matcher matcher = pat.matcher(line);
                if (matcher.find()) {
                    String resolution = matcher.group();
                    String[] splited = resolution.split("x");

                    int horizontal = Integer.parseInt(splited[0]);
                    int vertical = Integer.parseInt(splited[1]);

                    int newV = (int)(vertical / (horizontal / 160.0));

                    String retValue = "160x" + newV;
                    System.out.println(resolution + " --> " + retValue);

                    return retValue;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "160x100";
    }

    public void trim() {
        SRTInfo originalInfo = SRTReader.read(new File("Movie/" + title + ".srt"));
        SRTInfo purifiedInfo = purify(originalInfo);
        SRTInfo trimedInfo = new SRTInfo();

        int newSequence = 1;
        int SIZE = purifiedInfo.size();
        for (int i = 1; i <= SIZE; i++) {
            SRT s = purifiedInfo.get(i);
            for (int j = i + 1; j <= SIZE; j++) {
                SRT next = purifiedInfo.get(j);
                if (Character.isLowerCase(next.text.get(0).charAt(0))) {
                    s = merge(s, next);
                    i++;
                } else {
                    break;
                }
            }

            trimedInfo.add(new SRT(newSequence++, s.startTime, s.endTime, s.text));
        }

        SRTWriter.write(new File("Movie" + File.separator + title + ".trim.srt"), trimedInfo);
    }

    public void trim2(String title) {
        SRTInfo originalInfo = SRTReader.read(new File("Movie/" + title + ".srt"));
        SRTInfo purifiedInfo = purify(originalInfo);
        SRTInfo trimedInfo = new SRTInfo();
        int newSequence = 1;
        int SIZE = purifiedInfo.size();

        for (int i = 1; i <= SIZE; i++) {
            SRT s = purifiedInfo.get(i);
            for (int j = i + 1; j <= SIZE; j++) {
                SRT next = purifiedInfo.get(j);
                if (isEnd(s.text.get(0))) {
                    break;
                } else {
                    s = merge(s, next);
                    i++;
                }
            }

            trimedInfo.add(new SRT(newSequence++, s.startTime, s.endTime, s.text));
        }

        SRTWriter.write(new File("Movie" + File.separator + title + ".trim.srt"), trimedInfo);
    }

    private boolean isEnd(String s) {
        return (s.endsWith("...") == false) && (s.endsWith(".") || s.endsWith("?") || s.endsWith("!"));
    }

    private SRTInfo purify(SRTInfo info) {
        SRTInfo newInfo = new SRTInfo();
        int newSequence = 1;
        for (SRT s : info) {
            if (s.text.size() == 0) continue;

            System.out.println("Number: " + s.number);
            System.out.println("Start time: " + SRTTimeFormat.format(s.startTime));
            System.out.println("End time: " + SRTTimeFormat.format(s.endTime));
            System.out.println("Texts:");
            for (String line : s.text) {
                System.out.println("    " + line);
            }
            System.out.println();

            List<String> newList = new ArrayList<>();
            String newString = concat(s.text).replaceAll("<i>|</i>|<p>|</p>|#|<[a-z]*[^>]*>|\\([^\\)]*\\)|^\\.\\.\\.|[A-Z]+:", "").trim();
            newString = newString.replaceAll("[(].[A-Z]+[)].", "");
            newString = newString.replaceAll("\\n", " ");
            if (newString.isEmpty()) continue;
            newList.add(newString);
            newInfo.add(new SRT(newSequence++, s.startTime, s.endTime, newList));
        }

        return newInfo;
    }

    public SRT merge(SRT s1, SRT s2) {
        List<String> newText = new ArrayList<>();
        String concat = "";

        concat += concat(s1.text);
        concat += " " + concat(s2.text);

        newText.add(concat);

        return new SRT(s1.number, s1.startTime, s2.endTime, newText);
    }

    private String concat(List<String> list) {
        String concat = "";
        for (String str : list) {
            if (Character.isUpperCase(str.charAt(0))) {
                if (concat.isEmpty()) {
                    concat += str;
                } else {
                    concat += "\n" + str;
                }
            } else {
                if (concat.isEmpty()) {
                    concat += str;
                } else {
                    concat += " " + str;
                }
            }
        }
        return concat;
    }


    public void chunking(int flag) {
        try {
            String OS = System.getProperty("os.name").toLowerCase();
            String _title = title.replaceAll(" ", "_");
            String outputDirName = String.format("Output/output%d_%s_%s", flag, _title, sdf.format(new Date()));
            File outputDir = new File(outputDirName);
            outputDir.mkdirs();

            if (OS.contains("nux")) {
                System.setErr(new PrintStream(new FileOutputStream(outputDirName + "/run.sh")));
                System.err.println("#!/bin/sh");
            } else {
                System.setErr(new PrintStream(new FileOutputStream(outputDirName + "/run.bat")));
            }
            FileWriter audipoFw = null;
            FileWriter dictationFw = null;
            if (flag == 1) {
                System.setOut(new PrintStream(new FileOutputStream(outputDirName + "/index.html")));
                audipoFw = new FileWriter(outputDirName + "/audipo.txt");
                dictationFw = new FileWriter(outputDirName + "/dictation.html");
                System.out.println("<HTML><HEAD><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"></HEAD><BODY><TABLE cellpadding=\"5\" cellspacing=\"0\" border=\"1\" style=\"border-collapse:collapse; border:1px gray solid;\">");
                dictationFw.write("<HTML><HEAD><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"></HEAD><BODY><TABLE cellpadding=\"5\" cellspacing=\"0\" border=\"1\" style=\"border-collapse:collapse; border:1px gray solid;\">\n");


            }
            SRTInfo info = SRTReader.read(new File("Movie" + File.separator + title + ".trim.srt"));
            StringBuffer sb = new StringBuffer();
            SRTBundle bundle = null;
            for (SRT s : info) {
                if (bundle == null) {
                    bundle = new SRTBundle();
                    bundle.number = s.number;
                    bundle.startTime = s.startTime;
                }

                bundle.text += "<P>";
                for (String x : s.text) {
                    bundle.text += (x + " ");
                }
                bundle.text += "</P>";

                if (s.number % CHUNK == 0) {
                    bundle.endTime = s.endTime;
                    if (flag == 1)
                        ffmpeg(bundle, audipoFw, dictationFw);
                    else
                        ffmpeg2(bundle, outputDirName);
                    bundle = null;
                }
            }
            if (bundle != null) {
                ffmpeg(bundle, null, null);
            }
            if (flag == 1)
                System.err.println(String.format("zip \"%s(HTML).zip\" ./*", _title));
            if (flag == 2) {
                System.err.println(String.format("zip \"%s(분할).zip\" ./*.mp4 ./*.srt", _title));
                System.err.println(String.format("zip \"%s(MP3).zip\" ./*.mp3", _title));
            }

            if (OS.contains("nux")) {
                System.err.println("rm -rf *.srt *.mp4 *.mp3 *.html *.jpg *.html *.txt *.sh");
                System.err.println("mv *.zip /home-mc/siwon.sung/Phytoncide/Movies/New_Movie/0_Processing/");
            }
            audipoFw.close();
            dictationFw.close();

            if (OS.contains("nux")) {
                Runtime rt = Runtime.getRuntime();
                String command = String.format("chmod +x %s/run.sh", outputDir.getAbsolutePath());
                Process pr = rt.exec(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ffmpeg(SRTBundle bundle, FileWriter audipofw, FileWriter dictationFw) {
        try {
            System.out.println("<TR>");
            SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");
            String outputFile = String.format(title.replaceAll(" ", "_") + "_%04d.jpg", bundle.number);
            String command = String.format("ffmpeg -ss %s -i \"../../Movie/%s\" -vframes 1 -s %s %s -y", SDF.format(bundle.startTime), MOVIE, RESOLUTION, outputFile);
//            long diff = (bundle.endTime.getTime() - bundle.startTime.getTime() + 500) / 1000;
//            Date sdate = new Date(bundle.startTime.getTime() - 500);
//            String outputFile = String.format(title + "_%04d", bundle.number);
//            String command = String.format("ffmpeg -ss %s -t %d -i \"%s\" -acodec copy -vcodec copy output2/%s.mkv", SDF.format(sdate), diff, MOVIE, outputFile);

            System.out.println(String.format("<TD align=\"center\">%04d</TD>", bundle.number));
            System.out.println(String.format("<TD><IMG SRC='%s'></TD>", outputFile));
            System.out.println(String.format("<TD>%s</TD>", bundle.text));
            System.err.println(command);
//            Runtime.getRuntime().exec(command);
            System.out.print("</TR>");


            if (dictationFw != null) {
                dictationFw.write(String.format("<TD align=\"center\">%04d</TD>\n", bundle.number));
                dictationFw.write(String.format("<TD>%s</TD>\n", makeDictation(bundle.text)));
                dictationFw.write("</TR>\n");
            }

            int id = audipo_id++;
            long pos = bundle.startTime.getTime() - Minus9Hour;
            String tag = title + "." + bundle.number;
            if (audipofw != null)
                audipofw.write(String.format("{\"id\":%d,\"pos\":%d,\"tag\":\"%s\"},", id, pos + shift, tag));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ffmpeg2(SRTBundle bundle, String outputDirName) {
        try {
            SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss.sss");
            SimpleDateFormat SDF2 = new SimpleDateFormat("ss.sss");
            Date diff = new Date(bundle.endTime.getTime() - bundle.startTime.getTime() + 2500);
            Date sdate = new Date(bundle.startTime.getTime() - 500);
            String outputFile = String.format(title + "_%04d", bundle.number);
            String command = String.format("ffmpeg -i \"../../Movie/%s\" -ss %s -t %s -acodec aac -strict experimental -ac 2 -ab 192k \"%s.mp4\" -y", MOVIE, SDF.format(sdate), SDF2.format(diff), outputFile);
            String command2 = String.format("ffmpeg -i \"%s.mp4\" \"%s.mp3\" -y", MOVIE, outputFile, outputFile);
            System.err.println(command);
            System.err.println(command2);
//            Runtime.getRuntime().exec(command);
            System.out.print("</TR>");

            SRTInfo newInfo = new SRTInfo();
            String text = bundle.text.replaceAll("<P>|</P>", "");
            newInfo.add(new SRT(1, new Date(Minus9Hour), new Date(Minus9Hour + 60 * 1000), text));
            SRTWriter.write(new File(outputDirName + "/" + outputFile + ".srt"), newInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class SRTBundle {
        public int number;
        public Date startTime;
        public Date endTime;
        public String text = "";
    }

//    private List<String> concat(List<String> strings) {
//        String concated = "";
//        for (String s : strings) {
//            concated += removeSpecial(s) + " ";
//        }
//        concated.trim();
//        List<String> list = new ArrayList<String>(1);
//        list.add(concated);
//
//        return list;
//    }

//    private String removeSpecial(SRTInfo info) {
//        for (SRT s : info) {
//            List<String> newList = new ArrayList<>();
//            List<String> stringList = s.text;
//            for (String text : s.text) {
//                newList.add(text.replaceAll("<i>|</i>|<p>|</p>|-|#", "").trim());
//            }
//            s.
//            s.text = newList;
//        }
//    }

    public void sentences(String title) {
        SRTInfo originalInfo = SRTReader.read(new File("Movie/" + title + ".srt"));
        int SIZE = originalInfo.size();

        for (int i = 1; i <= SIZE; i++) {
            SRT s = originalInfo.get(i);
            System.out.println(s.text.get(0));
        }
    }
}
