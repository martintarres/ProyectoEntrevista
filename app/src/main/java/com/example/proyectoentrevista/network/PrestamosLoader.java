package com.example.proyectoentrevista.network;

import com.example.proyectoentrevista.network.AlmacenarDatos.AlmacenarDatosBody;
import com.example.proyectoentrevista.network.AlmacenarDatos.AlmacenarResponse;
import com.example.proyectoentrevista.network.SolicitarPrestamo.PrestamoResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrestamosLoader implements PrestamoService{

    private PrestamoService service;
    private String URL;


    public PrestamosLoader(String URL) {

        this.URL = URL;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(20, TimeUnit.SECONDS);
        httpClient.readTimeout(20, TimeUnit.SECONDS);

        httpClient.addInterceptor(logging);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        service = retrofit.create(PrestamoService.class);

    }

    @Override
    public Call<PrestamoResponse> solicitarPrestamo(String authHeader, String dni) {
        return service.solicitarPrestamo(authHeader, dni);
    }

    @Override
    public Call<AlmacenarResponse> almacenarDatos(AlmacenarDatosBody body) {
        return service.almacenarDatos(body);
    }


}
