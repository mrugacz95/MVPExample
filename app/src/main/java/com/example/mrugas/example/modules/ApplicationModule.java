package com.example.mrugas.example.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mruga on 24.10.2016.
 */
@Module
public class ApplicationModule {
    private Application mApplication;
    public ApplicationModule(Application application){
        mApplication=application;
    }
    @Provides
    @Singleton
    Application providesApplication(){
        return mApplication;
    }
    @Provides
    @Singleton
    RetrofitModule provideRetrofitModule(){
        return new RetrofitModule();
    }
}
