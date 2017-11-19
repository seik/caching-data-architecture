package me.ivmg.cachingdataarchitecture.injections;

import javax.inject.Singleton;

import dagger.Component;
import me.ivmg.cachingdataarchitecture.ui.activities.MainActivity;

/**
 * Created by Iván
 */

@Singleton
@Component(modules = AppModule.class)
public interface ApplicationComponent {
    void inject(MainActivity activity);
}
