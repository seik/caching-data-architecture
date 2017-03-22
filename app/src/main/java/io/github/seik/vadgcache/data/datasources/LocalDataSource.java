package io.github.seik.vadgcache.data.datasources;

import java.util.List;

import io.github.seik.vadgcache.models.Repo;
import io.github.seik.vadgcache.models.User;
import io.realm.Realm;
import rx.Observable;

/**
 * Created by Ivan
 */

public class LocalDataSource {

    private Realm realm;


    public LocalDataSource() {
        this.realm = Realm.getDefaultInstance();
    }


    public Observable<User> getUser() {
        User user = realm.where(User.class).findFirst();

        return (user != null) ? user.asObservable() : Observable.empty();
    }

    public Observable<List<Repo>> getRepos() {
        List<Repo> repos = realm.where(Repo.class).findAll();

        return (!repos.isEmpty()) ? Observable.just(repos) : Observable.empty();
    }

}
