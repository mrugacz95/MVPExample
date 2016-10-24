package com.example.mrugas.example.components;

import com.example.mrugas.example.activities.MainActivity;
import com.example.mrugas.example.modules.ApplicationModule;
import com.example.mrugas.example.modules.RetrofitModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mruga on 24.10.2016.
 */

@Singleton
@Component(modules = {ApplicationModule.class, RetrofitModule.class})
public interface NetComponent {
    void inject(MainActivity activity);
}
