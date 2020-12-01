package com.example.proyectoentrevista.network;

import com.example.proyectoentrevista.network.AlmacenarDatos.AlmacenarDatosBody;
import com.example.proyectoentrevista.network.AlmacenarDatos.AlmacenarResponse;
import com.example.proyectoentrevista.network.SolicitarPrestamo.PrestamoResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PrestamoService {
    @GET("pre-score/{dni}")
    Call<PrestamoResponse> solicitarPrestamo(
            @Header("credential") String authHeader,
            @Path("dni") String dni
    );

    @POST("users.json")
    Call<AlmacenarResponse> almacenarDatos(
            @Body AlmacenarDatosBody body
    );
}
