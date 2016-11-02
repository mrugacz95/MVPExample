package com.example.mrugas.example.injection.modules;

import com.example.mrugas.example.activities.UserActivity;
import com.example.mrugas.example.injection.scopes.ActivityScope;
import com.example.mrugas.example.interfaces.MVP_User;
import com.example.mrugas.example.models.GitHubUser;
import com.example.mrugas.example.presenters.UserPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mruga on 02.11.2016.
 */
@Module
public class UserActivityModule {
    private UserActivity activity;

    public UserActivityModule(UserActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    UserActivity providesUserActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    MVP_User.PresenterOps presenterOps() {
        UserPresenter presenter = new UserPresenter( activity );
        GitHubUser model = new GitHubUser();
        presenter.setModel( model );
        return presenter;
    }
}
