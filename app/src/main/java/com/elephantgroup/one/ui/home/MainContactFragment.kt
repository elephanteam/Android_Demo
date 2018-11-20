package com.elephantgroup.one.ui.home

import com.elephantgroup.one.R
import com.elephantgroup.one.base.BaseFragment

class MainContactFragment : BaseFragment<MainContactContract.Presenter>(), MainContactContract.View {

    override fun getLayoutId(): Int {
        return R.layout.main_contact_layout
    }

    override fun createPresenter(): MainContactPresenter {
        return MainContactPresenter(this)
    }

    override fun initData() {
        setTitle(getString(R.string.main_contact))
    }

    override fun onResult(result: Any, message: String) {

    }

    override fun onError(throwable: Throwable, message: String) {

    }
}
