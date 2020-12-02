package com.example.proyectoentrevista.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoentrevista.R;
import com.example.proyectoentrevista.network.AlmacenarDatos.AlmacenarDatosBody;
import com.example.proyectoentrevista.ui.fragments.VerPeticiones;
import com.example.proyectoentrevista.utils.ScreenFactory;

import java.awt.font.TextAttribute;
import java.util.ArrayList;



public class SolicitudesAdapter extends RecyclerView.Adapter<PeticionesHolder> {

    private ArrayList<AlmacenarDatosBody> listaPeticiones;
    private TextView nombreAdapter;
    private TextView apellidoAdapter;
    private Button editarAdapter;
    private Button eliminarAdapter;
    private View viewVerPeticiones;
    private RecyclerView peticiones;
    private Fragment fragment;
    private TextView vacio;

    public SolicitudesAdapter(ArrayList<AlmacenarDatosBody> listaPeticiones, View viewVerPeticiones,
                              RecyclerView peticiones, Fragment fragment, TextView vacio){
        this.listaPeticiones = listaPeticiones;
        this.viewVerPeticiones = viewVerPeticiones;
        this.peticiones = peticiones;
        this.fragment = fragment;
        this.vacio=vacio;
        nombreAdapter = viewVerPeticiones.findViewById(R.id.textView_nombre_adapter);
        apellidoAdapter = viewVerPeticiones.findViewById(R.id.textView_apellido_adapter);
        editarAdapter = viewVerPeticiones.findViewById(R.id.button_editar_adapter);
        eliminarAdapter = viewVerPeticiones.findViewById(R.id.button_eliminar_adapter);

        if(listaPeticiones != null && !listaPeticiones.isEmpty()){
            peticiones.setVisibility(View.VISIBLE);
            vacio.setVisibility(View.GONE);
        }else{
            peticiones.setVisibility(View.GONE);
            vacio.setVisibility(View.VISIBLE);
        }

    }


    @NonNull
    @Override
    public PeticionesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_solicitudes, parent, false);
        return new PeticionesHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PeticionesHolder holder, int position) {
        holder.nombreAdapter.setText(listaPeticiones.get(position).name);
        holder.apellidoAdapter.setText(listaPeticiones.get(position).last);

        holder.eliminarAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaPeticiones.remove(position);
                ((VerPeticiones) fragment).putData(listaPeticiones);

                SolicitudesAdapter solicitudesAdapter = new SolicitudesAdapter(((VerPeticiones) fragment).getData(),
                        viewVerPeticiones, peticiones,fragment, vacio);
                peticiones.setAdapter(solicitudesAdapter);
            }
        });

        holder.editarAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name", listaPeticiones.get(position).name );
                bundle.putString("last", listaPeticiones.get(position).last );
                bundle.putString("gender", listaPeticiones.get(position).gender );
                bundle.putString("email", listaPeticiones.get(position).email );
                bundle.putString("dni", listaPeticiones.get(position).dni );
                bundle.putString("position", String.valueOf(position ));
                ((VerPeticiones) fragment).showScreen(ScreenFactory.SCREEN.PEDIRPRESTAMO,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listaPeticiones != null){
            return listaPeticiones.size();
        }else{
            return 0;
        }

    }
}
