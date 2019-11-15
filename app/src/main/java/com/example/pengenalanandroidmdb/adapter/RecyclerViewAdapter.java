package com.example.pengenalanandroidmdb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pengenalanandroidmdb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HashMap<String, String>> arrayList;

    public RecyclerViewAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    private View.OnClickListener onItemClickListener;

    public void setOnItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
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

        Picasso.get().load(hashMap.get("base_url")).into(holder.imgOffice);
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

            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }
}
