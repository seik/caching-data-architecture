package io.github.seik.vadgcache;

import android.app.Application;

import io.github.seik.vadgcache.injections.AppModule;
import io.github.seik.vadgcache.injections.ApplicationComponent;
import io.github.seik.vadgcache.injections.DaggerApplicationComponent;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Iván
 */

public class App extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm();

        applicationComponent = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    private void initRealm() {
        Realm.init(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
