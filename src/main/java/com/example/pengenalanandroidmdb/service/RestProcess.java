package com.example.pengenalanandroidmdb.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RestProcess {

    // API akan dikonsumsi denan menggunakan asyncronus

    public HashMap apiSettingLocal(){ // fungsi ini berguna untuk menetapkan setting yang diperlukan untuk mengakses api.
        HashMap<String,String> apiData = new HashMap<>();
        apiData.put("str_ws_addr","https://jamlima.multiintifinancialteknologi.co.id:8443/api/Training/"); // link api
        apiData.put("str_ws_user","apitraining"); // username yang digunakan untuk authorization
        apiData.put("str_ws_pass","password");    // password yang digunakan untuk authorization

        return apiData;

    }

    public ArrayList<HashMap<String,String>> getJsonData(String resp_content) throws JSONException {
        // fungsi ini mengambil object JSON , pecah sesuai jesin apakan JSONObject atau JSONArray,
        // lalu ubah ke string, dan masukkan hasilnya ke variable arrayRetrun yang beripe ArrayList

        ArrayList<HashMap<String,String>> arrayReturn = new ArrayList<HashMap<String, String>>(); // object

        JSONObject obj_json = null;                                     //  membuat sebuah object dati Class JSONObject
        int i =0;                                                          //  hanya untuk increament/ indeks aja
        String var_result,var_result_flag,var_message;                  // menampung object json yang telah diubah ke String
        HashMap<String,String> map_gen;                                 // object yang menampung pasangan key-value

        try {
            obj_json        = new JSONObject(resp_content);             // inisiasi object json
            var_result_flag = obj_json.get("var_result").toString();    // ambil object var_result dari file json yang didapat, ubah ke String dan masukkan ke variable  flag
            var_message     = obj_json.get("var_message").toString();   // sama kyk yang diatas
            var_result      = obj_json.get("result").toString();        // sama kyk yang diatas
            map_gen         = new HashMap<String, String>();            // inisiasi object map

            map_gen.put("var_result",var_result_flag);                  //  masukkan data baru yang dengan key var_resutlt dan valuenya diambil dari variable flag
            map_gen.put("var_message",var_message);                     //  sama kyk yang diatas
            arrayReturn.add(map_gen);                                   // tambahkan map_gen ke arrayReturn

            if (var_result_flag.equals("1")){                           // jika didapatkan flag bernilai 1 maka
                JSONArray result_array = new JSONArray(var_result);     // buat object JSONArray dan inisiasi dengan result ( jika flagnya ok atau 1 maka ambil result dan ubah jadi JSONArray

                for (i =0; i < result_array.length(); i++){             // untuk setiap elemen array lakukan hal berikut :
                    JSONObject obj = result_array.getJSONObject(i);     // - buat object dari JSONObject dan inisiasi dengan elemen dari array

                    map_gen = new HashMap<String,String>();             // inisiasi lagi map_gen dengan object HashMap

                    for (int j = 0; j < obj.length(); j++) {            // untuk setiap object yang baru dibuat, lakukan hal berikut:
                        String json_key = obj.names().getString(j);
                        String json_value = obj.getString(json_key);    // ambil value berdasarkan key yang didapat
                        map_gen.put(json_key,json_value);               // masukkan key dan value kedalam object hashmap
                    }

                    arrayReturn.add(map_gen);                           // tambahkan object hashmap kedalam array list
                }
            }

        } catch (JSONException e){
            e.printStackTrace();
        }



        return arrayReturn;                                             // kembalikan arrayList arrayReturn yang berisi kumpulan object HashMap

    }

    // akhir methode getJsonData()

}
















