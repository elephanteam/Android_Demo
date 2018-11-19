package com.elephantgroup.one.dialog

import com.elephantgroup.one.base.BaseActivity


class AlertDialog : BaseActivity<AlertContract.Presenter>(), AlertContract.View {


    override fun getLayoutId(): Int {
        return 0
    }

    override fun createPresenter(): AlertContract.Presenter {
        return AlertPresenter(this)
    }

    override fun initData() {

    }

    override fun mnemonicBackupSubmit() {

    }

    override fun onResult(result: Any, message: String) {

    }

    override fun onError(throwable: Throwable, message: String) {

    }
}
