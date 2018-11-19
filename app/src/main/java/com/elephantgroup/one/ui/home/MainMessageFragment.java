package com.elephantgroup.one.ui.home;

import com.elephantgroup.one.R;
import com.elephantgroup.one.base.BaseFragment;

public class MainMessageFragment extends BaseFragment<MainMessageContract.Presenter> implements MainMessageContract.View   {

    @Override
    public int getLayoutId() {
        return R.layout.main_message_layout;
    }

    @Override
    public MainMessagePresenter createPresenter() {
        return new MainMessagePresenter(this);
    }

    @Override
    protected void initData() {
        setTitle(getString(R.string.main_chats));
    }

    @Override
    public void onResult(Object result, String message) {

    }

    @Override
    public void onError(Throwable throwable, String message) {

    }
}
