package com.tejus.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tejus.popularmovies.utilities.ApiKey;

public class ApiActivity extends AppCompatActivity {

    private EditText apiKeyEditText;
    private Button saveApiButton;
    private Button clearApiButton;
    private TextView apiKeyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        apiKeyTextView = findViewById(R.id.tv_api_key);
        if (ApiKey.isApiSet()) {
            apiKeyTextView.setText(ApiKey.getApiKey());
        } else {
            apiKeyTextView.setText(R.string.api_key_not_set);
        }

        apiKeyEditText = findViewById(R.id.et_api_key);
        saveApiButton = findViewById(R.id.btn_save_api_key);
        saveApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apiKey = apiKeyEditText.getText().toString();
                if (!apiKey.equals(null) && !apiKey.equals("")) {
                    ApiKey.setApiKey(apiKey);
                    apiKeyTextView.setText(apiKey);
                    Toast.makeText(getApplicationContext(), R.string.api_saved, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.api_not_provided, Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearApiButton = findViewById(R.id.btn_clear_api_key);
        clearApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ApiKey.isApiSet()) {
                    ApiKey.clearApiKey();
                    apiKeyEditText.setText("");
                    apiKeyTextView.setText(R.string.api_key_not_set);
                    Toast.makeText(getApplicationContext(), R.string.api_cleared, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.api_not_set, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
