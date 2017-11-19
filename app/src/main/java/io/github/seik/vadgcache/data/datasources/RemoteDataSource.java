package io.github.seik.vadgcache.data.datasources;

import java.util.List;

import io.github.seik.vadgcache.data.StorageHandler;
import io.github.seik.vadgcache.models.Repo;
import io.github.seik.vadgcache.models.User;
import io.github.seik.vadgcache.retrofit.GithubAPI;
import io.reactivex.Flowable;

/**
 * Created by Ivan
 */

public class RemoteDataSource {

    GithubAPI service;

    public RemoteDataSource(GithubAPI service) {
        this.service = service;
    }

    public Flowable<User> getUser(String username) {
        return service.getUser(username)
                .doOnNext(StorageHandler::saveObject);
    }

    public Flowable<List<Repo>> getRepos(String username) {
        return service.getRepos(username)
                .doAfterNext(StorageHandler::saveObjects);
    }
}
