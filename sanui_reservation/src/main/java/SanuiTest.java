import retrofit2.Call;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SanuiTest {
    public static final String A_TIME = "2019-02-19 19:00";
    public static final String B_TIME = "2019-02-19 21:00";

    private Parent Hong = new Parent("김홍석", "shar", "ghdi0522!!", "010-5426-1432");
    private Parent Jina = new Parent("김진아", "jjin053", "ghdi0522!!", "010-4017-9992");
    private Parent Siwon = new Parent("성시원", "coolove", "ghdi0522!!", "010-4013-9992");

    private Parent[] parents = new Parent[]{
            Jina, Siwon
    };

    private Student Hansol = new Student("김한솔", "1", "1", "1");
    private Student Julian = new Student("성지율", "3", "3", "1");
    private Student April = new Student("성하연", "1", "1", "1");

    public static void main(String[] args) {
        new SanuiTest().testInstance();
    }

    RunCase runCase1 = new RunCase(Jina) {
        @Override
        protected void runCase() {
            while (true) {
                try {
                    //19시 신청
                    parent.execute("363", April, A_TIME); // 생명과학2(에코 생명) 1~2학년 A반(금 2:10~3:30)

                    //21시 신청
                    parent.execute("474", April, B_TIME); // 방송댄스 1-2학년 A반 (월 2:10-3:30)
                    parent.execute("486", April, B_TIME); // 국악난타 1-2학년 A반 (화 2:10-3:30)
                    parent.execute("484", April, B_TIME); // 리코더&오카리나&단소 1-2학년 A반 (수 1:20~2:40)
                    parent.execute("494", April, B_TIME); // 창의미술 1-2학년 A반 (목 2:10~3:30)

                    parent.execute("558", Julian, B_TIME); // 영어국제반IC13-6학년(주4회,수요일 2T)

                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    RunCase runCase2 = new RunCase(Hong) {
        @Override
        protected void runCase() {
            while (true) {
                try {
                    //19시 신청
                    parent.execute("363", Hansol, A_TIME); // 생명과학2(에코 생명) 1~2학년 A반(금 2:10~3:30)

                    //21시 신청
                    parent.execute("474", Hansol, B_TIME); // 방송댄스 1-2학년 A반 (월 2:10-3:30)
                    parent.execute("486", Hansol, B_TIME); // 국악난타 1-2학년 A반 (화 2:10-3:30)
                    parent.execute("484", Hansol, B_TIME); // 리코더&오카리나&단소 1-2학년 A반 (수 1:20~2:40)
                    parent.execute("494", Hansol, B_TIME); // 창의미술 1-2학년 A반 (목 2:10~3:30)

                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void testInstance() {
        runCase1.run();
        runCase2.run();
    }

    public class Parent {
        public Parent(String name, String id, String passwd, String phone) {
            this.name = name;
            this.id = id;
            this.passwd = passwd;
            this.phone = phone;
            this.sanuiApi = new SanuiApi();
        }

        String name;
        String id;
        String passwd;
        String phone;

        SanuiApi sanuiApi;

        public void execute(String uno, Student student, String openDateString) throws Exception {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date openDate = dateFormat.parse(openDateString);
            Date now = new Date();

            if (now.after(openDate)) {
                LOG(getCall(uno, student).execute().body());
            } else {
                LOG("신청가능 시각은 " + openDateString + " 입니다.");
            }
        }

        private Call<String> getCall(String uno, Student student) {
            Call<String> call = this.sanuiApi.getService().postSubmit("add", "", uno, "", this.name, student.name, student.grade, student.group, student.number, this.phone, "신청");

            return call;
        }

        private boolean doLogin() {
            try {
                sanuiApi.setCookie(null);
                LOG("DO LOGIN : " + this.name + " " + this.id + " " + this.passwd);

                okhttp3.RequestBody formBody = new okhttp3.FormBody.Builder()
                        .add("url", "/")
                        .add("UID", this.id)
                        .add("PASSWD", this.passwd)
                        .add("loginnow.x", "" + (int) (Math.random() * 100 % 70))
                        .add("loginnow.y", "" + (int) (Math.random() * 100 % 70))
                        .build();

                okhttp3.Request request1 = new okhttp3.Request.Builder()
                        .url("http://after.sanui.es.kr/_login")
                        .post(formBody)
                        .build();
                okhttp3.Call call1 = this.sanuiApi.getHttpClient().newCall(request1);
                okhttp3.Response response1 = call1.execute();
                String cookie1 = response1.header("Set-Cookie").split(";")[0];

                okhttp3.Request request2 = new okhttp3.Request.Builder()
                        .url("http://after.sanui.es.kr/segio/online/online_list.php?shell=/index.shell:390")
                        .addHeader("Cookie", cookie1)
                        .build();
                okhttp3.Call call2 = this.sanuiApi.getHttpClient().newCall(request2);
                okhttp3.Response response2 = call2.execute();
                List<String> cookie2 = response2.headers("Set-Cookie");

                String body = response2.body().string();
                boolean correct = body.contains("반갑습니다");
                if (correct == false) {
                    return false;
                }

                String cookie = "";
                for (String slab : cookie2) {
                    if (cookie.isEmpty() == false)
                        cookie += "; ";
                    cookie += slab.split(";")[0];
                }

                LOG("END LOGIN : " + cookie);
                this.sanuiApi.setCookie(cookie);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    public class Student {
        public Student(String name, String grade, String group, String number) {
            this.name = name;
            this.grade = grade;
            this.group = group;
            this.number = number;
        }

        String name;
        String grade;
        String group;
        String number;
    }

    public abstract class RunCase {
        Parent parent;

        public RunCase(Parent parent) {
            this.parent = parent;
        }

        public void run() {
            new Thread(() -> {
                while (parent.doLogin() == false) {
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                runCase();
            }, this.parent.name).start();
        }

        abstract protected void runCase();
    }

    public void LOG(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + message);
    }
}
