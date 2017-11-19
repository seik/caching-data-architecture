package io.github.seik.vadgcache.data.datasources;

import java.util.List;

import io.github.seik.vadgcache.models.Repo;
import io.github.seik.vadgcache.models.User;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Ivan
 */

public class LocalDataSource {

    private Realm realm;

    public LocalDataSource() {
        this.realm = Realm.getDefaultInstance();
    }

    public Flowable<User> getUser() {
        User user = realm.where(User.class).findFirst();

        return (user != null) ? user.asFlowable() : Flowable.empty();
    }

    public Flowable<List<Repo>> getRepos() {
        RealmResults<Repo> repoResults = realm.where(Repo.class).findAll();

        return (!repoResults.isEmpty()) ?
                repoResults.asFlowable().map(repos -> repos) :
                Flowable.empty();
    }
}
