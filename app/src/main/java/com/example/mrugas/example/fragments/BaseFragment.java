package com.example.mrugas.example.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrugas.example.MyApp;
import com.example.mrugas.example.R;

import butterknife.ButterKnife;

/**
 * Created by mruga on 24.10.2016.
 */

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view =  inflater.inflate(getLayoutRes(), container, false);
        ButterKnife.bind(this,view);
        resolveDependencies();
        return view;
    }
    protected abstract int getLayoutRes();
    protected abstract void resolveDependencies();
}
