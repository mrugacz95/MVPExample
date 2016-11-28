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
import com.example.mrugas.example.activities.UserActivity;
import com.example.mrugas.example.models.GitHubUser;
import com.example.mrugas.example.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mrugas.example.activities.UserActivity.DAILYMOTION;
import static com.example.mrugas.example.activities.UserActivity.GITHUB;
import static com.example.mrugas.example.activities.UserActivity.TYPE;
import static com.example.mrugas.example.activities.UserActivity.USER;

/**
 * Created by mruga on 24.10.2016.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context mContext;
    private List<? extends User> firstList = new ArrayList<>();
    private List<? extends User> secondList = new ArrayList<>();
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
        final User user = (firstList.size()>0 && position< firstList.size())? firstList.get(position) : secondList.get(position- firstList.size());

        holder.tvLogin.setText(user.getUsername());
        Glide.with(mContext).load(user.getAvatarUrl()).into(holder.ivAvatar);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserActivity.class);
                intent.putExtra(USER, user.getUsername());
                if(user instanceof GitHubUser)
                    intent.putExtra(TYPE, GITHUB);
                else
                    intent.putExtra(TYPE,DAILYMOTION);
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
        return firstList.size() + secondList.size();
    }

    public void addUsers(List<? extends User> usersList){
        if(firstList.isEmpty())
            firstList = usersList;
        else
            secondList = usersList;
        notifyDataSetChanged();
    }
//
    public void clear() {
        firstList.clear();
        secondList.clear();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_login)
        TextView tvLogin;
        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;
        @BindView(R.id.card_view)
        CardView cardView;

        UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
