package com.example.tristagram.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tristagram.adapters.AdapterRVmuro;
import com.example.tristagram.R;
import com.example.tristagram.pojo.FotoMuro;

import org.json.JSONArray;

import static com.example.tristagram.connections.HttpUrlConnections.sendDataMuro;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Muro extends AppCompatActivity {
    ImageView fotoPerfil;
    TextView tituloUser;
    ArrayList<FotoMuro> fotosMuro=new ArrayList<>();
    AdapterRVmuro adapterRVmuro;
    RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muro);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //foto de perfil
        fotoPerfil=findViewById(R.id.ivProfile);

        Glide.with(this)
                .load(getIntent().getStringExtra("rutaImagenPerfil"))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .circleCrop()
                .into(fotoPerfil);

        //nombre de usuario
        tituloUser=(TextView) findViewById(R.id.txtUserProfile);
        tituloUser.setText(getIntent().getStringExtra("user"));

        //recycler
        rv = findViewById(R.id.rvFotos2);

        cargaFotosMuro();

        adapterRVmuro = new AdapterRVmuro(fotosMuro,this);
        rv.setAdapter(adapterRVmuro);
    }

    private void cargaFotosMuro(){
        Thread tr = new Thread(() -> {
            final String answer = sendDataMuro(getIntent().getStringExtra("user"));
            checkJSONMuro(answer);
        });
        tr.start();
    }




    public void checkJSONMuro(String answer) {

        String pathImage="";
        String imagenPerfil="";
        int likes;
        String userSeguido;

        try {
            JSONArray json = new JSONArray(answer);
            //creo objetos Imagen y los a??ado a la fotosMuro
            for (int i = 0; i < json.length(); i++) {
                pathImage=json.getJSONObject(i).getString("rutaImage");
                imagenPerfil=json.getJSONObject(i).getString("profile_image_path");
                likes=json.getJSONObject(i).getInt("likes");
                userSeguido=json.getJSONObject(i).getString("user");
                fotosMuro.add(new FotoMuro(pathImage,imagenPerfil,likes,userSeguido));
            }
            //actualiza el adaptador del Recycler
            //pero antes de actualizar metere la entrada final motivadora
            FotoMuro ultima =dameMotivacion();
            fotosMuro.add(fotosMuro.size(),ultima);

            rv.setAdapter(adapterRVmuro);

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    private FotoMuro dameMotivacion() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 9);
        FotoMuro fotoMuro=new FotoMuro("http://192.168.100.31/tristagramimages/motivacion"+randomNum+
                ".jpg","http://192.168.100.31/tristagramimages/tristagram.jpg",0,"Tristagram");
        return fotoMuro;
    }


}