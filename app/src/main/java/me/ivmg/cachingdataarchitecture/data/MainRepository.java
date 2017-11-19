package me.ivmg.cachingdataarchitecture.data;

import java.util.List;

import me.ivmg.cachingdataarchitecture.models.User;
import me.ivmg.cachingdataarchitecture.retrofit.GithubAPI;
import me.ivmg.cachingdataarchitecture.data.datasources.LocalDataSource;
import me.ivmg.cachingdataarchitecture.data.datasources.RemoteDataSource;
import me.ivmg.cachingdataarchitecture.models.Repo;
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
