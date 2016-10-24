package com.example.mrugas.example.modules;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.mrugas.example.events.ConnectionErrorEvent;
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

/**
 * Created by mruga on 20.10.2016.
 */
@Module
public class RetrofitModule {

    public static final String DALIYMOTION_URL = "https://api.dailymotion.com/";
    public static final String GITHUB_URL = "https://api.github.com/";

    @Provides
    @Singleton
    public GitHubApi provideGitHubApi(Gson gson, OkHttpClient httpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
        return retrofit.create(GitHubApi.class);
    }
    @Provides
    @Singleton
    public DailyMotionApi provideDailyMotionApi(Gson gson, OkHttpClient httpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DALIYMOTION_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
        return retrofit.create(DailyMotionApi.class);
    }
    @Provides
    @Singleton
    public Gson provideGson(){
        return new GsonBuilder().disableHtmlEscaping()
                .create();
    }
    @Provides
    @Singleton
    public OkHttpClient provideHttpClient() {

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
    }
    public interface DailyMotionApi{
        @GET("/users")
        Call<DailyMotionUsersList> getUsers(@Field("fileds") String fileds);
    }

}

