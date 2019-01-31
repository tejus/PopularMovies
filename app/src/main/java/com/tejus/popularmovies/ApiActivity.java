package com.tejus.popularmovies;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tejus.popularmovies.utilities.ApiKey;

public class ApiActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mButton;
    private TextView mTextView;
    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        mScrollView = findViewById(R.id.api_layout);
        mEditText = findViewById(R.id.et_api_key);
        mButton = findViewById(R.id.btn_save_api_key);
        mTextView = findViewById(R.id.tv_api_key);

        //Check if the API Key has been set or not, and set the corresponding text to the views
        if (ApiKey.isApiSet()) {
            mTextView.setText(ApiKey.getApiKey());
            mButton.setText(R.string.api_activity_clear);
        } else {
            mTextView.setText(R.string.api_key_not_set);
            mButton.setText(R.string.api_activity_save);
        }

        //Define the click actions for the button based on whether the API Key has been set or entered
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apiKey = mEditText.getText().toString();
                if (ApiKey.isApiSet()) {
                    ApiKey.clearApiKey();
                    mEditText.setText("");
                    mTextView.setText(R.string.api_key_not_set);
                    mButton.setText(R.string.api_activity_save);
                    Snackbar.make(mScrollView, R.string.api_cleared, Snackbar.LENGTH_SHORT).show();
                } else {
                    if (!apiKey.equals("")) {
                        ApiKey.setApiKey(apiKey);
                        mTextView.setText(apiKey);
                        mButton.setText(R.string.api_activity_clear);
                        Snackbar.make(mScrollView, R.string.api_saved, Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(mScrollView, R.string.api_not_provided, Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
