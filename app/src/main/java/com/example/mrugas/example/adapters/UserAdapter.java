package com.example.mrugas.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mrugas.example.R;
import com.example.mrugas.example.activities.DetailsActivity;
import com.example.mrugas.example.models.DailyMotionUser;
import com.example.mrugas.example.models.DailyMotionUsersList;
import com.example.mrugas.example.models.GitHubUser;
import com.example.mrugas.example.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by mruga on 24.10.2016.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context mContext;
    private List<GitHubUser> gitHubUsers = new ArrayList<>();
    private List<DailyMotionUser> dailyMotionUsersList = new ArrayList<>();

    public UserAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final User user = (gitHubUsers.size()>0 && position<gitHubUsers.size())? gitHubUsers.get(position) : dailyMotionUsersList.get(position-gitHubUsers.size());
        holder.tvLogin.setText(user.getUsername());
        Glide.with(mContext).load(user.getAvatarUrl()).into(holder.ivAvatar);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("User", user.getUsername());
                mContext.startActivity(intent);
            }
        });
        int color;
        if (user instanceof GitHubUser)
            color = R.color.gitHubBackground;
        else
            color = R.color.dailyMotionBackground;
        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, color));
    }

    @Override
    public int getItemCount() {
        return gitHubUsers.size()+dailyMotionUsersList.size();
    }
    public void addGitHubUsers(List<GitHubUser> usersList){
        this.gitHubUsers = usersList;
        notifyDataSetChanged();
    }

    public void addDailyMotionUsers(List<DailyMotionUser> usersList){
        this.dailyMotionUsersList = usersList;
        notifyDataSetChanged();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_login)
        TextView tvLogin;
        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;
        @BindView(R.id.card_view)
        CardView cardView;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
