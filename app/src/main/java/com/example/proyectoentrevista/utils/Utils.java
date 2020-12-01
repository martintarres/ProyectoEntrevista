package com.example.proyectoentrevista.utils;

import android.content.Context;
import android.util.Patterns;

import com.example.proyectoentrevista.MainActivity;

import java.util.regex.Pattern;


public class Utils {
    /**
     * Lanza la actividad principal para cambiar el fragment que se esta mostrando
     *
     * @param context

     */
    public static MainActivity getMainActivity(Context context) {
        return (MainActivity) context;
    }

    public static boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
