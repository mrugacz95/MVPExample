package com.example.mrugas.example.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mrugas.example.MyApp;
import com.example.mrugas.example.R;
import com.example.mrugas.example.models.GitHubUser;
import com.example.mrugas.example.modules.RetrofitModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_data)
    RecyclerView recyclerView;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @Inject
    RetrofitModule.GitHubApi gitHubApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((MyApp) getApplication()).getNetComponent().inject(this);
        if(gitHubApi == null)
            tvResult.setText("Failed");
        else{
            Call<List<GitHubUser>> call = gitHubApi.getUsers();
            call.enqueue(new Callback<List<GitHubUser>>() {
                @Override
                public void onResponse(Call<List<GitHubUser>> call, Response<List<GitHubUser>> response) {
                    List<GitHubUser> gitHubUsers = response.body();
                    int l = gitHubUsers.size();
                    Log.d("ilosc uzytkownikow", String.valueOf(l));
                    tvResult.setText("Done, first user:" + gitHubUsers.get(0).getLogin());
                }

                @Override
                public void onFailure(Call<List<GitHubUser>> call, Throwable t) {

                }
            });
        }

    }
}
