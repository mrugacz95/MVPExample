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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mrugas.example.MyApp;
import com.example.mrugas.example.R;
import com.example.mrugas.example.activities.DetailsActivity;
import com.example.mrugas.example.injection.modules.RetrofitModule;
import com.example.mrugas.example.models.DailyMotionUser;
import com.example.mrugas.example.models.GitHubUser;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mrugas.example.activities.DetailsActivity.DAILYMOTION;
import static com.example.mrugas.example.activities.DetailsActivity.GITHUB;

/**
 * Created by mruga on 24.10.2016.
 */
@FragmentWithArgs
public class UserFragment extends BaseFragment {
    @Inject
    RetrofitModule.GitHubApi gitHubApi;
    @Inject
    RetrofitModule.DailyMotionApi dailyMotionApi;
    static final String FIELDS ="city,country,id,url,gender,avatar_360_url,created_time,username,status,twitter_url";
    @Arg
    public String user;
    @Arg
    public String type;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
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
    void getGitHubData(){
        Call<GitHubUser> gitHubCall = gitHubApi.getUser(user);
        gitHubCall.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                if(response.body()!=null)
                    loadData(response.body());
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {
                Log.d("Failure",t.getMessage());
                Snackbar.make(mainLayout, "Failure: " + t.getMessage(),Snackbar.LENGTH_LONG).show();
            }
        });
    }
    void getDailyMotionData(){
        Call<DailyMotionUser> gitHubCall = dailyMotionApi.getUser(user,FIELDS);
        gitHubCall.enqueue(new Callback<DailyMotionUser>() {
            @Override
            public void onResponse(Call<DailyMotionUser> call, Response<DailyMotionUser> response) {
                if(response.body()!=null)
                    loadData(response.body());
            }

            @Override
            public void onFailure(Call<DailyMotionUser> call, Throwable t) {
                Log.d("Failure",t.getMessage());
                Snackbar.make(mainLayout, "Failure: " + t.getMessage(),Snackbar.LENGTH_LONG).show();
            }
        });
    }
    private void loadData(GitHubUser user){
        Glide.with(mContext).load(user.getAvatarUrl()).into(ivAvatar);
        tvEmail.setText(user.getEmail());
        tvId.setText(String.valueOf(user.getId()));
        if(user.getLocation().isEmpty() || user.getLocation().equals(""))
            tvLocation.setText(mContext.getString(R.string.no_data));
        else
            tvLocation.setText(user.getLocation());
        tvLogin.setText(user.getUsername());
        if(user.getHtmlUrl()==null || user.getHtmlUrl().equals("null"))
            tvUrl.setText(mContext.getString(R.string.no_data));
        else {
            tvUrl.setText(Html.fromHtml("<a href=\"" + user.getHtmlUrl() + "\">" + user.getHtmlUrl() + "</a>"));
            tvUrl.setMovementMethod(LinkMovementMethod.getInstance());
        }
        tvType.setText(user.getType());
    }
    private void loadData(DailyMotionUser user){
        Glide.with(mContext).load(user.getAvatarUrl()).into(ivAvatar);
        if(user.getUrl()==null || user.getUrl().equals("null")) {
            tvEmail.setText(Html.fromHtml("<a href=\"" + user.getTwitterUrl() + "\">" + user.getTwitterUrl() + "</a>"));
            tvEmail.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else {
            tvEmail.setText(mContext.getString(R.string.no_data));
        }
        tvId.setText(user.getId());
        if(user.getCity().isEmpty() || user.getCity().equals(""))
            tvLocation.setText(mContext.getString(R.string.no_data));
        else
            tvLocation.setText(user.getCity());
        tvLogin.setText(user.getUsername());
        tvUrl.setText(mContext.getString(R.string.no_data));
        tvUrl.setText(Html.fromHtml("<a href=\"" + user.getUrl() + "\">" + user.getUrl() + "</a>"));
        tvUrl.setMovementMethod(LinkMovementMethod.getInstance());
        tvType.setText(user.getStatus());
    }
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user;
    }

    @Override
    protected void resolveDependencies() {
        ((MyApp) getActivity().getApplication()).getNetComponent().inject(this);
    }
}
