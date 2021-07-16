package com.example.tristagram.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.example.tristagram.R;
import com.example.tristagram.fragments.*;

public class Principal extends AppCompatActivity {

    // se declaran los fragments
    FragmentPpal fragmentPpal;
    FragmentProfilePhoto fragmentProfilePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // se les crea una instancia
        fragmentPpal = new FragmentPpal();
        fragmentProfilePhoto = new FragmentProfilePhoto();
        //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // se los setea en el fragment conainer, el fragment container peta en api 27 si no se le ponen las dependencias
        getSupportFragmentManager().beginTransaction().add(R.id.fcv_Fragment_container, fragmentPpal).commit();
        //String id = getIntent().getStringExtra("id");
    }


}