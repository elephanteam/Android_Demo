package com.elephantgroup.one.ui.home;

import com.elephantgroup.one.R;
import com.elephantgroup.one.base.BaseFragment;

public class MainContactFragment extends BaseFragment<MainContactContract.Presenter> implements MainContactContract.View  {

    @Override
    public int getLayoutId() {
        return R.layout.main_contact_layout;
    }

    @Override
    public MainContactPresenter createPresenter() {
        return new MainContactPresenter(this);
    }

    @Override
    protected void initData() {
        setTitle(getString(R.string.main_contact));
    }

    @Override
    public void onResult(Object result, String message) {

    }

    @Override
    public void onError(Throwable throwable, String message) {

    }
}
