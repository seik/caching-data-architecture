package me.ivmg.cachingdataarchitecture.data.datasources;

import java.util.List;

import me.ivmg.cachingdataarchitecture.data.StorageHandler;
import me.ivmg.cachingdataarchitecture.models.User;
import me.ivmg.cachingdataarchitecture.retrofit.GithubAPI;
import me.ivmg.cachingdataarchitecture.models.Repo;
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
