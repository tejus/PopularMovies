package com.tejus.popularmovies.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.tejus.popularmovies.R;

public class MainActivity extends AppCompatActivity implements MainFragment.OnMovieClickListener {

    private static final String LOG_TAG = "MainActivity";

    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mFragment = new MainFragment();
            replaceFragment(mFragment, false);
        } else {
            mFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        }
    }

    @Override
    public void onMovieClicked(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        Fragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        replaceFragment(fragment, true);
    }

    private void replaceFragment(Fragment fragment, boolean toBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.toString());
        if (toBackStack) {
            fragmentTransaction.addToBackStack(fragment.toString());
        }
        fragmentTransaction.commit();
    }
}
