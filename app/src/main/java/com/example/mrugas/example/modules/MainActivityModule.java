package com.example.mrugas.example.modules;

import com.example.mrugas.example.activities.MainActivity;
import com.example.mrugas.example.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mruga on 20.10.2016.
 */

@Module
public class MainActivityModule {

    private MainActivity activity;

    public MainActivityModule(MainActivity activity) {

        this.activity = activity;

    }

    @Provides
    @ActivityScope
    MainActivity providesMainActivity() {
        return activity;
    }



}