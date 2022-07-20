package com.project.shopping.Model;

public class Comments extends CommentsId{
      private String comment , user, userName, userImage;

    public Comments(String comment, String user, String userName, String userImage) {
        this.comment = comment;
        this.user = user;
        this.userName = userName;
        this.userImage = userImage;
    }

    public Comments() {
    }

    public String getComment() {
        return comment;
    }

    public String getUser() {
        return user;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
