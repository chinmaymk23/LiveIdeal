package com.sourcey.relocator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private EditText _firstname;
    private EditText _lastname;
    private EditText _username;
    private EditText _passwordText;
    private EditText _reEnterPasswordText;
    private Button _signupButton;
    private TextView _loginLink;

    private ProgressDialog progressDialog;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        _firstname = findViewById(R.id.input_firstname);
        _lastname = findViewById(R.id.input_lastname);
        _username = findViewById(R.id.input_username);
        _passwordText = findViewById(R.id.input_password);
        _reEnterPasswordText = findViewById(R.id.input_reEnterPassword);
        _signupButton = findViewById(R.id.btn_signup);
        _loginLink = findViewById(R.id.link_login);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String firstname = _firstname.getText().toString();
        String lastname = _lastname.getText().toString();
        String username = _username.getText().toString();
        String password = _passwordText.getText().toString();

        try{
            String url = "https://mcprojectauth.herokuapp.com/registerUser";
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("firstName", firstname);
            jsonParam.put("lastName", lastname);
            jsonParam.put("username", username);
            jsonParam.put("password", password);
            RegistrationTask r = new RegistrationTask(url, jsonParam);
            r.execute();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public boolean registerUser(String url, JSONObject param){
        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);


            Log.i("JSON", param.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(param.toString());

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG", conn.getResponseMessage());

            if(conn.getResponseCode() == 200)
                return true;

            conn.disconnect();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String firstname = _firstname.getText().toString();
        String lastname = _lastname.getText().toString();
        String username = _username.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (firstname.isEmpty() || firstname.length() < 3) {
            _firstname.setError("At least 3 characters");
            valid = false;
        } else {
            _firstname.setError(null);
        }

        if (lastname.isEmpty() || lastname.length() < 3) {
            _lastname.setError("At least 3 characters");
            valid = false;
        } else {
            _lastname.setError(null);
        }

        if (username.isEmpty() || username.length() < 3) {
            _username.setError("At least 3 characters");
            valid = false;
        } else {
            _username.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    class RegistrationTask extends AsyncTask<Void, Void, Boolean> {

        private final String url;
        private final JSONObject jsonParam;

        public RegistrationTask(String url, JSONObject jsonParam) {

            this.url = url;
            this.jsonParam = jsonParam;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return registerUser(url, jsonParam);

        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            progressDialog.dismiss();
            if (isSuccess)
                onSignupSuccess();
            else
                onSignupFailed();
        }
    }
}