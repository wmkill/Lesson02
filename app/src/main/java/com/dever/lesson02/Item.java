package com.dever.lesson02;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2015/12/29.
 */
public class Item {
    private long id;
    private String userIcon;
    private String userName;
    private String content;
    private String image;

    public Item(JSONObject object) throws JSONException {

        if (!object.isNull("user")) {
            userIcon = object.getJSONObject("user").getString("icon");
            userName = object.getJSONObject("user").getString("login");
            id = object.getJSONObject("user").getLong("id");
        }
        content = object.getString("content");
        if(!object.isNull("image")){
            image = object.getString("image");
        }
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
