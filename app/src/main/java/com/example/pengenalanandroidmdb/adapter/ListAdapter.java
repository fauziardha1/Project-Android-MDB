package com.example.pengenalanandroidmdb.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pengenalanandroidmdb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ListAdapter extends BaseAdapter {

    private Activity activity;                      // untuk menampung activity yang memanggil kelas ini
     ArrayList<HashMap<String,String>>data;  // untuk menyimpan data
    private int list_position =0;                   //  untuk indeks data pada json dimulai dari 0
    private static LayoutInflater inflater = null;  // inflater berguna untuk mendefinisikan layout yang akan dipakai
    private String PACKAGE_NAME;                    // untuk menampung nama folder atau package

    public ListAdapter(Activity activity, ArrayList<HashMap<String, String>> data, int list_position) { // constructor
        this.activity       = activity;
        this.data           = data;
        this.list_position  = list_position;

        inflater            = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // inisiasi inflater sehingga akan ditempelkan pada activity yang memanggilnya
        PACKAGE_NAME        = activity.getPackageName(); // mengmabil nama folder / package dari activity yang memanggii kelas ini
    }


    @Override
    public int getCount() { // mengembalikan banyak data yang di dapatkan / yang disimpan
        return data.size();
    }

    @Override
    public Object getItem(int position) { // mengembalikan satu objek data yang dipanggil berdasarkan posisi atau indeks
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        switch (list_position){
            case 1 :
                if(convertView == null)
                    view = inflater.inflate(R.layout.lv_employee_item,null);

                TextView tvEmployeeName     = view.findViewById(R.id.tvEmployeeName);
                TextView tvEmployeeNumber   = view.findViewById(R.id.tvEmployeeNumber);
                TextView tvEmployeeAddress  = view.findViewById(R.id.tvEmployeeAddress);
                TextView tvEmployeeGender   = view.findViewById(R.id.tvEmployeeGender);
                ImageView imgEmployee       = view.findViewById(R.id.img_employee);

                HashMap<String,String> empList = new HashMap<String,String>();

                empList = data.get(position);

                tvEmployeeName.setText(empList.get("employee_name"));
                tvEmployeeNumber.setText(empList.get("nomor_induk_pegawai"));
                tvEmployeeAddress.setText(empList.get("address"));
                tvEmployeeGender.setText(empList.get("gender"));
                String imgUrl = empList.get("base_url");
                Picasso.get().load(imgUrl).into(imgEmployee);


                break;

                default:
                    break;
        }



        return view;
    }
}













