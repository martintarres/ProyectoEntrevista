package com.example.proyectoentrevista.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoentrevista.R;
import com.example.proyectoentrevista.ui.BaseFragment;
import com.example.proyectoentrevista.utils.ScreenFactory;


public class VerPeticiones extends BaseFragment {

    private View view;

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

        return view;
    }

    private void initButtons(){
    }

    @Override
    public void onBackPressed(Context context) {
        super.onBackPressed(context);
        showScreen(ScreenFactory.SCREEN.HOME,null);
    }
}