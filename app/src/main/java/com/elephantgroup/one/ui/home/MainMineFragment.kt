package com.elephantgroup.one.ui.home

import com.elephantgroup.one.R
import com.elephantgroup.one.base.BaseFragment

class MainMineFragment : BaseFragment<MainMineContract.Presenter>(), MainMineContract.View {

    override fun getLayoutId(): Int {
        return R.layout.main_mine_layout
    }

    override fun createPresenter(): MainMinePresenter {
        return MainMinePresenter(this)
    }

    override fun initData() {
        setTitle(getString(R.string.main_mine))
    }

    override fun onResult(result: Any, message: String) {

    }

    override fun onError(throwable: Throwable, message: String) {

    }
}
