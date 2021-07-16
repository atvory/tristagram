package com.example.tristagram.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tristagram.R;
import com.example.tristagram.pojo.FotoMuro;

import java.util.ArrayList;

/**
 * Adaptador del RV del muro
 */
public class AdapterRVmuro extends RecyclerView.Adapter<AdapterRVmuro.ViewHolderRVmuro>{

    //recibe una lista de fotos cargada con bucle desde la BD para inflar el RV
    private ArrayList<FotoMuro> listaFotos;
    private Context mycontext;

    // es obligatorio crear un constructor para que funcione
    public AdapterRVmuro(ArrayList<FotoMuro> listaFotos, Context context) {
        this.listaFotos = listaFotos;
        this.mycontext=context;
    }

    @NonNull
    @Override
    public ViewHolderRVmuro onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objeto_rv_muro,parent,false);
        return new ViewHolderRVmuro(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRVmuro.ViewHolderRVmuro holder, int position) {
        holder.user.setText(listaFotos.get(position).getUserSeguido());
        holder.likes.setText(listaFotos.get(position).getLikes().toString());


        //Aqui le paso el contexto a glide y carga las fotos desde la lista de URLs
        Glide.with(mycontext)
                .load(listaFotos.get(position).getRutaImagen())
                .into(holder.foto);

        Glide.with(mycontext)
                .load(listaFotos.get(position).getImagenPerfil())
                .into(holder.fotoPerfil);


        //likes
        holder.corazon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int x=listaFotos.get(position).getLikes();
                holder.likes.setText((x+1)+"");
                holder.corazon.setBackgroundColor(Color.WHITE);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listaFotos.size();
    }

    public static class ViewHolderRVmuro extends RecyclerView.ViewHolder {
        TextView user;
        ImageView foto;
        ImageView fotoPerfil;
        TextView likes;
        ImageButton corazon;

        public ViewHolderRVmuro(@NonNull View itemView) {
            super(itemView);

            foto = itemView.findViewById(R.id.ivFotoRv2);
            fotoPerfil= itemView.findViewById(R.id.ivProfile);
            user = itemView.findViewById(R.id.txtUserRv2);
            fotoPerfil = itemView.findViewById(R.id.ivProfile);
            likes = itemView.findViewById(R.id.txtLikesRv2);
            corazon=itemView.findViewById(R.id.ibCorazon);
        }
    }
}
