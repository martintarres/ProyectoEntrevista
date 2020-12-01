package com.example.proyectoentrevista.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.proyectoentrevista.R;
import com.example.proyectoentrevista.ui.BaseFragment;
import com.example.proyectoentrevista.utils.ScreenFactory;


public class PedirPrestamos extends BaseFragment {

    private View view;
    private TypedArray mTypedArrayDocument;
    private Spinner mTypeDoc;

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


        mTypedArrayDocument = getActivity().getResources().obtainTypedArray(R.array.type_document_array);
        int size = mTypedArrayDocument.length();
        String[] array = new String[size];
        for(int i = 0; i < size ; i++){
            array[i] = mTypedArrayDocument.getString(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,array);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
//                R.array.type_document_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        this.mTypeDoc.setAdapter(adapter);

    }

    @Override
    public void onBackPressed(Context context) {
        super.onBackPressed(context);
        showScreen(ScreenFactory.SCREEN.HOME,null);
    }
}