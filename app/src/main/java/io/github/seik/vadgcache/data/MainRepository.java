package io.github.seik.vadgcache.data;

import java.util.List;

import io.github.seik.vadgcache.data.datasources.LocalDataSource;
import io.github.seik.vadgcache.data.datasources.RemoteDataSource;
import io.github.seik.vadgcache.models.Repo;
import io.github.seik.vadgcache.models.User;
import io.github.seik.vadgcache.retrofit.GithubAPI;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Iván
 */

public class MainRepository {

    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    public MainRepository(GithubAPI service) {
        this.localDataSource = new LocalDataSource();
        this.remoteDataSource = new RemoteDataSource(service);
    }

    public Flowable<User> getUser(String username) {
        Flowable<User> localObservable = localDataSource.getUser()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());

        Flowable<User> remoteObservable = remoteDataSource.getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return Flowable.concat(localObservable, remoteObservable);
    }

    public Flowable<List<Repo>> getRepos(String username) {
        Flowable<List<Repo>> localObservable = localDataSource.getRepos()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());

        Flowable<List<Repo>> remoteObservable = remoteDataSource.getRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return Flowable.concat(localObservable, remoteObservable);
    }

}
