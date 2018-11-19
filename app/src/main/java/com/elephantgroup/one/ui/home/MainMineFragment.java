package com.elephantgroup.one.ui.home;

import com.elephantgroup.one.R;
import com.elephantgroup.one.base.BaseFragment;

public class MainMineFragment extends BaseFragment<MainMineContract.Presenter> implements MainMineContract.View   {

    @Override
    public int getLayoutId() {
        return R.layout.main_mine_layout;
    }

    @Override
    public MainMinePresenter createPresenter() {
        return new MainMinePresenter(this);
    }

    @Override
    protected void initData() {
        setTitle(getString(R.string.main_mine));
    }

    @Override
    public void onResult(Object result, String message) {

    }

    @Override
    public void onError(Throwable throwable, String message) {

    }
}
