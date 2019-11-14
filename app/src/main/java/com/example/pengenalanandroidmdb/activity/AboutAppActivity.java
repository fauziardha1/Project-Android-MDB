package com.example.pengenalanandroidmdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.pengenalanandroidmdb.R;
import com.example.pengenalanandroidmdb.service.RestProcess;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class AboutAppActivity extends AppCompatActivity {

    private WebView webView ;

    //API process
    private RestProcess rest_class;

    // Declare ArrayList
    ArrayList<HashMap<String,String>> dataWebViewArrayList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        setTitle("About");

        rest_class = new RestProcess();

        webView = findViewById(R.id.webView_about);
        getDataAbout(null);
    }

    private void getDataAbout(final View view){

        HashMap<String,String> apiData = new HashMap<String, String>();
        apiData                        = rest_class.apiSettingLocal();
        AsyncHttpClient client         = new AsyncHttpClient();
//        RequestParams params           = new RequestParams();
        String base_url;

        base_url = apiData.get("str_ws_addr") + "/about_apps";

        client.setBasicAuth(apiData.get("str_ws_user"),apiData.get("str_ws_pass"));
        client.post(base_url, new AsyncHttpResponseHandler() {
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
                    Toast.makeText(getApplicationContext(), "Koneksi Gagal ! 1", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(getApplicationContext(),"Koneksi Gagal ! 2",Toast.LENGTH_LONG).show();

            }
        });

    }
    // akhir method getDataKaryawan()

    // awal dari methode displayDataKaryawan()

    private void displayDataKaryawan(View view,String resp_content ){

        try{
            dataWebViewArrayList = rest_class.getJsonData(resp_content);

            if (dataWebViewArrayList.get(0).get("var_result").equals("1")){

                String aboutText = dataWebViewArrayList.get(1).get("about_apps");
                System.out.println(aboutText);
                webView.loadData(aboutText,"text/html",null);

                Toast.makeText(getApplicationContext(),"Koneksi Berhasil !",Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(),"Koneksi Gagal ! 3",Toast.LENGTH_LONG).show();

            }

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),"Koneksi Gagal ! 4",Toast.LENGTH_LONG).show();
        }
    }



}
