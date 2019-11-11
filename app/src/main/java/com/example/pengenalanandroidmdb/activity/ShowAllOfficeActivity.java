package com.example.pengenalanandroidmdb.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pengenalanandroidmdb.R;
import com.example.pengenalanandroidmdb.adapter.RecyclerViewAdapter;
import com.example.pengenalanandroidmdb.service.API;
import com.example.pengenalanandroidmdb.service.Service;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowAllOfficeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Service service;
    ImageButton imgbtnBack;
    ShimmerLayout shimmerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_office);

        recyclerView = findViewById(R.id.recycler);
        imgbtnBack = findViewById(R.id.imgBtnBack);
        service = API.getClient().create(Service.class);
        shimmerLayout   = findViewById(R.id.shimmerLayout);

        imgbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shimmerLayout.startShimmerAnimation(); // jalankan shimmer layout
        Call<JsonObject> getOffice = service.getOffice();
        getOffice.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().get("var_result").getAsString().equals("1")) {
                        JsonObject object = response.body();

                        JsonArray array = object.get("result").getAsJsonArray();
                        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                        for (int i = 0; i < array.size(); i++) {
                            JsonObject mObject = array.get(i).getAsJsonObject();
                            HashMap<String, String> item = new HashMap<>();
                            item.put("office_name", mObject.get("object_name").getAsString());
                            item.put("office_address", mObject.get("office_address").getAsString());
                            item.put("offce_description", mObject.get("office_description").getAsString());
                            item.put("cell_phone", mObject.get("cell_phone").getAsString());
                            item.put("email", mObject.get("email").getAsString());
                            item.put("location_gps", mObject.get("location_gps").getAsString());
                            item.put("base_url", mObject.get("base_url").getAsString());
                            arrayList.add(item);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(), arrayList));
                        shimmerLayout.stopShimmerAnimation();       // stop shimmer layout
                        shimmerLayout.setVisibility(View.GONE);     //  hilangkan shimmer layout
                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        shimmerLayout.stopShimmerAnimation();       // stop shimmer layout
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                shimmerLayout.stopShimmerAnimation();       // stop shimmer layout

            }
        });
    }
}