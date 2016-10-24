package com.example.mrugas.example;

import android.app.Application;

import com.example.mrugas.example.injection.components.DaggerNetComponent;
import com.example.mrugas.example.injection.components.NetComponent;
import com.example.mrugas.example.injection.modules.ApplicationModule;
import com.example.mrugas.example.injection.modules.RetrofitModule;

/**
 * Created by mruga on 24.10.2016.
 */

public class MyApp extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();


        // Dagger%COMPONENT_NAME%
        mNetComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .applicationModule(new ApplicationModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .retrofitModule(new RetrofitModule())
                .build();

        // If a Dagger 2 component does not have any constructor arguments for any of its modules,
        // then we can use .create() as a shortcut instead:
        //  mNetComponent = com.codepath.dagger.components.DaggerNetComponent.create();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
