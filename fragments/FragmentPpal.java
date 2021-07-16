package com.example.tristagram.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.tristagram.Objetos.Foto;
import com.example.tristagram.R;
import com.example.tristagram.activities.Muro;
import com.example.tristagram.activities.Seguidores;
import com.example.tristagram.adapters.AdapterRVperfil;
import com.example.tristagram.pojo.Image;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.tristagram.connections.HttpUrlConnections.checkJSONtransaction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPpal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPpal extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView rv;
    ArrayList<Image> listaFotos;
    AdapterRVperfil adaptadorRV;
    ImageButton btnGrid;
    ImageButton btnList;
    ImageButton btnExplorar;
    ImageButton btnNewPhoto;
    ImageButton btnSeguidores;
    TextView txtUser;
    ImageView profilePhoto;
    String user;
    View view;
    ImageButton btnChangeProfile;
    Context context;


    public FragmentPpal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPpal.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPpal newInstance(String param1, String param2) {
        FragmentPpal fragment = new FragmentPpal();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_ppal, container, false); // para acceder al findviewbyid se tiene que inflar la vista antes

        rv = (RecyclerView) view.findViewById(R.id.frg_rvFotos2);
        listaFotos = new ArrayList<>();
        rv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));



        adaptadorRV = new AdapterRVperfil(listaFotos,getContext());
        rv.setAdapter(adaptadorRV);

        btnGrid = (ImageButton) view.findViewById(R.id.frg_ibGrid);
        btnList = (ImageButton) view.findViewById(R.id.frg_ibLista);
        btnSeguidores= (ImageButton) view.findViewById(R.id.frg_ibSeguidores);
        btnExplorar = (ImageButton) view.findViewById(R.id.frg_ibExplore);
        btnChangeProfile = view.findViewById(R.id.frg_btnChangeProfile);
        btnNewPhoto = view.findViewById(R.id.frg_ibNewPhoto);
        context=getContext();


        txtUser = (TextView) view.findViewById(R.id.frg_txtUserProfile);
        user = getActivity().getIntent().getStringExtra("user");
        txtUser.setText(user);
        String id = getActivity().getIntent().getStringExtra("id");

        cargaFotosBD(user);

        String profileP = getActivity().getIntent().getStringExtra("profile");
        profilePhoto = (ImageView) view.findViewById(R.id.frg_ivProfile);

        Glide.with(this)
                .load(profileP)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .circleCrop()
                .into(profilePhoto);


        Bundle bundle;
        if ((bundle = this.getArguments()) != null) {
            String data = bundle.getString("newProfile");
            Toast.makeText(getContext(), "BIEN", Toast.LENGTH_LONG);
            String newProfile = checkJSONtransaction(data);
            Glide.with(this)
                    .load(newProfile)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .circleCrop()
                    .into(profilePhoto);
        }


        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapListaV(rv,adaptadorRV);
            }
        });

        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapGrid(rv,adaptadorRV);
            }
        });



        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction;
                fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fcv_Fragment_container, new FragmentProfilePhoto());
                fragmentTransaction.commit();

                Toast.makeText(getContext(), "A cambiar la foto", Toast.LENGTH_LONG).show();
            }
        });

        btnNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                FragmentTransaction fragmentTransaction;
                FragmentNewPost fragmentNewPost = new FragmentNewPost();
                fragmentNewPost.setArguments(bundle);
                fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fcv_Fragment_container,new FragmentNewPost());
                fragmentTransaction.commit();
            }
        });
        //BOTON seguidores--------------------
        btnSeguidores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSeguidores =new Intent(context, Seguidores.class);
                intentSeguidores.putExtra("id",id);
                startActivity(intentSeguidores);

            }
        });



        btnExplorar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMuro = new Intent(getContext(), Muro.class);
                intentMuro.putExtra("user", user);                                                                //paso el user
                intentMuro.putExtra("rutaImagenPerfil",getActivity().getIntent().getStringExtra("profile"));//y la imagen del perfil
                startActivity(intentMuro);
            }
        });

        return view;
    }




    public void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
    }




    //carga de fotos crea un thread para pedir las fotos a la db
    private void cargaFotosBD(String user) {
        Thread tr =new Thread(){
            public void run(){
                final String answer = sendDataGET(user);
                checkJSON(answer);
            }
        };
        tr.start();
    }


    public String sendDataGET(String user){

        String linea = "";
        int answer = 0;
        StringBuilder result = null;
        try {
            URL url = new URL("http://192.168.100.31/tristagramwebservice/source/prepareds/fotosPerfil.php?user="+user);
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

        }catch(Exception e){}

        return result.toString();
    }


    public void checkJSON(String answer) {
        int id;
        String tittle;
        int id_user;
        String pathImage="";
        try {
            JSONArray json = new JSONArray(answer);
            //creo objetos Imagen y los añado a la listaImagenes
            for (int i = 0; i < json.length(); i++) {
                id=json.getJSONObject(i).getInt("id");
                tittle=json.getJSONObject(i).getString("title");
                id_user=json.getJSONObject(i).getInt("id_user");
                pathImage = json.getJSONObject(i).getString("rutaImage");
                listaFotos.add(new Image(id,tittle,id_user,pathImage));
            }
            //actualiza el adaptador del Recycler
            rv.setAdapter(adaptadorRV);

        } catch (Exception e) {

        }
    }

    private void swapGrid(RecyclerView rv, AdapterRVperfil adapter){
        rv.setLayoutManager(new GridLayoutManager(getContext(),2));
        adaptadorRV = new AdapterRVperfil(listaFotos, getContext());
        rv.setAdapter(adaptadorRV);
    }

    private void swapListaV(RecyclerView rv, AdapterRVperfil adapter){
        rv.setLayoutManager(new GridLayoutManager(getContext(),1));
        adaptadorRV = new AdapterRVperfil(listaFotos, getContext());
        rv.setAdapter(adaptadorRV);
    }


/*// este no lo he borrado de momento
    private void cargaFotos() {
        listaFotos.add(new Foto(R.drawable.cargarv));
        listaFotos.add(new Foto(R.drawable.cargarv));
        listaFotos.add(new Foto(R.drawable.cargarv));
        listaFotos.add(new Foto(R.drawable.cargarv));
        listaFotos.add(new Foto(R.drawable.cargarv));
        listaFotos.add(new Foto(R.drawable.cargarv));
        listaFotos.add(new Foto(R.drawable.cargarv));
        listaFotos.add(new Foto(R.drawable.cargarv));
        listaFotos.add(new Foto(R.drawable.cargarv));
        listaFotos.add(new Foto(R.drawable.cargarv));
    }*/


}