package com.elephantgroup.one.ui.home

import com.elephantgroup.one.base.BasePresenter
import com.elephantgroup.one.base.BaseView

interface MainDiscoverContract {

    interface View : BaseView

    interface Presenter : BasePresenter{
        fun loadData();
    }
}
