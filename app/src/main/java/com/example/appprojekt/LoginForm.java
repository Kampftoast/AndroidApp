package com.example.appprojekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class LoginForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
    }
    public void openSignUpForm(View view){

        Intent i = new Intent(getApplicationContext(), SignUpForm.class);
        view.getContext().startActivity(i);
    }
    public void verifyLoginParams(View view){
        TextView usernametextBox = (TextView)findViewById(R.id.usernameTextBox);
        TextView passwordField = (TextView)findViewById(R.id.passwordTextBox);
        TextView errorText = (TextView)findViewById(R.id.errorText);
        if(usernametextBox.getText().length() != 0 && passwordField.getText().length() != 0){
            this.makeWebRequest();
        }else{
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Fields cannot be empty!");
        }
    }
    public void makeWebRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://3.17.112.103:8080/api/login";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        setContentView(R.layout.activity_main_screen);
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
