package com.example.mrugas.example.presenters;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.example.mrugas.example.MyApp;
import com.example.mrugas.example.injection.modules.RetrofitModule;
import com.example.mrugas.example.interfaces.MVP_User;
import com.example.mrugas.example.models.DailyMotionUser;
import com.example.mrugas.example.models.GitHubUser;
import com.example.mrugas.example.models.User;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mrugas.example.activities.UserActivity.DAILYMOTION;
import static com.example.mrugas.example.activities.UserActivity.GITHUB;

/**
 * Created by mruga on 01.11.2016.
 */

public class UserPresenter implements MVP_User.PresenterOps {
    static final String FIELDS ="city,country,id,url,gender,avatar_360_url,created_time,username,status,twitter_url";
    User model;
    private WeakReference<MVP_User.ViewOps> mView;
    String userName;
    @Inject
    RetrofitModule.DailyMotionApi dailyMotionApi;
    @Inject
    RetrofitModule.GitHubApi gitHubApi;
    public UserPresenter(MVP_User.ViewOps view){
        mView = new WeakReference<>(view);
    }

    @Override
    public void onScreenCreated(String type, String userName){
        ((MyApp) mView.get().getAppContext().getApplicationContext()).getNetComponent().inject(this);
        this.userName= userName;
        switch (type){
            default:
            case GITHUB:
                getGitHubData();
                break;
            case DAILYMOTION:
                getDailyMotionData();
                break;
        }
    }

    @Override
    public void setModel(GitHubUser model) {
        this.model = model;
    }

    @Override
    public MVP_User.ViewOps getView() {
        if(mView!=null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    private void getGitHubData(){

        Call<GitHubUser> gitHubCall = gitHubApi.getUser(userName);
        gitHubCall.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                if(response.body()!=null)
                    mView.get().showData(response.body());
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {
                Log.d("Failure",t.getMessage());
                mView.get().showSnackbar("Failure: " + t.getMessage());
            }
        });
    }
    private void getDailyMotionData(){
        Call<DailyMotionUser> gitHubCall = dailyMotionApi.getUser(userName,FIELDS);
        gitHubCall.enqueue(new Callback<DailyMotionUser>() {
            @Override
            public void onResponse(Call<DailyMotionUser> call, Response<DailyMotionUser> response) {
                if(response.body()!=null)
                    mView.get().showData(response.body());
            }

            @Override
            public void onFailure(Call<DailyMotionUser> call, Throwable t) {
                Log.d("Failure",t.getMessage());
                mView.get().showSnackbar("Failure: " + t.getMessage());
            }
        });
    }
}
