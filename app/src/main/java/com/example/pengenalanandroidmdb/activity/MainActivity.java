package com.example.pengenalanandroidmdb.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androdocs.httprequest.HttpRequest;
import com.example.pengenalanandroidmdb.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private TextView inputUsername,inputPassword;
    private Button btnLogin, btnEmpl, btnCuti, btnIzin, btnSetting, btn_kegiatan;
    private ImageView imgView;
    private String CITY = "Jakarta,ID";
    private String API = "5743929c4a396e392506b1896ab261b0";
    private TextView addressTxt, updated_atTxt, statusTxt, tempTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Multi Inti Digital Bisnis");

        imgView = findViewById(R.id.imageView);
        //weather
        addressTxt = findViewById(R.id.address);
        updated_atTxt = findViewById(R.id.updated_at);
        statusTxt = findViewById(R.id.status);
        tempTxt     = findViewById(R.id.temp);

        //activity lain2
        btnEmpl = findViewById(R.id.btn_karyawan);
        btnCuti = findViewById(R.id.btn_c);
        btnIzin = findViewById(R.id.btn_izin);
        btnSetting = findViewById(R.id.btn_setting);
        btn_kegiatan = findViewById(R.id.btn_kegiatan);

        btnEmpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EmployeeActivity.class));
            }
        });

        btnCuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CutiActivity.class));
            }
        });

        btnIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IzinActivity.class));
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
            }
        });
        btn_kegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, KegiatanActivity.class));
            }
        });

        //jalankan api cuaca
        new weatherTask().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.data_karyawan:
                startActivity( new Intent(MainActivity.this, EmployeeActivity.class));
                break;
            case R.id.tentang_app:
                startActivity(new Intent(MainActivity.this, AboutAppActivity.class));
                break;
            case R.id.keluar:
                startActivity( new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.data_office:
                startActivity(new Intent(getApplicationContext(),ShowAllOfficeActivity.class));
                break;

        }

        return true;
    }

    // class and method untuk menggunakan api cuaca
    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result){
            try

            {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";

                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");


                /* Populating extracted data into our views */
                addressTxt.setText(address);
                updated_atTxt.setText(updatedAtText);
                statusTxt.setText(weatherDescription.toUpperCase());
                tempTxt.setText(temp);

            }catch(
                    JSONException ignored)

            {
            }
        }

    }
    //akhir api cuaca



}
