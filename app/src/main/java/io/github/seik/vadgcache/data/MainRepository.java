package io.github.seik.vadgcache.data;

import java.util.List;

import io.github.seik.vadgcache.data.datasources.LocalDataSource;
import io.github.seik.vadgcache.data.datasources.RemoteDataSource;
import io.github.seik.vadgcache.models.Repo;
import io.github.seik.vadgcache.models.User;
import io.github.seik.vadgcache.retrofit.GithubAPI;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    public Observable<User> getUser(String username) {

        Observable<User> localObservable = localDataSource.getUser()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());


        Observable<User> remoteObservable = remoteDataSource.getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        return Observable.concat(localObservable, remoteObservable);
    }

    public Observable<List<Repo>> getRepos(String username) {
        Observable<List<Repo>> localObservable = localDataSource.getRepos()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<List<Repo>> remoteObservable = remoteDataSource.getRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return Observable.concat(localObservable, remoteObservable);
    }

}
