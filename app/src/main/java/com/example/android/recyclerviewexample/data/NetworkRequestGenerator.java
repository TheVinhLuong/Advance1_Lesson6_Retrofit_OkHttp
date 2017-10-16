package com.example.android.recyclerviewexample.data;

import android.util.Log;
import com.example.android.recyclerviewexample.utils.Constant;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by VinhTL on 16/10/2017.
 */

public class NetworkRequestGenerator {
    private static final String TAG = NetworkRequestGenerator.class.getSimpleName();
    public static <S> S createService(Class<S> serviceClass) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        httpClientBuilder.addInterceptor(chain -> {
            Log.d(TAG, chain.request().toString());
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header(Constant.FIELD_CONTENT_TYPE, Constant.CONTENT_TYPE_APP_URL_ENCODED)
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
    
        Retrofit retrofit = builder.client(httpClientBuilder.build()).build();
        return retrofit.create(serviceClass);
    }
}
