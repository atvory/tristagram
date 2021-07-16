package com.example.tristagram.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tristagram.Objetos.Foto;
import com.example.tristagram.R;
import com.example.tristagram.pojo.Image;

import java.util.ArrayList;

/**
 * adaptador del recycler view de fotos del perfil principal
 */
public class AdapterRVperfil extends RecyclerView.Adapter<AdapterRVperfil.ViewHolderRVperfil>{

    //recibe una lista de Image cargada con bucle desde la BD para inflar el RV
    private ArrayList<Image> listaFotos;
    private Context myContext;

    // es obligatorio crear un constructor para que funcione
    public AdapterRVperfil(ArrayList<Image> listaFotos, Context context) {
        this.listaFotos = listaFotos;
        this.myContext= context;
    }

    @NonNull
    @Override
    public ViewHolderRVperfil onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objeto_rv,parent,false);
        return new ViewHolderRVperfil(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRVperfil.ViewHolderRVperfil holder, int position) {
        holder.titulo.setText(listaFotos.get(position).getTittle());

        //Aqui le pasamos el contexto a glide y carga las fotos desde la lista de URLs
        Glide.with(myContext)
                .load(listaFotos.get(position).getImage())
                .into(holder.foto);
    }



    @Override
    public int getItemCount() {
        return listaFotos.size();
    }

    public static class ViewHolderRVperfil extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView titulo;
        public ViewHolderRVperfil(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.ivFoto);
            titulo=itemView.findViewById(R.id.textView3);
        }
    }
}
