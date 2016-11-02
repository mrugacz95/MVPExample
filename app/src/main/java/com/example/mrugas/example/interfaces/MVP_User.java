package com.example.mrugas.example.interfaces;

import android.content.Context;

import com.example.mrugas.example.models.DailyMotionUser;
import com.example.mrugas.example.models.GitHubUser;

/**
 * Created by mruga on 02.11.2016.
 */

public interface MVP_User {

    interface ViewOps{
        Context getAppContext();
        void showData(GitHubUser gitHubUser);
        void showData(DailyMotionUser dailyMotionUser);
        void showSnackbar(String text);
    }

    interface PresenterOps{
        void onScreenCreated(String type, String user);
        void setModel(GitHubUser model);
        ViewOps getView();
    }

    interface ModelOps{
        Context loadData();
    }
}
