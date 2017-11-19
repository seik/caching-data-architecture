package me.ivmg.cachingdataarchitecture.data.datasources;

import java.util.List;

import me.ivmg.cachingdataarchitecture.models.User;
import me.ivmg.cachingdataarchitecture.models.Repo;
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
