package com.example.proyectoentrevista.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyectoentrevista.Adapter.SolicitudesAdapter;
import com.example.proyectoentrevista.R;
import com.example.proyectoentrevista.network.AlmacenarDatos.AlmacenarDatosBody;
import com.example.proyectoentrevista.ui.BaseFragment;
import com.example.proyectoentrevista.utils.ScreenFactory;

import java.awt.font.TextAttribute;
import java.util.ArrayList;


public class VerPeticiones extends BaseFragment {

    private View view;
    private RecyclerView peticiones;
    private TextView vacio;

    public static VerPeticiones newInstance() {
        return new VerPeticiones();
    }

    public VerPeticiones() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ver_peticiones, container, false);

        initButtons();
        cargarPeticiones();


        return view;
    }

    private void initButtons(){
        peticiones = view.findViewById(R.id.recyclerView_peticiones);
        peticiones.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        peticiones.setLayoutManager(layoutManager);

        vacio = view.findViewById(R.id.textView_vacio);
    }

    @Override
    public void onBackPressed(Context context) {
        super.onBackPressed(context);
        showScreen(ScreenFactory.SCREEN.HOME,null);
    }

    private void cargarPeticiones(){
        SolicitudesAdapter solicitudesAdapter = new SolicitudesAdapter(getData(), view, peticiones, this, vacio);
        peticiones.setAdapter(solicitudesAdapter);

    }
}