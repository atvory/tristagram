package com.example.tristagram.pojo;

public class FotoMuro {
    private String rutaImagen;
    private String imagenPerfil;
    private Integer likes;
    private String userSeguido;


    public FotoMuro(String rutaImage, String imagenPerfil, Integer likes, String userSeguido) {
        super();
        this.rutaImagen = rutaImage;
        this.imagenPerfil = imagenPerfil;
        this.likes = likes;
        this.userSeguido = userSeguido;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getUserSeguido() {
        return userSeguido;
    }

    public void setUserSeguido(String userSeguido) {
        this.userSeguido = userSeguido;
    }
}