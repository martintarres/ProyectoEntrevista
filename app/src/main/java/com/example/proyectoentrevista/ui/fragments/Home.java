package com.example.proyectoentrevista.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyectoentrevista.R;
import com.example.proyectoentrevista.ui.BaseFragment;
import com.example.proyectoentrevista.utils.ScreenFactory;


public class Home extends BaseFragment implements View.OnClickListener {

    private Button pedirPrestamo;
    private Button verSolicitudes;
    private View view;

    public static Home newInstance() {
        return new Home();
    }

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initButtons();

        return view;
    }

    private void initButtons(){
        pedirPrestamo = (Button) view.findViewById(R.id.button_pedir_prestamo);
        verSolicitudes = (Button) view.findViewById(R.id.button_ver_peticiones);

        pedirPrestamo.setOnClickListener(this);
        verSolicitudes.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_pedir_prestamo:
                showScreen(ScreenFactory.SCREEN.PEDIRPRESTAMO,null);
                break;
            case R.id.button_ver_peticiones:
                showScreen(ScreenFactory.SCREEN.VERPETICIONES,null);
                break;

        }
    }
}