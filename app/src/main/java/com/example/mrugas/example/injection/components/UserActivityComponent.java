package com.example.mrugas.example.injection.components;

import com.example.mrugas.example.activities.UserActivity;
import com.example.mrugas.example.injection.modules.UserActivityModule;
import com.example.mrugas.example.injection.scopes.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by mruga on 02.11.2016.
 */
@ActivityScope
@Subcomponent( modules = UserActivityModule.class )
public interface UserActivityComponent {
    UserActivity inject(UserActivity activity);
}
