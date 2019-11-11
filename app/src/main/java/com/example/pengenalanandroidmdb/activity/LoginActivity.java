package com.example.pengenalanandroidmdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pengenalanandroidmdb.R;
import com.example.pengenalanandroidmdb.service.RestProcess;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private TextView inputUsername,inputPassword,lupaPassword;
    private Button btnLogin;
    private String var_username,var_password;

    private RestProcess restProcess;
    ArrayList<HashMap<String,String>> arrayLogin = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        inputUsername = findViewById(R.id.editText_username);
        inputPassword = findViewById(R.id.editText_password);
        btnLogin      = findViewById(R.id.btn_login);
        lupaPassword  = findViewById(R.id.lupa_password);


        getSupportActionBar().hide();

        restProcess = new RestProcess();

        // hide action bar
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                var_username = inputUsername.getText().toString();
                var_password = inputPassword.getText().toString();

                if(inputUsername.getText().toString().equals(""))
                    inputUsername.setError("Username harus diisi!");

                else
                    if (inputPassword.getText().toString().length() == 0)
                    inputPassword.setError("password harus 4 karakter");

                else
                    loginProcess(v);
            }
        });

        lupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLupaPassword = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(toLupaPassword);
                finish();
            }
        });
    }
    // akhir methode create

    // awal method login process
    private  void loginProcess (final View view){

        HashMap<String,String> apiData = new HashMap<String, String>();
        apiData                        = restProcess.apiSettingLocal();
        AsyncHttpClient client         = new AsyncHttpClient();         // main thread cuman boleh untuk view
        RequestParams params           = new RequestParams();
        String base_url;

        base_url = apiData.get("str_ws_addr") + "/auth";
        params.put("var_cell_phone",var_username);
        params.put("var_password",var_password);

        client.setBasicAuth(apiData.get("str_ws_user"),apiData.get("str_ws_pass"));
        client.post(base_url, params, new AsyncHttpResponseHandler() {                      // melakukan pemanggilan api denan methode post dengan parameter params, jika data dengan params sesuai maka data akan kirimkan dan mengembalikan success
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp_content = "";

                try {
                    resp_content = new String(responseBody,"UTF-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try {

                    displayLogin(view,resp_content);

                } catch (Throwable e) {
                    Toast.makeText(LoginActivity.this, "Koneksi Gagal ! 1", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(LoginActivity.this,"Koneksi Gagal ! 2",Toast.LENGTH_LONG).show();

            }
        });

    }

    // akhir methode login Process


    //awal methode displayLogin()
    public  void displayLogin(View view,String resp_content){

        try{
            arrayLogin = restProcess.getJsonData(resp_content);

            if (arrayLogin.get(0).get("var_result").equals("1")){
                Intent main_intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main_intent);
                finish();

            } else if (arrayLogin.get(0).get("var_result").equals("0")){
                Toast.makeText(LoginActivity.this,"Koneksi Gagal ! 3",Toast.LENGTH_LONG).show();

            }

        } catch (JSONException e) {
           Toast.makeText(LoginActivity.this,"Koneksi Gagal ! 4",Toast.LENGTH_LONG).show();
        }


    }
    // akhir method displayLogin()


}





























