package com.example.tristagram.pojo;

import java.util.Objects;

public class Users {
    private Integer id;
    private String user;
    private String password;
    private String email;
    private String profileImage;

    public Users() {}
    //public Users(String user) {this.user = user;}
   public Users(Integer id, String user, String profileImage) {
        this.id = id;
        this.user = user;
        this.profileImage = profileImage;
    }
    public Users (String email,String password){
        this.email = email;
        this.password = password;
    }

    public Users(Integer id, String user, String password, String email, String profileImage) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.email = email;
        this.profileImage = profileImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users user1 = (Users) o;
        return Objects.equals(id, user1.id) &&
                Objects.equals(user, user1.user) &&
                password.equals(user1.password) &&
                email.equals(user1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", profileImage=" + profileImage +
                '}';
    }
}
