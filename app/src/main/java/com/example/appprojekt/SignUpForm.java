package com.example.appprojekt;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class SignUpForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);
    }

    public void createAccount(View view) {
        TextView passwordField = (TextView) findViewById(R.id.passwordTextBox);
        TextView retryPasswordField = (TextView) findViewById(R.id.retypePasswordTextBox);

        if (passwordField.getText().toString().equals(retryPasswordField.getText().toString())) {
            this.makeWebRequest();
        } else {
            TextView errorText = (TextView) findViewById(R.id.errorText);
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Passwords doesnt match!");
        }
    }

    public void makeWebRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://3.17.112.103:8080/api/createaccount";


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Intent i = new Intent(getApplicationContext(), LoginForm.class);
                        startActivity(i);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        String message = new String(error.networkResponse.data);
                        TextView errorText = (TextView) findViewById(R.id.errorText);
                        errorText.setVisibility(View.VISIBLE);
                        errorText.setText(message);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                TextView usernameTextBox = (TextView) findViewById(R.id.usernameTextBox);
                TextView passwordTextBox = (TextView) findViewById(R.id.passwordTextBox);
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", usernameTextBox.getText().toString());
                params.put("password", passwordTextBox.getText().toString());

                return params;
            }
        };
        queue.add(postRequest);
    }
}