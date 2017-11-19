package me.ivmg.cachingdataarchitecture.retrofit;

import java.util.List;

import me.ivmg.cachingdataarchitecture.models.User;
import me.ivmg.cachingdataarchitecture.models.Repo;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Iván
 */

public interface GithubAPI {

    @GET("users/{user}")
    Flowable<User> getUser(@Path("user") String user);

    @GET("users/{user}/repos")
    Flowable<List<Repo>> getRepos(@Path("user") String user);

}
