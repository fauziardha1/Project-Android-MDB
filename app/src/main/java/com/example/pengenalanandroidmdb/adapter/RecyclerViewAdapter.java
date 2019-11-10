package com.example.pengenalanandroidmdb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pengenalanandroidmdb.R;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HashMap<String, String>> arrayList;

    public RecyclerViewAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_office_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        HashMap<String, String> hashMap = arrayList.get(position);
        holder.tvNameOffice.setText(hashMap.get("office_name"));
        holder.tvEmailOffice.setText(hashMap.get("office_email"));
        holder.tvPhoneOffice.setText(hashMap.get("cell_phone"));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgOffice;
        TextView tvNameOffice, tvEmailOffice, tvPhoneOffice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgOffice = itemView.findViewById(R.id.imgOffice);
            tvNameOffice = itemView.findViewById(R.id.tvNameOffice);
            tvEmailOffice = itemView.findViewById(R.id.tvEmailOffice);
            tvPhoneOffice = itemView.findViewById(R.id.tvPhoneOffice);
        }
    }
}
