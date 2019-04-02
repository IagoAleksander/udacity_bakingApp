package com.iaz.bakingapp.networkUtils;

import com.iaz.bakingapp.models.Recipe;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public final class BakingInfoApi {

    private static final String BAKING_INFO_BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    private static final BakingService BAKING_SERVICE;

    static {

//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BAKING_INFO_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BAKING_SERVICE = retrofit.create(BakingService.class);
    }


    public static void getBakingInfo(Callback<List<Recipe>> callback) {
        Call<List<Recipe>> call = BAKING_SERVICE.getBakingInfo();
        call.enqueue(callback);
    }
}
