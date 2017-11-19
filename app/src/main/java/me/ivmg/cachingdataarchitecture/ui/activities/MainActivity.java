package me.ivmg.cachingdataarchitecture.ui.activities;

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
import io.github.seik.cachingdataarchitecture.R;
import me.ivmg.cachingdataarchitecture.App;
import me.ivmg.cachingdataarchitecture.data.MainRepository;
import me.ivmg.cachingdataarchitecture.models.User;
import me.ivmg.cachingdataarchitecture.adapters.MainAdapter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.parentLayout)
    RelativeLayout parentLayout;

    @Inject
    MainRepository mainRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MainAdapter adapter;

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

        Disposable userDisposable = mainRepository.getUser("seik").subscribe(user -> {
            adapter.setUser(user);
            adapter.notifyDataSetChanged();
        }, throwable -> {
            Log.d("ErrorDataLoading", throwable.getMessage());
            Snackbar.make(parentLayout, getString(R.string.error),
                    Snackbar.LENGTH_SHORT).show();
        });

        Disposable reposDisposable = mainRepository.getRepos("seik").subscribe(repos -> {
            adapter.setRepos(repos);
            adapter.notifyDataSetChanged();
        }, throwable -> {
            Log.d("ErrorDataLoading", throwable.getMessage());
            Snackbar.make(parentLayout, getString(R.string.error),
                    Snackbar.LENGTH_SHORT).show();
        });

        compositeDisposable.addAll(userDisposable, reposDisposable);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
