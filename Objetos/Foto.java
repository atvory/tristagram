package com.example.tristagram.Objetos;

/**
 * Para enviar las fotos al recycler creo que llega con enviar un AL de integers, pero como me
 * comentaste que para recuperar las imagenes de la BD hay que usar un casteo extranho, he creado
 * la propia clase foto y si acaso modificamos el atributo.
 */
public class Foto {
    private int imagen;
    private int likes;

    public Foto(){}
    // constructor para las fotos de perfil
    public Foto(int imagen) {
        this.imagen = imagen;
    }
    public Foto(int imagen, int likes) {
        this.imagen = imagen;
        this.likes = likes;
    }

    public int getImagen() {
        return imagen;
    }
    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }

}
