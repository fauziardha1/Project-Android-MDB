package com.example.pengenalanandroidmdb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pengenalanandroidmdb.R;
import com.example.pengenalanandroidmdb.model.DownloadImageFromInternet;

public class MainActivity extends AppCompatActivity {

    private TextView inputUsername,inputPassword;
    private Button btnLogin;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imageView);

        String url = "https://i.imgur.com/lgI7maQ.jpg";

        // Image link from internet
        new DownloadImageFromInternet(imgView)
                .execute(url);





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
