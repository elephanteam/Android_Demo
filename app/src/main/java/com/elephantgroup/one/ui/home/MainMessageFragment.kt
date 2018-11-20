package com.elephantgroup.one.ui.home

import com.elephantgroup.one.R
import com.elephantgroup.one.base.BaseFragment

class MainMessageFragment : BaseFragment<MainMessageContract.Presenter>(), MainMessageContract.View {

    override fun getLayoutId(): Int {
        return R.layout.main_message_layout
    }

    override fun createPresenter(): MainMessagePresenter {
        return MainMessagePresenter(this)
    }

    override fun initData() {
        setTitle(getString(R.string.main_chats))
    }

    override fun onResult(result: Any, message: String) {

    }

    override fun onError(throwable: Throwable, message: String) {

    }
}
