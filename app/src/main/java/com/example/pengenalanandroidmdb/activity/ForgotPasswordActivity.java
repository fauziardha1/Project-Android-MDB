package com.example.pengenalanandroidmdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pengenalanandroidmdb.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextView inputNewPass,inputConfirmNewPass;
    private Button ubahPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().hide();

        inputNewPass = findViewById(R.id.editText_new_password);
        inputConfirmNewPass = findViewById(R.id.editText_konfirmasi_new_password);
        ubahPass     = findViewById(R.id.btn_ubah_password);


        ubahPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputNewPass.getText().toString().length() != 4)
                    inputNewPass.setError("It Must be 4 Numbers");
                else
                    if (inputConfirmNewPass.getText().toString().length() != 4)
                        inputConfirmNewPass.setError("It Must be 4 Numbers");
                else
                    if (!inputConfirmNewPass.getText().toString().equals(inputNewPass
                    .getText().toString()))
                        inputConfirmNewPass.setError("This should to be same as new password!");
                else {
                        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                        Toast.makeText(getApplicationContext(), "Password Berhasil Diperbarui ^^", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}
