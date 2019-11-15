package com.example.pengenalanandroidmdb.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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
    private View wrongView ;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_office);

        recyclerView = findViewById(R.id.recycler);
        imgbtnBack = findViewById(R.id.imgBtnBack);
        service = API.getClient().create(Service.class);
        shimmerLayout   = findViewById(R.id.shimmerLayout);
        wrongView       = findViewById(R.id.wrongViewOffice);
        getSupportActionBar().hide();

        imgbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        wrongView.setVisibility(View.GONE);     // sembunyikan layout wrong
        shimmerLayout.startShimmerAnimation(); // jalankan shimmer layout

        Call<JsonObject> getOffice = service.getOffice();
        getOffice.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().get("var_result").getAsString().equals("1")) {
                        JsonObject object = response.body();

                        JsonArray array = object.get("result").getAsJsonArray();
                        final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                        for (int i = 0; i < array.size(); i++) {
                            JsonObject mObject = array.get(i).getAsJsonObject();
                            HashMap<String, String> item = new HashMap<>();
                            item.put("office_name", mObject.get("office_name").getAsString());
                            item.put("office_address", mObject.get("office_address").getAsString());
                            item.put("office_description", mObject.get("office_description").getAsString());
                            item.put("cell_phone", mObject.get("cell_phone").getAsString());
                            item.put("email", mObject.get("email").getAsString());
                            item.put("location_gps", mObject.get("location_gps").getAsString());
                            item.put("base_url", mObject.get("base_url").getAsString());
                            arrayList.add(item);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(ShowAllOfficeActivity.this));
                        recyclerView.setHasFixedSize(true);
                        adapter = new RecyclerViewAdapter(ShowAllOfficeActivity.this, arrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RecyclerViewAdapter.ViewHolder holder = (RecyclerViewAdapter.ViewHolder) v.getTag();

                                int position = holder.getAdapterPosition();

                                startActivity(
                                        new Intent(ShowAllOfficeActivity.this, OfficeActivity.class)
                                                .putExtra("office_name", arrayList.get(position).get("office_name"))
                                                .putExtra("office_address", arrayList.get(position).get("office_address"))
                                                .putExtra("office_description", arrayList.get(position).get("office_description"))
                                                .putExtra("cell_phone", arrayList.get(position).get("cell_phone"))
                                                .putExtra("email", arrayList.get(position).get("email"))
                                                .putExtra("location_gps", arrayList.get(position).get("location_gps"))
                                                .putExtra("base_url", arrayList.get(position).get("base_url"))
                                );
                            }
                        });
                    } else {
                        Toast.makeText(ShowAllOfficeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    shimmerLayout.stopShimmerAnimation();       // stop shimmer layout
                    shimmerLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"OnFailure",Toast.LENGTH_SHORT).show();
                shimmerLayout.stopShimmerAnimation();       // stop shimmer layout
                shimmerLayout.setVisibility(View.GONE);     // sembunyikan shimmer layout
                wrongView.setVisibility(View.VISIBLE);      // tampilkan layout pesan kesalahan /wrong layout

            }
        });
    }
}
