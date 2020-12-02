package com.example.proyectoentrevista;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.example.proyectoentrevista.utils.BaseActivity;
import com.example.proyectoentrevista.utils.ScreenFactory;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public Fragment getFragment() {
        return ScreenFactory.getInstance().getScreen(ScreenFactory.SCREEN.HOME);
    }

}
