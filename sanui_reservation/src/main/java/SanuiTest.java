import retrofit2.Call;
import retrofit2.Response;

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
                    call = getCall("459");
                    response = call.execute();
//                    System.out.println(response.body());
//
//                    call = getCall("398");
//                    response = call.execute();
//                    System.out.println(response.body());
//
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
        Call<String> call = api.getService().postSubmit("add", "", uno, "", "김진아", "성지율", "1", "8", "8", "010-4017-9992", "신청");

        return call;
    }
}
