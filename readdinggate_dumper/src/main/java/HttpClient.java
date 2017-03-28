import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;

/**
 * Created by John on 2017-03-27.
 */
public class HttpClient {

    public static String COOKIE = null;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);

    public static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                public Response intercept(Chain chain) throws IOException {
                    final Request original = chain.request();

                    if (COOKIE == null)
                        return chain.proceed(original);

                    final Request authorized = original.newBuilder()
                            .addHeader("Cookie", COOKIE)
                            .build();

                    return chain.proceed(authorized);
                }
            })
            .addInterceptor(logging)
            .build();
}
