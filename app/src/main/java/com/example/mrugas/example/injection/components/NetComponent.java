package com.example.mrugas.example.injection.components;

import com.example.mrugas.example.fragments.UserFragment;
import com.example.mrugas.example.fragments.MainFragment;
import com.example.mrugas.example.injection.modules.ApplicationModule;
import com.example.mrugas.example.injection.modules.RetrofitModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mruga on 24.10.2016.
 */

@Singleton
@Component(modules = {ApplicationModule.class, RetrofitModule.class})
public interface NetComponent {
    void inject(MainFragment fragment);
    void inject(UserFragment fragment);
}
