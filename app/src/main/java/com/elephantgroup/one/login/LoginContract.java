package com.elephantgroup.one.login;

import android.content.Context;

import com.elephantgroup.one.base.BasePresenter;
import com.elephantgroup.one.base.BaseView;

public interface LoginContract {

    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter {
        void login(String username, String password,String aesKey,String message);
    }
}
