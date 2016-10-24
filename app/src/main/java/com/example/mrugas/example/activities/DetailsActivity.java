package com.example.mrugas.example.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.mrugas.example.R;
import com.example.mrugas.example.fragments.BaseFragment;
import com.example.mrugas.example.fragments.GitHubUserFragment;
import com.example.mrugas.example.fragments.GitHubUserFragmentBuilder;
import com.example.mrugas.example.fragments.MainFragment;

/**
 * Created by mruga on 24.10.2016.
 */

public class DetailsActivity extends AppCompatActivity {
    public static final String USER = "USER";
    public static final String TYPE = "TYPE";
    public static final String GITHUB = "GITHUB";
    public static final String DAILYMOTION = "DAILYMOTION";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment mainFragment = new MainFragment();
        String user = getIntent().getStringExtra(USER);
        String type = getIntent().getStringExtra(TYPE);
        BaseFragment fragment;
        switch (type){
            default:
            case GITHUB:
                fragment = new GitHubUserFragmentBuilder(user).build();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
                actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
