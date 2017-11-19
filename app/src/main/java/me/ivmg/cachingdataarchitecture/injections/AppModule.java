package me.ivmg.cachingdataarchitecture.injections;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.ivmg.cachingdataarchitecture.data.MainRepository;
import me.ivmg.cachingdataarchitecture.retrofit.GithubAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();

        return retrofit.create(GithubAPI.class);
    }

    @Provides
    @Singleton
    MainRepository provideRepository(GithubAPI service) {
        return new MainRepository(service);
    }
}
