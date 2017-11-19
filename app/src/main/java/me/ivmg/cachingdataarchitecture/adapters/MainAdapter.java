package me.ivmg.cachingdataarchitecture.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.seik.cachingdataarchitecture.R;
import me.ivmg.cachingdataarchitecture.models.Repo;
import me.ivmg.cachingdataarchitecture.models.User;

/**
 * Created by Iván
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private static final int TYPE_USER = 0;
    private static final int TYPE_REPO = 1;

    private User user;
    private List<Repo> repos;

    public MainAdapter(Context context, User user, List<Repo> repos) {
        this.context = context;
        this.user = user;
        this.repos = repos;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRepos(List<Repo> repos) {
        this.repos = repos;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.profileImage)
        CircleImageView profileImage;
        @BindView(R.id.userIdentity)
        TextView userIdentity;
        @BindView(R.id.reposNumber)
        TextView repos;
        @BindView(R.id.followers)
        TextView followers;

        UserViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.repoName)
        TextView name;

        RepoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_REPO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo,
                    parent, false);
            return new RepoViewHolder(view);
        } else if (viewType == TYPE_USER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,
                    parent, false);
            return new UserViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {

            if (user == null) return;

            UserViewHolder userHolder = (UserViewHolder) holder;

            Glide.with(context)
                    .load(user.getProfilePhoto())
                    .into(userHolder.profileImage);

            userHolder.followers.setText(String.valueOf(user.getFollowers()));
            userHolder.repos.setText(String.valueOf(user.getRepos()));

            String bio = (user.getBio() == null) ? "None" : user.getBio();

            userHolder.userIdentity.setText(context.getString(R.string.userIdentity,
                    user.getName(), bio));

        } else if (holder instanceof RepoViewHolder) {
            RepoViewHolder repoHolder = (RepoViewHolder) holder;

            repoHolder.name.setText(getItem(position).getName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_USER;

        return TYPE_REPO;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private Repo getItem(int position) {
        return repos.get(position - 1);
    }


    @Override
    public int getItemCount() {
        return repos.size() + 1;
    }
}
