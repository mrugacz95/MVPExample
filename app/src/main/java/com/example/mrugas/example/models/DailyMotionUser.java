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

        /**
         *
         * @return
         * The avatar360Url
         */
        public String getAvatar360Url() {
            return avatar360Url;
        }

        /**
         *
         * @param avatar360Url
         * The avatar_360_url
         */
        public void setAvatar360Url(String avatar360Url) {
            this.avatar360Url = avatar360Url;
        }

        /**
         *
         * @return
         * The username
         */
        public String getUsername() {
            return username;
        }

        /**
         *
         * @param username
         * The username
         */
        public void setUsername(String username) {
            this.username = username;
        }


        @Override
        public String getAvatarUrl() {
                return avatar360Url;
        }
}
