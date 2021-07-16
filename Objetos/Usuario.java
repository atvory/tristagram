package com.example.tristagram.Objetos;

import java.util.ArrayList;

public class Usuario {
    //Faltaria crear un id para concurrencia
    private String nom_user;
    private String password;
    private String email;
    private Foto foto;
    private ArrayList<Foto> listaFotos;

    public Usuario() {}

    //constructor prueba de carga
    public Usuario(String nom_user, ArrayList<Foto>listaFotos){
        this.nom_user = nom_user;
        this.listaFotos = listaFotos;
    }
    public Usuario(String nom_user,Foto foto, ArrayList<Foto>listaFotos){
        this.nom_user = nom_user;
        this.foto = foto;
        this.listaFotos = listaFotos;
    }
    public Usuario(String nom_user, String password, String email, Foto foto, ArrayList<Foto> listaFotos) {
        this.nom_user = nom_user;
        this.password = password;
        this.email = email;
        this.foto = foto;
        this.listaFotos = listaFotos;
    }

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public ArrayList<Foto> getListaFotos() {
        return listaFotos;
    }

    public void setListaFotos(ArrayList<Foto> listaFotos) {
        this.listaFotos = listaFotos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nom_user='" + nom_user + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", foto=" + foto +
                ", listaFotos=" + listaFotos +
                '}';
    }
}
