package com.smartcarpark.smartcarparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smartcarpark.smartcarparking.network_action.PerformNetworkRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, PerformNetworkRequest.AsyncResponse {

    private EditText userId, password;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initializeViews();
        loginButton.setOnClickListener(this);
    }

    private void initializeViews() {
        userId = findViewById(R.id.userid_edittext);
        password = findViewById(R.id.password_edittext);
        loginButton = findViewById(R.id.login_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                login();
        }
    }

    private void login() {
        if (loginValidation()) {
            Map<String, String> params = new HashMap<>();
            params.put(Constants.API_USER_ID, userId.getText().toString());
            params.put(Constants.API_PASSWORD, password.getText().toString());

            new PerformNetworkRequest(Constants.URL_VALIDATE_USER_LOGIN, params, Constants.CODE_POST_REQUEST, getApplicationContext(), this).execute();
        }
    }

    private boolean loginValidation() {
        boolean validLogin = true;

        if (TextUtils.isEmpty(userId.getText().toString())) {
            userId.setError("Please enter User Id");
            userId.requestFocus();
            validLogin = false;
        }

        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Please enter password");
            password.requestFocus();
            validLogin = false;
        }

        return validLogin;
    }

    @Override
    public void processFinish(String output) {
        try {
            System.out.println("\nOutput: " + output);
            JSONObject object = new JSONObject(output);

            if (! object.getBoolean(Constants.ERROR_RESPONSE)) {
                Intent intent = new Intent(this, Dashboard.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, object.getString(Constants.MESSAGE_RESPONSE), Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
