package io.github.seik.vadgcache.ui.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.seik.vadgcache.App;
import io.github.seik.vadgcache.R;
import io.github.seik.vadgcache.adapters.MainAdapter;
import io.github.seik.vadgcache.data.MainRepository;
import io.github.seik.vadgcache.models.User;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.parentLayout)
    RelativeLayout parentLayout;

    @Inject
    MainRepository mainRepository;

    CompositeSubscription subscriptions = new CompositeSubscription();

    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

        adapter = new MainAdapter(this, new User(), new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        Subscription userSubscription = mainRepository.getUser("seik").subscribe(user -> {

            adapter.setUser(user);
            adapter.notifyDataSetChanged();

        }, throwable -> {

            Log.d("ErrorDataLoading", throwable.getMessage());
            Snackbar.make(parentLayout, getString(R.string.error),
                    Snackbar.LENGTH_SHORT).show();

        });

        Subscription reposSubscription = mainRepository.getRepos("seik").subscribe(repos -> {

            adapter.setRepos(repos);
            adapter.notifyDataSetChanged();

        }, throwable -> {
            Log.d("ErrorDataLoading", throwable.getMessage());
            Snackbar.make(parentLayout, getString(R.string.error),
                    Snackbar.LENGTH_SHORT).show();
        });

        subscriptions.addAll(userSubscription, reposSubscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
    }
}
