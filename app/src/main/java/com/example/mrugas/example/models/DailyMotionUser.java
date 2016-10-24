package com.example.mrugas.example.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by mruga on 24.10.2016.
 */

@Generated("org.jsonschema2pojo")
public class DailyMotionUser implements User {

    @SerializedName("avatar_360_url")
    @Expose
    private String avatar360Url;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("created_time")
    @Expose
    private Integer createdTime;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("twitter_url")
    @Expose
    private String twitterUrl;

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public Integer getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Integer createdTime) {
        this.createdTime = createdTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    /**
     * @return The avatar360Url
     */
    public String getAvatar360Url() {
        return avatar360Url;
    }

    /**
     * @param avatar360Url The avatar_360_url
     */
    public void setAvatar360Url(String avatar360Url) {
        this.avatar360Url = avatar360Url;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String getAvatarUrl() {
        return avatar360Url;
    }

    public String getStatus() {
        return status;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }
}
