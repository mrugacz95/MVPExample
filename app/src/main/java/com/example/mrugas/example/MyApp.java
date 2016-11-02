package com.example.mrugas.example;

import android.app.Application;

import com.example.mrugas.example.injection.components.ApplicationComponent;
import com.example.mrugas.example.injection.components.DaggerApplicationComponent;
import com.example.mrugas.example.injection.components.DaggerNetComponent;
import com.example.mrugas.example.injection.components.NetComponent;
import com.example.mrugas.example.injection.modules.ApplicationModule;
import com.example.mrugas.example.injection.modules.RetrofitModule;

/**
 * Created by mruga on 24.10.2016.
 */

public class MyApp extends Application {

    private NetComponent mNetComponent;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initNetComponent();
        initAppComponent();



    }
    private void initNetComponent(){
        mNetComponent = DaggerNetComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .retrofitModule(new RetrofitModule())
                .build();
    }
    private void initAppComponent(){
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public ApplicationComponent getmApplicationComponent() {
        return mApplicationComponent;
    }
}
