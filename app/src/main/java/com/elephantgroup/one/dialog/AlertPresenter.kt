package com.elephantgroup.one.dialog

import android.content.Context
import android.content.DialogInterface

import com.elephantgroup.one.base.BasePresenterImpl

class AlertPresenter(view: AlertContract.View) : BasePresenterImpl<AlertContract.View>(view), AlertContract.Presenter {

    override fun mnemonicBackupDialog(context: Context, negativeMsg: String) {

    }

}
