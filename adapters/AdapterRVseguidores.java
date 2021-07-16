package com.example.tristagram.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tristagram.R;
import com.example.tristagram.pojo.Seguidos;

import java.util.ArrayList;

import static com.example.tristagram.connections.HttpUrlConnections.dejarDeSeguir;
import static com.example.tristagram.connections.HttpUrlConnections.empezarASeguir;

/**
 * Adaptador del RV seguidores
 */
public class AdapterRVseguidores extends RecyclerView.Adapter<AdapterRVseguidores.ViewHolderRVseguidores>{

    //recibe una lista de fotos cargada con bucle desde la BD para inflar el RV
    private ArrayList<Seguidos> listaSeguidos;
    private Context mycontext;

    // es obligatorio crear un constructor para que funcione
    public AdapterRVseguidores(ArrayList<Seguidos> listaSeguidos, Context context) {
        this.listaSeguidos = listaSeguidos;
        this.mycontext=context;
    }

    @NonNull
    @Override
    public ViewHolderRVseguidores onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objeto_rv_seguidores,parent,false);
        return new ViewHolderRVseguidores(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRVseguidores.ViewHolderRVseguidores holder, int position) {

        holder.user.setText(listaSeguidos.get(position).getUser());

        if(listaSeguidos.get(position).isSeguido()){
            holder.btSeguir.setText("SEGUIDO");
        }else{
            holder.btSeguir.setText("SEGUIR");
        }


        //Aqui le paso el contexto a glide y carga las fotos desde la lista de URLs
        Glide.with(mycontext)
                .load(listaSeguidos.get(position).getProfileImage())
                .into(holder.fotoPerfil);

        holder.btSeguir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(listaSeguidos.get(position).isSeguido()){
                    dejarDeSeguir(listaSeguidos.get(position).getIdUser(),listaSeguidos.get(position).getId_seguido());
                    listaSeguidos.get(position).setSeguido(false);
                    holder.btSeguir.setText("SEGUIR");
                }else{
                    empezarASeguir(listaSeguidos.get(position).getIdUser(),listaSeguidos.get(position).getId_seguido());
                    listaSeguidos.get(position).setSeguido(true);
                    holder.btSeguir.setText("SEGUIDO");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaSeguidos.size();
    }

    public static class ViewHolderRVseguidores extends RecyclerView.ViewHolder {
        TextView user;
        ImageView fotoPerfil;
        Button btSeguir;


        public ViewHolderRVseguidores(@NonNull View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.txtUserRv2);
            fotoPerfil = itemView.findViewById(R.id.ivProfile);
            btSeguir=itemView.findViewById(R.id.btSeguir);
        }
    }
}
