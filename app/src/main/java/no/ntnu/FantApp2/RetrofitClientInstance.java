package no.ntnu.FantApp2;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    //IP at school wifi
    //private static final String BASE_URL = "http://10.22.179.220:8080/Oblig1/api/";

    //local IP for emulator
    private static final String BASE_URL = "http://10.0.2.2:8080/Oblig1/api/";

    private static Retrofit retrofit;
    private static RetrofitClientInstance SINGLETON;

    private RetrofitClientInstance(){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized RetrofitClientInstance getSINGLETON(){
        if (SINGLETON == null){
            SINGLETON = new RetrofitClientInstance();
        }
        return SINGLETON;
    }


    public FantAPI getAPI(){
        return retrofit.create(FantAPI.class);
    }

    public static void addInterceptor(OkHttpClient.Builder httpClient){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        System.out.println();
    }
}
