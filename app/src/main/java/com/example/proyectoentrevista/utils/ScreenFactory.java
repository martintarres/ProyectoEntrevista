package com.example.proyectoentrevista.utils;

import androidx.fragment.app.Fragment;

import com.example.proyectoentrevista.ui.fragments.Home;
import com.example.proyectoentrevista.ui.fragments.PedirPrestamos;
import com.example.proyectoentrevista.ui.fragments.VerPeticiones;


public class ScreenFactory {
    public enum SCREEN {
        HOME,
        PEDIRPRESTAMO,
        VERPETICIONES
    }

    private static ScreenFactory INSTANCE = null;

    // Private constructor suppresses
    private ScreenFactory(){}


    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ScreenFactory();
        }
    }

    public static ScreenFactory getInstance() {
        if (INSTANCE == null){
            createInstance();
        }
        return INSTANCE;
    }

    public Fragment getScreen(SCREEN screen){
        Fragment fragment = null;
        switch (screen){
            case HOME:fragment = Home.newInstance(); break;
            case PEDIRPRESTAMO:fragment = PedirPrestamos.newInstance(); break;
            case VERPETICIONES:fragment = VerPeticiones.newInstance(); break;
        }
        return fragment;
    }

}
