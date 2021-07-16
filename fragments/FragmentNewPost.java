package com.example.tristagram.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
 * Use the {@link FragmentNewPost#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNewPost extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String IP_SERVIDOR = "192.168.100.31";
    private static final String API_NEW_POST ="http://"+IP_SERVIDOR+"/tristagramwebservice/source/prepareds/newpost.php?";
    private static final int CAMERA_REQUEST_CODE = 1001;
    private static final int GALLERY_REQUEST_CODE = 123;
    private static String photoB64;
    RequestQueue requestQueue;
    private  Context context;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView newPhoto;
    private ImageButton btnGallery;
    private ImageButton btnCamera;
    private Button btnPost;
    private EditText txtTitle;
    private View view;


    public FragmentNewPost() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNewPost.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNewPost newInstance(String param1, String param2) {
        FragmentNewPost fragment = new FragmentNewPost();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_new_post, container, false);

        newPhoto = (ImageView) view.findViewById(R.id.frg3_ivNewPhoto);
        btnGallery = (ImageButton) view.findViewById(R.id.frg3_ibGallery);
        btnCamera = (ImageButton)view.findViewById(R.id.frg3_ibCamera);
        btnPost = (Button) view.findViewById(R.id.frg3_btnSend);
        txtTitle = (EditText) view.findViewById(R.id.frg3_txtTitle);

        btnPost.setEnabled(false);


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

        //GALERIA -----------------------------------------------------------------------------------------------------------------
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            //activa el boton actualizar
            btnPost.setEnabled(true);

            Uri image= data.getData();

            try{
                InputStream is = getActivity().getContentResolver().openInputStream(image);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                Glide.with(this)
                        .asBitmap()
                        .load(bitmap)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(newPhoto);


                btnPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        StringRequest stringRequest = new StringRequest(
                                Request.Method.POST,
                                API_NEW_POST,
                                new Response.Listener<String>(){

                                    @Override
                                    public void onResponse(String response) {
                                        if(response.trim().equalsIgnoreCase("yes")){
                                            Toast.makeText(context,"enviada",Toast.LENGTH_LONG).show();
                                            transactionProfile();
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
                                photoB64 =bitmapTobase64(bitmap);
                                String title = txtTitle.getText().toString();
                                String imageName = rLocalDateTime();

                                Map<String,String> imageMap = new HashMap<>();
                                imageMap.put("image", photoB64);
                                imageMap.put("idUser",id);
                                imageMap.put("imagePath",imageName);
                                imageMap.put("title", title);

                                return imageMap;
                            }
                        };
                        requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(stringRequest);
                        //transactionProfile();******************************************************************************

                    }
                });

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        //CAMARA ------------------------------------------------------------------------------------------------------
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            btnPost.setEnabled(true);

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            Glide.with(this)
                    .asBitmap()
                    .load(bitmap)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(newPhoto);


            btnPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            API_NEW_POST,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.trim().equalsIgnoreCase("yes")){
                                        Toast.makeText(context,"enviada",Toast.LENGTH_LONG).show();
                                        transactionProfile();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();

                                }
                            }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            photoB64 =bitmapTobase64(bitmap);
                            String title = txtTitle.getText().toString();
                            String imageName = rLocalDateTime();

                            Map<String,String> imageMap = new HashMap<String, String>();
                            imageMap.put("image", photoB64);
                            imageMap.put("idUser",id);
                            imageMap.put("imagePath",imageName);
                            imageMap.put("title", title);

                            return imageMap;
                        }
                    };
                    requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);
                    //transactionProfile();***********************************************************
                }
            });
        }

    }
    public void transactionProfile(){
        Thread tr2 = new Thread(new Runnable() {
            @Override
            public void run() {

                FragmentTransaction fragmentTransaction;
                FragmentPpal fragmentPpal = new FragmentPpal();

                fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fcv_Fragment_container, fragmentPpal);
                fragmentTransaction.commit();

            }
        });
        tr2.start();
    }

    public static String  bitmapTobase64(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] bitmapConvertido = baos.toByteArray();
        String encoded = Base64.encodeToString(bitmapConvertido,Base64.DEFAULT);
        return encoded;
    }
}