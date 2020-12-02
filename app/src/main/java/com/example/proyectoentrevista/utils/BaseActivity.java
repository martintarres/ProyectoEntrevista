package com.example.proyectoentrevista.utils;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.proyectoentrevista.R;


public abstract class BaseActivity extends AppCompatActivity {
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    protected ScreenFactory.SCREEN mScreenEnum;
    private OnBackPressedListener mListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());




        if (savedInstanceState == null) {
            if (getFragment() != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, getFragment(), getFragment().getClass().getName()).commit();
            }


        }
    }

    public void showScreen(ScreenFactory.SCREEN screenEnum, Bundle params) {
        if (screenEnum != null) {
            Fragment mFragment = ScreenFactory.getInstance().getScreen(screenEnum);
            if (params != null) {
                mFragment.setArguments(params);
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mFragment, mFragment.getClass().getName()).commitAllowingStateLoss();
            this.mScreenEnum = screenEnum;
        }
    }


    public abstract int getLayout();

    public abstract Fragment getFragment();

    @Override
    public void onBackPressed() {
        if (mScreenEnum != null) {
            if (this.mListener != null) {
                this.mListener.onBackPressed(this);
            }
        } else {
            super.onBackPressed();
        }
    }

    public void setOnBackPressedListener(OnBackPressedListener listener) {
        this.mListener = listener;
    }

    /**
     * Listener creado para capturar cuando se invoca al metodo
     */
    public interface OnBackPressedListener {
        void onBackPressed(Context context);
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
