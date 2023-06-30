package com.eme22.citasApp.model.api;

import com.eme22.citasApp.model.api.serializer.LocalDateDeserializer;
import com.eme22.citasApp.model.api.serializer.LocalDateSerializer;
import com.eme22.citasApp.model.api.serializer.LocalDateTimeDeserializer;
import com.eme22.citasApp.model.api.serializer.LocalDateTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://hospital-web-server-production.up.railway.app/";
    //public static final String BASE_URL = "https://9a21-181-66-164-171.ngrok-free.app";
    private static Retrofit retrofit = null;

    private static GsonBuilder gsonBuilder = new GsonBuilder();

    private static Gson buildGson() {

        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());

        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());

        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());

        return gsonBuilder.setPrettyPrinting().create();

    }

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder()
                                .addHeader("ngrok-skip-browser-warning", "value")
                                .addHeader("User-Agent", "a").build();
                        return chain.proceed(request);
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(buildGson()))
                    .build();
        }
        return retrofit;
    }

    public static Api getSOService() {
        return ApiClient.getClient(BASE_URL).create(Api.class);
    }
}