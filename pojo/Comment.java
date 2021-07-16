package com.example.tristagram.pojo;

public class Comment {private Integer id;
    private Integer id_image;
    private Integer id_user;
    private String commentary;

    public Comment() {
        super();
    }

    public Comment(Integer id, Integer id_image, Integer id_user, String commentary) {
        super();
        this.id = id;
        this.id_image = id_image;
        this.id_user = id_user;
        this.commentary = commentary;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId_image() {
        return id_image;
    }
    public void setId_image(Integer id_image) {
        this.id_image = id_image;
    }
    public Integer getId_user() {
        return id_user;
    }
    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }
    public String getCommentary() {
        return commentary;
    }
    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }
    @Override
    public String toString() {
        return "Comment [id=" + id + ", id_image=" + id_image + ", id_user=" + id_user + ", commentary=" + commentary
                + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((commentary == null) ? 0 : commentary.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((id_image == null) ? 0 : id_image.hashCode());
        result = prime * result + ((id_user == null) ? 0 : id_user.hashCode());
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
        Comment other = (Comment) obj;
        if (commentary == null) {
            if (other.commentary != null)
                return false;
        } else if (!commentary.equals(other.commentary))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (id_image == null) {
            if (other.id_image != null)
                return false;
        } else if (!id_image.equals(other.id_image))
            return false;
        if (id_user == null) {
            if (other.id_user != null)
                return false;
        } else if (!id_user.equals(other.id_user))
            return false;
        return true;
    }


}
