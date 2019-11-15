package com.example.pengenalanandroidmdb.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pengenalanandroidmdb.R;

public class IzinActivity extends AppCompatActivity {
    EditText etNama, etNip, etJabatan, etTgl, etAlasan;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izin);
        setTitle("Form Izin");

        etNama = findViewById(R.id.etNama);
        etNip = findViewById(R.id.etNip);
        etJabatan = findViewById(R.id.etJabatan);
        etTgl = findViewById(R.id.etTgl);
        etAlasan = findViewById(R.id.etAlasan);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = etNama.getText().toString();
                String nip = etNip.getText().toString();
                String jabatan = etJabatan.getText().toString();
                String tgl = etTgl.getText().toString();
                String alasan = etAlasan.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(IzinActivity.this);
                builder.setTitle("Surat Izin " + nama);
                builder.setMessage("\n\nKepada Yth, \nHR PT. Multi Inti Digital Bisnis \n\nSaya yang bertanda tangan di bawah ini: \n" +
                        "Nama: " + nama + "\nNIP: " + nip + "\nJabatan: " + jabatan + "\nNIP: " + nip + "\n\n" +
                                "Bahwa pada hari " + tgl + " tidak masuk kerja karena " + alasan + ".\n\nDemikian" +
                        "surat izin kerja ini saya buat dengan sebagaimana mestinya. Atas perhatian Bapak/Ibu, saya ucapkan " +
                                "terima kasih\n\nHormat saya, \n\n\n\n" + nama);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        etNama.getText().clear();
                        etNip.getText().clear();
                        etJabatan.getText().clear();
                        etTgl.getText().clear();
                        etAlasan.getText().clear();
                        Toast.makeText(IzinActivity.this,"Surat Izin Dikirim ke HRD",Toast.LENGTH_LONG).show();
                    }
                });

                builder.create();
                builder.show();
            }
        });


    }
}
