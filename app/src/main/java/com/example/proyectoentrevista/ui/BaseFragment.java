package com.example.proyectoentrevista.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import com.example.proyectoentrevista.MainActivity;
import com.example.proyectoentrevista.network.AlmacenarDatos.AlmacenarDatosBody;
import com.example.proyectoentrevista.utils.BaseActivity;
import com.example.proyectoentrevista.utils.ScreenFactory;
import com.example.proyectoentrevista.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;



public abstract class BaseFragment extends Fragment implements BaseActivity.OnBackPressedListener {
    MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.setOnBackPressedListener(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.onBackPressed(getActivity());
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed(Context context) {
        //this.showScreen(ScreenFactory.SCREEN.LOGIN, getArguments());
        //System.out.println("asdadasd");
    }

    public void showScreen(ScreenFactory.SCREEN screen, Bundle params) {
        Utils.getMainActivity(this.getActivity()).showScreen(screen, params);
    }

    public void putData(ArrayList<AlmacenarDatosBody> listData){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        String json = gson.toJson(listData);
        editor.putString("lista", json);
        editor.apply();

    }

    public ArrayList<AlmacenarDatosBody> getData(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String json = sharedPref.getString("lista", "");
        Type listType = new TypeToken<ArrayList<AlmacenarDatosBody>>(){}.getType();
        ArrayList <AlmacenarDatosBody> list = new Gson().fromJson(json, listType);
        return list;
    }

}
