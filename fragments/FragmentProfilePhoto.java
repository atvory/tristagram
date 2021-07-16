package com.example.tristagram.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.tristagram.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.example.tristagram.connections.HttpUrlConnections.rLocalDateTime;
import static com.example.tristagram.connections.HttpUrlConnections.sendDataGETprofilephoto;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfilePhoto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfilePhoto extends Fragment {
    ImageView profilePhoto;
    private static final int GALLERY_REQUEST_CODE = 123;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static String encoded;
    View view;
    Button btnEnviar;
    ImageButton btnGallery;
    ImageButton btnCamera;
    Context context;
    String profileP;
    private static final int CAMERA_REQUEST_CODE = 1001;
    private static final String IP_SERVIDOR = "192.168.100.31";
    private static final String url= "http://"+IP_SERVIDOR+"/tristagramwebservice/source/prepareds/returnpp.php";
    private static final String API_NEW_PP ="http://"+IP_SERVIDOR+"/tristagramwebservice/source/prepareds/newpp.php?";

    private RequestQueue requestQueue; // <--- volley


    public FragmentProfilePhoto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfilePhoto.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfilePhoto newInstance(String param1, String param2) {
        FragmentProfilePhoto fragment = new FragmentProfilePhoto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //btnEnviar.setEnabled(false);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_photo, container, false);
        btnEnviar = view.findViewById(R.id.frg2_btnOk);
        btnGallery = view.findViewById(R.id.frg2_btnGallery);
        btnCamera = view.findViewById(R.id.frg2_btnCamera);
        btnEnviar.setEnabled(false);
        profileP = getActivity().getIntent().getStringExtra("profile"); // <-------- asi o recibiendo el bundle
        profilePhoto = (ImageView) view.findViewById(R.id.frg2_ivProfile);
        context=getContext();



        Glide.with(getContext())
                .load(profileP)
                .circleCrop()
                .into(profilePhoto);


        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,""), GALLERY_REQUEST_CODE);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String id = getActivity().getIntent().getStringExtra("id");

        //GALERIA --------------------------------------------------------------------------------
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            //activa el boton actualizar
            btnEnviar.setEnabled(true);

            Uri image= data.getData();

            try{
                InputStream is = getActivity().getContentResolver().openInputStream(image);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                Glide.with(this)
                        .asBitmap()
                        .load(bitmap)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .circleCrop()
                        .into(profilePhoto);


                btnEnviar.setOnClickListener(new View.OnClickListener() {
                    String rutaNewImage;
                    @Override
                    public void onClick(View v) {


                        String imageName = rLocalDateTime();
                        StringRequest stringRequest = new StringRequest(
                                Request.Method.POST,
                                API_NEW_PP,
                                new Response.Listener<String>(){

                                    @Override
                                    public void onResponse(String response) {
                                        if(response.trim().equalsIgnoreCase("yes")){
                                            Toast.makeText(getContext(),"enviada",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener(){
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }
                        ){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String,String> imageMap = new HashMap<String, String>();
                                imageMap.put("image",bitmapTobase64(bitmap));
                                imageMap.put("id",id);
                                imageMap.put("name",imageName);

                                return imageMap;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        requestQueue.add(stringRequest);
                        transactionProfile();
                    }
                });

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        //CAMARA ----------------------------------------------------------------------------------
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            btnEnviar.setEnabled(true);

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            Glide.with(this)
                    .asBitmap()
                    .load(bitmap)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .circleCrop()
                    .into(profilePhoto);


            btnEnviar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String imageName = rLocalDateTime();
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            API_NEW_PP,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.trim().equalsIgnoreCase("yes")){

                                    Toast.makeText(getContext(),"enviada",Toast.LENGTH_LONG).show();
                                   }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();

                                }
                            }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {


                            Map<String,String> imageMap = new HashMap<String, String>();
                            imageMap.put("image", bitmapTobase64(bitmap));
                            imageMap.put("id",id);
                            imageMap.put("name",imageName);

                            return imageMap;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);
                    transactionProfile();
                }
            });
        }
    }


    public void transactionProfile(){
        FragmentPpal fragmentPpal= new FragmentPpal();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fcv_Fragment_container, fragmentPpal).commit();
    }

    public static String bitmapTobase64(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] bitmapConvertido = baos.toByteArray();
        encoded = Base64.encodeToString(bitmapConvertido,Base64.DEFAULT);
        return encoded;
    }

}
