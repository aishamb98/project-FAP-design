package com.project.shopping.Model;

import java.util.Date;

public class Post extends PostId {

    private String image , user , caption, userName, userImage;
    private Date time;

    public String getImage() {
        return image;
    }

    public String getUser() {
        return user;
    }

    public String getCaption() {
        return caption;
    }

    public Date getTime() {
        return time;
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

    public void setImage(String image) {
        this.image = image;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
