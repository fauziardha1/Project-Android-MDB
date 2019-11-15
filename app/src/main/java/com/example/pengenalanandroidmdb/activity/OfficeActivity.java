package com.example.pengenalanandroidmdb.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pengenalanandroidmdb.R;
import com.squareup.picasso.Picasso;;

import androidx.appcompat.app.AppCompatActivity;

public class OfficeActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvCell, tvAddress, tvDescription;
    ImageView imgOffice1;
    Button btnLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvCell = findViewById(R.id.tvCell);
        tvAddress = findViewById(R.id.tvAddress);
        tvDescription = findViewById(R.id.tvDescription);
        imgOffice1 = findViewById(R.id.imgOffice1);
        btnLocation = findViewById(R.id.btnLocation);

        String office_name = getIntent().getStringExtra("office_name");
        String office_address = getIntent().getStringExtra("office_address");
        String office_description = getIntent().getStringExtra("office_description");
        String cell_phone = getIntent().getStringExtra("cell_phone");
        String email = getIntent().getStringExtra("email");
        final String location_gps = getIntent().getStringExtra("location_gps");
        String base_url = getIntent().getStringExtra("base_url");

        tvName.setText(office_name);
        tvEmail.setText(email);
        tvCell.setText(cell_phone);
        tvAddress.setText(office_address);
        tvDescription.setText(office_description);
        Picasso.get().load(base_url).into(imgOffice1);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/" + location_gps)));
            }
        });

    }
}
