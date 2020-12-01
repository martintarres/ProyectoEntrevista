package com.example.proyectoentrevista.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.proyectoentrevista.R;
import com.example.proyectoentrevista.ui.BaseFragment;
import com.example.proyectoentrevista.utils.ScreenFactory;
import com.example.proyectoentrevista.utils.Utils;


public class PedirPrestamos extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private View view;
    private TypedArray mTypedArrayDocument;
    private Spinner mTypeDoc;
    private EditText textoNombre;
    private EditText textoApellido;
    private EditText textoMail;
    private EditText textoDocNumber;
    private Button botonEnviarPeticion;
    private int typeGeneroSelected = -1;
    private String typeGeneroField;

    public static PedirPrestamos newInstance() {
        return new PedirPrestamos();
    }

    public PedirPrestamos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pedir_prestamos, container, false);
        initButtons();

        return view;
    }

    private void initButtons(){
        mTypeDoc = (Spinner) view.findViewById(R.id.spinner_type_doc);
        textoNombre = (EditText) view.findViewById(R.id.editText_nombre);
        textoApellido = (EditText) view.findViewById(R.id.editText_apellido);
        textoMail = (EditText) view.findViewById(R.id.editText_mail);
        textoDocNumber = (EditText) view.findViewById(R.id.editText_docNumber);
        botonEnviarPeticion = (Button) view.findViewById(R.id.button_enviar_peticion);
        botonEnviarPeticion.setOnClickListener(this);

        mTypedArrayDocument = getActivity().getResources().obtainTypedArray(R.array.type_document_array);
        int size = mTypedArrayDocument.length();
        String[] array = new String[size];
        for(int i = 0; i < size ; i++){
            array[i] = mTypedArrayDocument.getString(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeDoc.setAdapter(adapter);
        mTypeDoc.setOnItemSelectedListener(this);

    }

    @Override
    public void onBackPressed(Context context) {
        super.onBackPressed(context);
        showScreen(ScreenFactory.SCREEN.HOME,null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_enviar_peticion:
                if(verificarDatos()){
                    System.out.println("LLAMAR SERVICIO");
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Error en los datos ingresados.");
                    builder.setPositiveButton("Aceptar", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                break;

        }
    }

    private boolean verificarDatos(){
        if(!textoNombre.getText().toString().isEmpty() && !textoApellido.getText().toString().isEmpty() &&
        !textoMail.getText().toString().isEmpty() && !textoDocNumber.getText().toString().isEmpty()
        && Utils.validEmail(textoMail.getText().toString())){
            return true;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TypedArray typedArray = getActivity().getResources().obtainTypedArray(R.array.type_document_array);
        typeGeneroSelected = position;
        typeGeneroField = typedArray.getString(position);
        System.out.println(typeGeneroField);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}