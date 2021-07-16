package com.example.tristagram.pojo;

import java.sql.Blob;


public class Image {
    private Integer id;
    private String tittle;
    private Integer id_user;
    private String image;

    public Image() {
        super();
    }

    public Image(Integer id, String tittle, Integer id_user, String image) {
        super();
        this.id = id;
        this.tittle = tittle;
        this.id_user = id_user;
        this.image = image;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTittle() {
        return tittle;
    }
    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
    public Integer getId_user() {
        return id_user;
    }
    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    @Override
    public String toString() {
        return "Image [id=" + id + ", tittle=" + tittle + ", id_user=" + id_user + ", image=" + image + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((id_user == null) ? 0 : id_user.hashCode());
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        result = prime * result + ((tittle == null) ? 0 : tittle.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Image other = (Image) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (id_user == null) {
            if (other.id_user != null)
                return false;
        } else if (!id_user.equals(other.id_user))
            return false;
        if (image == null) {
            if (other.image != null)
                return false;
        } else if (!image.equals(other.image))
            return false;
        if (tittle == null) {
            if (other.tittle != null)
                return false;
        } else if (!tittle.equals(other.tittle))
            return false;
        return true;
    }


}