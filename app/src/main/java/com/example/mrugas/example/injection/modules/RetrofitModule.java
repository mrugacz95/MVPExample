package com.example.mrugas.example.injection.modules;

import android.util.Log;

import com.example.mrugas.example.events.ConnectionErrorEvent;
import com.example.mrugas.example.models.DailyMotionUser;
import com.example.mrugas.example.models.DailyMotionUsersList;
import com.example.mrugas.example.models.GitHubUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mruga on 20.10.2016.
 */
@Module
public class RetrofitModule {

    private static final String DALIYMOTION_URL = "https://api.dailymotion.com/";
    private static final String GITHUB_URL = "https://api.github.com/";

    @Provides
    @Singleton
    GitHubApi provideGitHubApi(Gson gson, OkHttpClient httpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
        return retrofit.create(GitHubApi.class);
    }
    @Provides
    @Singleton
    DailyMotionApi provideDailyMotionApi(Gson gson, OkHttpClient httpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DALIYMOTION_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
        return retrofit.create(DailyMotionApi.class);
    }
    @Provides
    @Singleton
    Gson provideGson(){
        return new GsonBuilder().disableHtmlEscaping()
                .create();
    }
    @Provides
    @Singleton
    OkHttpClient provideHttpClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor connectionInterceptor = new Interceptor() {
            @Override
            public Response intercept(final Interceptor.Chain chain) throws IOException {
                try {
                    return chain.proceed(chain.request());
                } catch (IOException e) {
                    Log.d("Connection Error", "Zaszło nieporozumienie, nie ma pan internetu :c -> HubaBuba");
                    EventBus.getDefault().postSticky(new ConnectionErrorEvent(e.toString()));
                    throw e;
                }

            }
        };
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(connectionInterceptor)
                .build();
    }


    public interface GitHubApi {
        @GET("/users")
        Call<List<GitHubUser>> getUsers();
        @GET("/users/{user}")
        Call<GitHubUser> getUser(@Path("user") String user);
    }
    public interface DailyMotionApi{
        @GET("/users")
        Call<DailyMotionUsersList> getUsers(@Query("fields") String fields);
        @GET("/user/{user}")
        Call<DailyMotionUser> getUser(@Path("user") String user,
                                      @Query("fields") String fields);
    }

}

