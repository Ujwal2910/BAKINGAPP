package app.com.example.songoku.baking_app;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by songoku on 17/11/17.
 */

public class RetrofitBuilder {

    public static final String BASE_URL="https://d17h27t6h515a5.cloudfront.net/";

    private static OkHttpClient.Builder sHttpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder sBuilder =new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(sHttpClient.build());

    public static <T> T createService(Class<T> serviceClass) {
        return sBuilder.build().create(serviceClass);
    }
}
