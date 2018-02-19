import retrofit2.Call;
import retrofit2.Response;

import java.util.Calendar;

public class SanuiTest {

    public static SanuiApi api = SanuiApi.getInstance();

    public static void main(String[] args) {
        testInstance();
    }

    private static void testInstance() {
        try {
//            System.out.println("START");
            Call<String> call;
            Response<String> response;
            while (true) {
                try {
//                    call = getCall("472"); // 월 큐브 2:20~3:40
//                    response = call.execute();
//                    System.out.println(response.body());
                    Calendar cal = Calendar.getInstance();

                    if (cal.get(Calendar.HOUR_OF_DAY) > 18 && cal.get(Calendar.MINUTE) > 58) {
                        call = getCall("462"); //수 성우교실 7시
                        response = call.execute();
                        System.out.println(response.body());
                    } else {
                        System.out.println("7시 이전 입니다");
                    }

                    if (cal.get(Calendar.HOUR_OF_DAY) > 20 && cal.get(Calendar.MINUTE) > 58) {
                        call = getCall("510"); // 화 바둑 9
                        response = call.execute();
                        System.out.println(response.body());

                        //                    call = getCall("494"); //목 창의미술
                        //                    response = call.execute();
                        //                    System.out.println(response.body());

                        call = getCall("480"); //목 음악줄넘기 9시
                        response = call.execute();
                        System.out.println(response.body());

                        call = getCall("482"); //금 농구 3:00시 9
                        response = call.execute();
                        System.out.println(response.body());
                    } else {
                        System.out.println("9시 이전 입니다");
                    }



                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Call<String> getCall(String uno) {
        Call<String> call = api.getService().postSubmit("add", "", uno, "", "김진아", "성지율", "2", "8", "8", "010-4017-9992", "신청");

        return call;
    }
}
