package com.example.mrugas.example.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mrugas.example.MyApp;
import com.example.mrugas.example.R;
import com.example.mrugas.example.injection.modules.RetrofitModule;
import com.example.mrugas.example.injection.modules.UserActivityModule;
import com.example.mrugas.example.interfaces.MVP_User;
import com.example.mrugas.example.models.DailyMotionUser;
import com.example.mrugas.example.models.GitHubUser;
import com.example.mrugas.example.models.User;
import com.example.mrugas.example.presenters.UserPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mruga on 24.10.2016.
 */

public class UserActivity extends AppCompatActivity implements MVP_User.ViewOps {
    public static final String USER = "USER";
    public static final String TYPE = "TYPE";
    public static final String GITHUB = "GITHUB";
    public static final String DAILYMOTION = "DAILYMOTION";
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
    @Inject
    MVP_User.PresenterOps mPresenter;
    String user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        setupComponent();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
                actionBar.setDisplayHomeAsUpEnabled(true);
        user = getIntent().getStringExtra(USER);
        String type = getIntent().getStringExtra(TYPE);
        mPresenter.onScreenCreated(type, user);
    }
    private void setupComponent(){
        ((MyApp) getApplication()).getmApplicationComponent().getUserComponent(new UserActivityModule(this)).inject(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getAppContext() {
        return this;
    }

    @Override
    public void showData(GitHubUser user) {
        Glide.with(this).load(user.getAvatarUrl()).into(ivAvatar);
        tvEmail.setText(user.getEmail());
        tvId.setText(String.valueOf(user.getId()));
        if(user.getLocation()== null || user.getLocation().isEmpty() || user.getLocation().equals(""))
            tvLocation.setText(this.getString(R.string.no_data));
        else
            tvLocation.setText(user.getLocation());
        tvLogin.setText(user.getUsername());
        if(user.getHtmlUrl()==null || user.getHtmlUrl().equals("null"))
            tvUrl.setText(this.getString(R.string.no_data));
        else {
            tvUrl.setText(Html.fromHtml("<a href=\"" + user.getHtmlUrl() + "\">" + user.getHtmlUrl() + "</a>"));
            tvUrl.setMovementMethod(LinkMovementMethod.getInstance());
        }
        tvType.setText(user.getType());
    }

    @Override
    public void showData(DailyMotionUser user) {
        Glide.with(this).load(user.getAvatarUrl()).into(ivAvatar);
        if(user.getUrl()==null || user.getUrl().equals("null")) {
            tvEmail.setText(Html.fromHtml("<a href=\"" + user.getTwitterUrl() + "\">" + user.getTwitterUrl() + "</a>"));
            tvEmail.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else {
            tvEmail.setText(this.getString(R.string.no_data));
        }
        tvId.setText(user.getId());
        if(user.getCity().isEmpty() || user.getCity().equals(""))
            tvLocation.setText(this.getString(R.string.no_data));
        else
            tvLocation.setText(user.getCity());
        tvLogin.setText(user.getUsername());
        tvUrl.setText(this.getString(R.string.no_data));
        tvUrl.setText(Html.fromHtml("<a href=\"" + user.getUrl() + "\">" + user.getUrl() + "</a>"));
        tvUrl.setMovementMethod(LinkMovementMethod.getInstance());
        tvType.setText(user.getStatus());
    }

    @Override
    public void showSnackbar(String text) {
        Snackbar.make(mainLayout, text, Snackbar.LENGTH_LONG).show();
    }
}
