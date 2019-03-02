package com.tejus.popularmovies.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.tejus.popularmovies.R;
import com.tejus.popularmovies.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //  TODO https://mikescamell.com/shared-element-transitions-part-1/index.html
        int pos = getIntent().getIntExtra("position", 0);
        //String transitionName = getIntent().getStringExtra("transition_name");
        Bundle bundle = new Bundle();
        bundle.putInt("position", pos);
        //bundle.putString("transition_name", transitionName);
        Fragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                //.addSharedElement()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
