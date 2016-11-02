package com.example.mrugas.example.injection.components;

import android.app.Application;

import com.example.mrugas.example.injection.modules.ApplicationModule;
import com.example.mrugas.example.injection.modules.UserActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mruga on 02.11.2016.
 */
@Singleton
@Component( modules = ApplicationModule.class)
public interface ApplicationComponent {
    Application application();
    UserActivityComponent getUserComponent(UserActivityModule module);
}
