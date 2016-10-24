package com.example.mrugas.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mrugas.example.MyApp;
import com.example.mrugas.example.R;
import com.example.mrugas.example.injection.modules.RetrofitModule;
import com.example.mrugas.example.models.GitHubUser;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mruga on 24.10.2016.
 */
@FragmentWithArgs
public class GitHubUserFragment extends BaseFragment {
    @Inject
    RetrofitModule.GitHubApi gitHubApi;
    @Arg
    public String user;
    GitHubUser gitHubUser;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.main)
    RelativeLayout mainLayout;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_url)
    TextView tvUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        ((MyApp) getActivity().getApplication()).getNetComponent().inject(this);
        FragmentArgs.inject(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Call<GitHubUser> gitHubCall = gitHubApi.getUser(user);
        gitHubCall.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                if(response.body()!=null)
                    gitHubUser = response.body();
                    loadData();
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {
                    Log.d("Failure",t.getMessage());
                    Snackbar.make(mainLayout, "Failure: " + t.getMessage(),Snackbar.LENGTH_LONG).show();
            }
        });
    }
    private void loadData(){
        Glide.with(mContext).load(gitHubUser.getAvatarUrl()).into(ivAvatar);
        tvEmail.setText(gitHubUser.getEmail());
        tvId.setText(String.valueOf(gitHubUser.getId()));
        tvLocation.setText(gitHubUser.getLocation());
        tvName.setText(gitHubUser.getName());
        tvLogin.setText(gitHubUser.getUsername());
        tvUrl.setText( Html.fromHtml("<a href=\""+gitHubUser.getHtmlUrl()+"\">"+gitHubUser.getHtmlUrl()+"</a>"));
        tvUrl.setMovementMethod(LinkMovementMethod.getInstance());
        tvType.setText(gitHubUser.getType());
    }
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_github_user;
    }

    @Override
    protected void resolveDependencies() {
        ((MyApp) getActivity().getApplication()).getNetComponent().inject(this);
    }
}
