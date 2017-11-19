package me.ivmg.cachingdataarchitecture;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.ivmg.cachingdataarchitecture.injections.AppModule;
import me.ivmg.cachingdataarchitecture.injections.ApplicationComponent;
import me.ivmg.cachingdataarchitecture.injections.DaggerApplicationComponent;

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
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
