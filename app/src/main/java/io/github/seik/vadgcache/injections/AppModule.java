package io.github.seik.vadgcache.injections;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.seik.vadgcache.data.MainRepository;
import io.github.seik.vadgcache.retrofit.GithubAPI;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Iván
 */

@Module
public class AppModule {

    String baseUrl = "https://api.github.com/";

    private Application application;


    public AppModule(Application application) {
        this.application = application;
    }


    @Provides
    @Singleton
    GithubAPI provideGitHubApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(GithubAPI.class);
    }

    @Provides
    @Singleton
    MainRepository provideRepository(GithubAPI service) {
        return new MainRepository(service);
    }
}
