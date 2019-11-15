package com.example.pengenalanandroidmdb.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import io.supercharge.shimmerlayout.ShimmerLayout;

import com.example.pengenalanandroidmdb.adapter.ListAdapter;
import com.example.pengenalanandroidmdb.R;
import com.example.pengenalanandroidmdb.service.RestProcess;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class EmployeeActivity extends AppCompatActivity {

    //API process
    private RestProcess rest_class;

    //ListView variable
    ListView lvDataKaryawan;

    // Declare Class ListAdappter
    ListAdapter adapter;

    // Declare ArrayList
    ArrayList<HashMap<String,String>> dataKaryawan_arrayList = new ArrayList<HashMap<String, String>>();

    //Declare ShrimmerLayout
    private ShimmerLayout shimmerLayout;

    private View wrongView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        setTitle("Employee");

        rest_class = new RestProcess();

        lvDataKaryawan = findViewById(R.id.listview_employee);
        shimmerLayout   = findViewById(R.id.shimmerLayout);
        wrongView       = findViewById(R.id.wrong);
        wrongView.setVisibility(View.GONE);


        shimmerLayout.startShimmerAnimation();             //menjalankan animasi dari shimmerLayout
        getDataKaryawan(lvDataKaryawan);


        lvDataKaryawan.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> item = new HashMap<String,String>();
                item = (HashMap<String, String>) adapter.getItem(position);
                final AlertDialog dialogBuilder = new AlertDialog.Builder(EmployeeActivity.this).create();
                LayoutInflater    inflater       = EmployeeActivity.this.getLayoutInflater();
                View dialogView                 = inflater.inflate(R.layout.employee_detail,null);

                ImageView img = dialogView.findViewById(R.id.profile_image);
                TextView nama,alamat,nip,gender,tempatLahir,tanggalLahir,golDarah,agama,status;
                nama          = dialogView.findViewById(R.id.employee_detail_nama);
                nip           = dialogView.findViewById(R.id.employee_detail_nip);
                alamat        = dialogView.findViewById(R.id.employee_detail_alamat);
                gender        = dialogView.findViewById(R.id.employee_detail_gender);
                tempatLahir   = dialogView.findViewById(R.id.employee_detail_tempat_lahir);
                tanggalLahir  = dialogView.findViewById(R.id.employee_detail_tanggal_lahir);
                golDarah      = dialogView.findViewById(R.id.employee_detail_Gol_darah);
                agama         = dialogView.findViewById(R.id.employee_detail_agama);
                status        = dialogView.findViewById(R.id.employee_detail_status);


                Button btn_tutup = dialogView.findViewById(R.id.btn_tutup);
                btn_tutup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                });

                Picasso.get().load(item.get("base_url")).into(img);
                nama.setText(item.get("employee_name"));
                nip.setText(item.get("nomor_induk_pegawai"));
                alamat.setText(item.get("address"));
                gender.setText(item.get("gender"));
                tempatLahir.setText(item.get("tempat_lahir"));
                tanggalLahir.setText(item.get("tanggal_lahir"));
                golDarah.setText(item.get("gol_darah"));
                agama.setText(item.get("agama"));
                status.setText(item.get("status_perkawinan"));




                dialogBuilder.setView(dialogView);
                dialogBuilder.setCancelable(false);
                dialogBuilder.show();
            }


        });
    }


    private void getDataKaryawan(final View view){

        HashMap<String,String> apiData = new HashMap<String, String>();
        apiData                        = rest_class.apiSettingLocal();
        AsyncHttpClient client         = new AsyncHttpClient();
        RequestParams params           = new RequestParams();
        String base_url;

        base_url = apiData.get("str_ws_addr") + "/employee";

        client.setBasicAuth(apiData.get("str_ws_user"),apiData.get("str_ws_pass"));
        client.post(base_url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp_content = "";

                try {
                    resp_content = new String(responseBody,"UTF-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try {

                    displayDataKaryawan(view,resp_content);

                } catch (Throwable ekjhij) {
                    Toast.makeText(EmployeeActivity.this, "Koneksi Gagal ! 1", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(EmployeeActivity.this,"Koneksi Gagal ! 2",Toast.LENGTH_LONG).show();
                shimmerLayout.stopShimmerAnimation();
                shimmerLayout.setVisibility(View.GONE);
                wrongView.setVisibility(View.VISIBLE);

            }
        });

    }
    // akhir method getDataKaryawan()

    // awal dari methode displayDataKaryawan()

    private void displayDataKaryawan(View view,String resp_content ){

        try{
            dataKaryawan_arrayList = rest_class.getJsonData(resp_content);

            if (dataKaryawan_arrayList.get(0).get("var_result").equals("1")){

                dataKaryawan_arrayList.remove(0);
                adapter = new ListAdapter(EmployeeActivity.this,dataKaryawan_arrayList,1);
                lvDataKaryawan.setAdapter(adapter);
                shimmerLayout.stopShimmerAnimation();
                shimmerLayout.setVisibility(View.GONE);
                Toast.makeText(EmployeeActivity.this,"Koneksi Berhasil !",Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(EmployeeActivity.this,"Koneksi Gagal ! 3",Toast.LENGTH_LONG).show();
                shimmerLayout.stopShimmerAnimation();
                shimmerLayout.setVisibility(View.GONE);
                wrongView.setVisibility(View.VISIBLE);


            }

        } catch (JSONException e) {
            Toast.makeText(EmployeeActivity.this,"Koneksi Gagal ! 4",Toast.LENGTH_LONG).show();
            shimmerLayout.stopShimmerAnimation();
            shimmerLayout.setVisibility(View.GONE);
            wrongView.setVisibility(View.VISIBLE);

        }
    }


    public void employeeDetailPopUp(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater    inflater       = this.getLayoutInflater();
        View dialogView                 = inflater.inflate(R.layout.lv_office_item,null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        dialogBuilder.show();

    }

}




















