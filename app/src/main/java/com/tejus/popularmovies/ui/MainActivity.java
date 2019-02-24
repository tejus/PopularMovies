package com.tejus.popularmovies.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.tejus.popularmovies.R;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mFragment = new MainFragment();
            fragmentTransaction.replace(R.id.fragment_container, mFragment, "main_fragment");
            fragmentTransaction.commit();
        } else {
            mFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        }
    }
}
