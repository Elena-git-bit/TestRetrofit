package org.example.util;

import lombok.experimental.UtilityClass;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@UtilityClass
public class RetrofitUtils {

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new PrettyLogger()); //New:07.02.2022
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

//--> Code without logging
//public   Retrofit getRetrofit(){
//         return new Retrofit.Builder()
//                .baseUrl(ConfigUtils.getBaseUrl())
//                .addConverterFactory(JacksonConverterFactory.create())
//                .build();
//    }
//<-- Code without logging

//--> Code with logging
         public Retrofit getRetrofit(){
             logging.setLevel(HttpLoggingInterceptor.Level.BODY);
             httpClient.addInterceptor(logging);
         return new Retrofit.Builder()
            .baseUrl(ConfigUtils.getBaseUrl())
            .addConverterFactory(JacksonConverterFactory.create())
            //добавляем okhttp-клиент к retrofit-клиенту
            .client(httpClient.build())
            .build();

}
//<-- Code with logging
}
