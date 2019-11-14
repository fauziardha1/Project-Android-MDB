package com.example.pengenalanandroidmdb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androdocs.httprequest.HttpRequest;
import com.example.pengenalanandroidmdb.R;
import com.example.pengenalanandroidmdb.model.DownloadImageFromInternet;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView inputUsername, inputPassword;
    private Button btnLogin;
    private ImageView imgView;
    String CITY = "Jakarta,ID";
    String API = "5743929c4a396e392506b1896ab261b0";
    TextView addressTxt, updated_atTxt, statusTxt, tempTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //imgView = findViewById(R.id.imageView);

        String url = "https://i.imgur.com/lgI7maQ.jpg";

        // Image link from internet (dihapus)
        new DownloadImageFromInternet(imgView)
                .execute(url);

        //weather
        addressTxt = findViewById(R.id.address);
        updated_atTxt = findViewById(R.id.updated_at);
        statusTxt = findViewById(R.id.status);
        tempTxt = findViewById(R.id.temp);


        new weatherTask().execute();

    }

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

            Long updatedAt = jsonObj.getLong("dt");
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
        JSONException e)

        {
        }
    }

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



}
