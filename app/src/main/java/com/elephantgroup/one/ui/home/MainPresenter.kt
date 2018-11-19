package com.elephantgroup.one.ui.home

import com.elephantgroup.one.base.BasePresenterImpl
import com.elephantgroup.one.login.LoginContract

class MainPresenter(view: MainContract.View) : BasePresenterImpl<MainContract.View>(view), MainContract.Presenter
