package com.elephantgroup.one.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elephantgroup.one.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    private Unbinder mUnBinder;
    private View view;
    private boolean isDataFirstLoaded;
    protected TextView mTitle;
    protected ImageView mBack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        isDataFirstLoaded = true;
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!isDataFirstLoaded && view != null) {
            ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(getLayoutId(), container, false);
        mUnBinder = ButterKnife.bind(this,view);
        initView();
        mPresenter = createPresenter();
        initData();
        return view;
    }

    private void initView() {
        mBack = view.findViewById(R.id.titleBack);
        mTitle = view.findViewById(R.id.titleName);
        if (mBack != null){
            mBack.setVisibility(View.GONE);
        }
    }

    protected void setTitle(String title) {
        if(mTitle != null){
            mTitle.setText(title);
        }
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isDataFirstLoaded) {
            return;
        }
        isDataFirstLoaded = false;
    }


    protected P mPresenter;

    public abstract int getLayoutId();

    public abstract P createPresenter();

    protected abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }
}