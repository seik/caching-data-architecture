package io.github.seik.vadgcache.retrofit;

import java.util.List;

import io.github.seik.vadgcache.models.Repo;
import io.github.seik.vadgcache.models.User;
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
