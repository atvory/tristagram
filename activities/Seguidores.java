package com.example.tristagram.activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tristagram.R;
import com.example.tristagram.adapters.AdapterRVseguidores;
import com.example.tristagram.pojo.Seguidos;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Seguidores extends AppCompatActivity {
    AdapterRVseguidores adapterRVseguidores;
    RecyclerView rv;
    ArrayList<Seguidos>listaSeguidos = new ArrayList<>();
    int id;
    String pathImage="";
    String userSeguido="";
    int id_seguido=0;
    Button btBuscar;
    EditText txBuscar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguidores);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        id=Integer.parseInt(getIntent().getStringExtra("id"));
        btBuscar=findViewById(R.id.btBuscar);
        txBuscar=findViewById(R.id.txBuscar);

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargaBuscados(txBuscar.getText().toString());
                rv.setAdapter(adapterRVseguidores);
            }
        });



        cargaSeguidos();
        adapterRVseguidores=new AdapterRVseguidores(listaSeguidos,this);
        rv= findViewById(R.id.rvSeguidores);
        rv.setAdapter(adapterRVseguidores);
        rv.setLayoutManager(new LinearLayoutManager(this));


    }




    //Thread para cargar Seguidos
    private void cargaSeguidos() {
        Thread tr = new Thread(() -> {
            final String answer = sendDataSeguidos();
            checkJSONSeguidos(answer);
        });
        tr.start();
    }
    private String sendDataSeguidos() {
        String linea="";
        int answer=0;
        StringBuilder result =null;
        URL url = null;
        try {
            url = new URL("http://192.168.100.31/tristagramwebservice/source/prepareds/seguidos.php?id="+id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            answer = con.getResponseCode();
            result = new StringBuilder();

            if(answer==200){
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while((linea = br.readLine())!=null){
                    result.append(linea);
                }
                is.close();
                br.close();
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private void checkJSONSeguidos(String answer) {
        try {
            JSONArray json = new JSONArray(answer);
            for(int i =0; i<json.length();i++){
                pathImage=json.getJSONObject(i).getString("profile_image_path");
                userSeguido=json.getJSONObject(i).getString("user");
                id_seguido=json.getJSONObject(i).getInt("id_seguido");
                listaSeguidos.add(new Seguidos(userSeguido,pathImage,id_seguido,id,true));
            }
            rv.setAdapter(adapterRVseguidores);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //Thread para cargar buscados-------------------------------------------------------------------
    private void cargaBuscados(String buscado) {
        Thread tr = new Thread(() -> {
            final String answer = sendDataBuscados(buscado);
            checkJSONBuscados(answer);
        });
        tr.start();
    }
    private String sendDataBuscados(String buscado){
        String linea="";
        int answer=0;
        StringBuilder result =null;
        URL url = null;
        try {
            url = new URL("http://192.168.100.31/tristagramwebservice/source/prepareds/buscar.php?buscado="+buscado+"%");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            answer = con.getResponseCode();
            result = new StringBuilder();

            if(answer==200){
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while((linea = br.readLine())!=null){
                    result.append(linea);
                }
                is.close();
                br.close();
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
    private void  checkJSONBuscados(String answer){
        try {
            JSONArray json = new JSONArray(answer);
            listaSeguidos.clear();
            for(int i =0; i<json.length();i++){
                pathImage=json.getJSONObject(i).getString("profile_image_path");
                userSeguido=json.getJSONObject(i).getString("user");
                id_seguido=json.getJSONObject(i).getInt("id");
                listaSeguidos.add(new Seguidos(userSeguido,pathImage,id_seguido,id,false));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}