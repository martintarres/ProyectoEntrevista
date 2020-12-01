package com.example.proyectoentrevista.utils;

import android.content.Context;

import com.example.proyectoentrevista.MainActivity;


public class Utils {
    /**
     * Lanza la actividad principal para cambiar el fragment que se esta mostrando
     *
     * @param context

     */
    public static MainActivity getMainActivity(Context context) {
        return (MainActivity) context;
    }
}
