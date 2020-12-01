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
import com.example.proyectoentrevista.network.AlmacenarDatos.AlmacenarDatosBody;
import com.example.proyectoentrevista.network.AlmacenarDatos.AlmacenarResponse;
import com.example.proyectoentrevista.network.PrestamoService;
import com.example.proyectoentrevista.network.PrestamosLoader;
import com.example.proyectoentrevista.network.SolicitarPrestamo.PrestamoResponse;
import com.example.proyectoentrevista.ui.BaseFragment;
import com.example.proyectoentrevista.utils.ScreenFactory;
import com.example.proyectoentrevista.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HEAD;


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
    private PrestamosLoader loader;

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

                    loader = new PrestamosLoader("https://api.moni.com.ar/api/v4/scoring/");
                    Call<PrestamoResponse> call = loader.solicitarPrestamo("ZGpzOTAzaWZuc2Zpb25kZnNubm5u",
                            textoDocNumber.getText().toString());

                    call.enqueue(new Callback<PrestamoResponse>() {
                        @Override
                        public void onResponse(Call<PrestamoResponse> call, Response<PrestamoResponse> response) {
                            if(response.isSuccessful()){
                                loader = new PrestamosLoader("https://wired-torus-98413.firebaseio.com/");

                                AlmacenarDatosBody almacenarDatosBody = new AlmacenarDatosBody(
                                        textoNombre.getText().toString(),
                                        textoApellido.getText().toString(),
                                        typeGeneroField,
                                        textoMail.getText().toString(),
                                        textoDocNumber.getText().toString(),
                                        response.body().status
                                );


                                Call<AlmacenarResponse> call1 = loader.almacenarDatos(almacenarDatosBody);
                                call1.enqueue(new Callback<AlmacenarResponse>() {
                                    @Override
                                    public void onResponse(Call<AlmacenarResponse> call, Response<AlmacenarResponse> response) {
                                        if(response.isSuccessful()){

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<AlmacenarResponse> call, Throwable t) {
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("Error en la ejecucion del servicio.");
                            builder.setPositiveButton("Aceptar", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });

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