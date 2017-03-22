package io.github.seik.vadgcache.injections;

import javax.inject.Singleton;

import dagger.Component;
import io.github.seik.vadgcache.ui.activities.MainActivity;

/**
 * Created by Iván
 */

@Singleton
@Component(modules = AppModule.class)
public interface ApplicationComponent {
    void inject(MainActivity activity);
}
