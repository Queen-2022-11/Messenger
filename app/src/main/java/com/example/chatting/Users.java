package com.example.chatting;

public class Users {
    String profilepic,mail,userName,pass,lastMessage,status,userId;
    public Users() {
        // Default constructor required for Firebase
    }

    public Users(String profilepic, String mail, String userName, String pass, String status, String userId) {
        this.profilepic = profilepic;
        this.mail = mail;
        this.userName = userName;
        this.pass = pass;
        this.status = status;
        this.userId = userId;
    }


    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
