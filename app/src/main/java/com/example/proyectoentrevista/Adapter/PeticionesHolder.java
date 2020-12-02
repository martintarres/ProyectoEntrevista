package com.example.proyectoentrevista.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoentrevista.R;

public class PeticionesHolder extends RecyclerView.ViewHolder {
    public TextView nombreAdapter;
    public TextView apellidoAdapter;
    public Button editarAdapter;
    public Button eliminarAdapter;


    public PeticionesHolder(View itemView){
        super(itemView);

        nombreAdapter = itemView.findViewById(R.id.textView_nombre_adapter);
        apellidoAdapter = itemView.findViewById(R.id.textView_apellido_adapter);
        editarAdapter = itemView.findViewById(R.id.button_editar_adapter);
        eliminarAdapter = itemView.findViewById(R.id.button_eliminar_adapter);
    }
}
