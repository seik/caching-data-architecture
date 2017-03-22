package io.github.seik.vadgcache.retrofit;

import java.util.List;

import io.github.seik.vadgcache.models.Repo;
import io.github.seik.vadgcache.models.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Iván
 */

public interface GithubAPI {

    @GET("users/{user}")
    Observable<User> getUser(@Path("user") String user);

    @GET("users/{user}/repos")
    Observable<List<Repo>> getRepos(@Path("user") String user);

}
