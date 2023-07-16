package com.example.evoting;

public class UserModel {
    private int id;
    private String username;
    private String password;
    private String phone;
    private boolean hasVoted;

    public UserModel(){

    }

    public UserModel(String username, String password, String phone, boolean hasVoted) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.hasVoted = hasVoted;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone=" + phone +
                ", hasVoted=" + hasVoted +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }


}
