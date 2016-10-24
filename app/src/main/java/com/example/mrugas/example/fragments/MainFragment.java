package com.example.mrugas.example.fragments;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrugas.example.MyApp;
import com.example.mrugas.example.R;
import com.example.mrugas.example.adapters.UserAdapter;
import com.example.mrugas.example.models.DailyMotionUsersList;
import com.example.mrugas.example.models.GitHubUser;
import com.example.mrugas.example.injection.modules.RetrofitModule;
import com.example.mrugas.example.models.User;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mruga on 24.10.2016.
 */

public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_data)
    RecyclerView recyclerView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    RetrofitModule.GitHubApi gitHubApi;
    @Inject
    RetrofitModule.DailyMotionApi dailyMotionApi;
    @Inject
    Application application;
    private boolean gitHubReady = false;
    private boolean dailyMotionReady = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new UserAdapter(mContext));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10)));
        getData();
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    private void getData() {
        Call<List<GitHubUser>> call = gitHubApi.getUsers();
        call.enqueue(new Callback<List<GitHubUser>>() {
            @Override
            public void onResponse(Call<List<GitHubUser>> call, Response<List<GitHubUser>> response) {
                if (response.body() != null)
                    ((UserAdapter) recyclerView.getAdapter()).addUsers(response.body());
                else
                    Log.d("Error", response.errorBody().toString());
                gitHubReady = true;
                stopRefreshing();
            }

            @Override
            public void onFailure(Call<List<GitHubUser>> call, Throwable t) {

            }
        });
        final Call<DailyMotionUsersList> dailyMotionCall = dailyMotionApi.getUsers("avatar_360_url,username");
        dailyMotionCall.enqueue(new Callback<DailyMotionUsersList>() {
            @Override
            public void onResponse(Call<DailyMotionUsersList> call, Response<DailyMotionUsersList> response) {
                if (response.body() != null)
                    ((UserAdapter) recyclerView.getAdapter()).addUsers(response.body().getList());
                else
                    Log.d("Error", response.errorBody().toString());
                dailyMotionReady = true;
                stopRefreshing();
            }

            @Override
            public void onFailure(Call<DailyMotionUsersList> call, Throwable t) {

            }
        });
    }

    private void stopRefreshing() {
        if (gitHubReady && dailyMotionReady)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    protected void resolveDependencies() {
        ((MyApp) getActivity().getApplication()).getNetComponent().inject(this);
    }

    @Override
    public void onRefresh() {
        ((UserAdapter) recyclerView.getAdapter()).clear();
        getData();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;

        GridSpacingItemDecoration(int spanCount, int spacing) {
            this.spanCount = spanCount;
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % 2+1;
            outRect.left = spacing / column;
            outRect.right = spacing / (column+1)%2;
            if (position < spanCount) {
                outRect.top = spacing;
            }
            outRect.bottom = spacing;

        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
