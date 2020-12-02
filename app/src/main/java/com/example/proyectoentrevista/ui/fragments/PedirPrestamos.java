package com.example.proyectoentrevista.ui.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.proyectoentrevista.R;
import com.example.proyectoentrevista.network.AlmacenarDatos.AlmacenarDatosBody;
import com.example.proyectoentrevista.network.AlmacenarDatos.AlmacenarResponse;

import com.example.proyectoentrevista.network.PrestamosLoader;
import com.example.proyectoentrevista.network.SolicitarPrestamo.PrestamoResponse;
import com.example.proyectoentrevista.ui.BaseFragment;
import com.example.proyectoentrevista.utils.ScreenFactory;
import com.example.proyectoentrevista.utils.Utils;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PedirPrestamos extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private View view;
    private Spinner mTypeGender;
    private EditText textoNombre;
    private EditText textoApellido;
    private EditText textoMail;
    private EditText textoDocNumber;
    private Button botonEnviarPeticion;
    private String typeGeneroField;
    private PrestamosLoader loader;
    private AlmacenarDatosBody almacenarDatosBody;
    private ArrayList<AlmacenarDatosBody> listDatos;

    private String nameTemp;
    private String lastTemp;
    private String genderTemp;
    private String emailTemp;
    private String dniTemp;
    private String position;

    private ProgressDialog mProgressDialog;


    public static PedirPrestamos newInstance() {
        return new PedirPrestamos();
    }

    public PedirPrestamos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!getArguments().getString("name").isEmpty()){
            nameTemp = getArguments().getString("name");
            lastTemp = getArguments().getString("last");
            genderTemp = getArguments().getString("gender");
            emailTemp = getArguments().getString("email");
            dniTemp = getArguments().getString("dni");
            position = getArguments().getString("position");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pedir_prestamos, container, false);
        initButtons();

        if(nameTemp != null && !nameTemp.isEmpty()){
            setDatosEdit();
        }
        return view;
    }

    private void initButtons(){
        mTypeGender = (Spinner) view.findViewById(R.id.spinner_type_doc);
        textoNombre = (EditText) view.findViewById(R.id.editText_nombre);
        textoApellido = (EditText) view.findViewById(R.id.editText_apellido);
        textoMail = (EditText) view.findViewById(R.id.editText_mail);
        textoDocNumber = (EditText) view.findViewById(R.id.editText_docNumber);
        botonEnviarPeticion = (Button) view.findViewById(R.id.button_enviar_peticion);
        botonEnviarPeticion.setOnClickListener(this);

        TypedArray mTypedArrayGender = getActivity().getResources().obtainTypedArray(R.array.type_document_array);
        int size = mTypedArrayGender.length();
        String[] array = new String[size];
        for(int i = 0; i < size ; i++){
            array[i] = mTypedArrayGender.getString(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeGender.setAdapter(adapter);
        mTypeGender.setOnItemSelectedListener(this);

        listDatos = new ArrayList<AlmacenarDatosBody>();

    }

    private void setDatosEdit(){
        textoNombre.setText(nameTemp);
        textoApellido.setText(lastTemp);
        textoMail.setText(emailTemp);
        textoDocNumber.setText(dniTemp);
        if(genderTemp.equals("Masculino")){
            mTypeGender.setSelection(0);
        }else{
            mTypeGender.setSelection(1);
        }
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
                 mProgressDialog = ProgressDialog.show(getActivity(), null, null);;

                if(verificarDatos()){
                    loader = new PrestamosLoader("https://api.moni.com.ar/api/v4/scoring/");
                    Call<PrestamoResponse> call = loader.solicitarPrestamo("ZGpzOTAzaWZuc2Zpb25kZnNubm5u",
                            textoDocNumber.getText().toString());

                    call.enqueue(new Callback<PrestamoResponse>() {
                        @Override
                        public void onResponse(Call<PrestamoResponse> call, Response<PrestamoResponse> response) {
                            if(response.isSuccessful()){
                                loader = new PrestamosLoader("https://wired-torus-98413.firebaseio.com/");

                                almacenarDatosBody = new AlmacenarDatosBody(
                                        textoNombre.getText().toString(),
                                        textoApellido.getText().toString(),
                                        typeGeneroField,
                                        textoMail.getText().toString(),
                                        textoDocNumber.getText().toString(),
                                        response.body().status
                                );

                                listDatos=getData();
                                if(position != null ){
                                    listDatos.remove(Integer.parseInt(position));
                                }
                                if(listDatos == null){
                                    listDatos = new ArrayList<AlmacenarDatosBody>();
                                }
                                listDatos.add(almacenarDatosBody);
                                putData(listDatos);

                                Call<AlmacenarResponse> call1 = loader.almacenarDatos(almacenarDatosBody);
                                call1.enqueue(new Callback<AlmacenarResponse>() {
                                    @Override
                                    public void onResponse(Call<AlmacenarResponse> call, Response<AlmacenarResponse> response) {
                                        if(response.isSuccessful()){
                                            mProgressDialog.dismiss();
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                            builder.setMessage("Solicitud enviada.");
                                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    showScreen(ScreenFactory.SCREEN.HOME,null);
                                                }
                                            });
                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<AlmacenarResponse> call, Throwable t) {
                                        mProgressDialog.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setMessage("Error en la ejecucion del servicio.");
                                        builder.setPositiveButton("Aceptar", null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                });


                            }
                        }

                        @Override
                        public void onFailure(Call<PrestamoResponse> call, Throwable t) {
                            mProgressDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("Error en la ejecucion del servicio.");
                            builder.setPositiveButton("Aceptar", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });

                }else{
                    mProgressDialog.dismiss();
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
        typeGeneroField = typedArray.getString(position);
        System.out.println(typeGeneroField);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}