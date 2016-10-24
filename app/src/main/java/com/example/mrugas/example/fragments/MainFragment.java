package com.example.mrugas.example.fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrugas.example.MyApp;
import com.example.mrugas.example.R;
import com.example.mrugas.example.adapters.UserAdapter;
import com.example.mrugas.example.models.DailyMotionUsersList;
import com.example.mrugas.example.models.GitHubUser;
import com.example.mrugas.example.injection.modules.RetrofitModule;
import com.example.mrugas.example.models.User;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mruga on 24.10.2016.
 */

public class MainFragment extends Fragment {

    @BindView(R.id.rv_data)
    RecyclerView recyclerView;
    @Inject
    RetrofitModule.GitHubApi gitHubApi;
    @Inject
    RetrofitModule.DailyMotionApi dailyMotionApi;
    @Inject
    Application application;
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MyApp)getActivity().getApplication()).getNetComponent().inject(this);
        mContext= getContext();
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new UserAdapter(mContext));
        Call<List<GitHubUser>> call = gitHubApi.getUsers();
        call.enqueue(new Callback<List<GitHubUser>>() {
            @Override
            public void onResponse(Call<List<GitHubUser>> call, Response<List<GitHubUser>> response) {
                if(response.body()!=null)
                    ((UserAdapter)recyclerView.getAdapter()).addGitHubUsers(response.body());
                else
                    Log.d("Error",response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<List<GitHubUser>> call, Throwable t) {

            }
        });
        Call<DailyMotionUsersList> dailyMotionCall = dailyMotionApi.getUsers("avatar_360_url,username");
        dailyMotionCall.enqueue(new Callback<DailyMotionUsersList>() {
            @Override
            public void onResponse(Call<DailyMotionUsersList> call, Response<DailyMotionUsersList> response) {
                if(response.body()!=null)
                    ((UserAdapter)recyclerView.getAdapter()).addDailyMotionUsers(response.body().getList());
                else
                    Log.d("Error",response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<DailyMotionUsersList> call, Throwable t) {

            }
        });
        return view;
    }
}
