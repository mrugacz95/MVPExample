package com.example.mrugas.example.fragments;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.mrugas.example.MyApp;
import com.example.mrugas.example.R;
import com.example.mrugas.example.adapters.UserAdapter;
import com.example.mrugas.example.models.DailyMotionUsersList;
import com.example.mrugas.example.models.GitHubUser;
import com.example.mrugas.example.injection.modules.RetrofitModule;
import com.example.mrugas.example.models.User;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mruga on 24.10.2016.
 */

public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_data)
    RecyclerView recyclerView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.main_container)
    RelativeLayout mainContainer;
    @BindView(R.id.bt_no_connection)
    Button noConnection;
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
    @OnClick(R.id.bt_no_connection)
    public void getData() {
        if(!isOnline()) {
            noConnection.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
            recyclerView.setVisibility(View.GONE);
            return;
        }
        noConnection.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);


        gitHubApi.getUsers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<GitHubUser>>(){

                    @Override
                    public void onCompleted() {
                        gitHubReady = true;
                        stopRefreshing();
                    }

                    @Override
                    public void onError(Throwable e) {
                        gitHubReady = true;
                        stopRefreshing();
                        Snackbar.make(mainContainer,"GitHub failed: "+e.getMessage(),Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<GitHubUser> users) {
                        ((UserAdapter) recyclerView.getAdapter()).addUsers(users);
                    }
                });
            dailyMotionApi.getUsers("avatar_360_url,username")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<DailyMotionUsersList>() {
                        @Override
                        public void onCompleted() {
                            dailyMotionReady = true;
                            stopRefreshing();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Snackbar.make(mainContainer,"DailyMotion failed: "+e.getMessage(),Snackbar.LENGTH_LONG).show();
                            dailyMotionReady = true;
                            stopRefreshing();
                        }

                        @Override
                        public void onNext(DailyMotionUsersList dailyMotionUsersList) {
                        ((UserAdapter) recyclerView.getAdapter()).addUsers(dailyMotionUsersList.getList());
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
            int column = position % 2;
            if(column==0) {
                outRect.left = spacing;
                outRect.right = spacing / 2;
            }
            else {
                outRect.left = spacing /2;
                outRect.right = spacing;
            }
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
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
