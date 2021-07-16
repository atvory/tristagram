package com.example.tristagram.pojo;

import java.util.Objects;

public class Seguidos {
    private String user;
    private String profileImage;
    private int id_seguido;
    private int idUser;
    private boolean seguido;

    public Seguidos() {
    }
    //public Users(String user) {this.user = user;}

    public Seguidos(String user, String profileImage, int id_seguido, int idUser, boolean seguido) {
        this.user = user;
        this.profileImage = profileImage;
        this.id_seguido = id_seguido;
        this.idUser = idUser;
        this.seguido = seguido;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getId_seguido() {
        return id_seguido;
    }

    public void setId_seguido(int id_seguido) {
        this.id_seguido = id_seguido;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean isSeguido() {
        return seguido;
    }

    public void setSeguido(boolean seguido) {
        this.seguido = seguido;
    }
}