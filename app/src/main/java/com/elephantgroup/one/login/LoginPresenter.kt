package com.elephantgroup.one.login

import com.elephantgroup.one.base.BasePresenterImpl
import com.elephantgroup.one.net.BaseModelImpl
import com.elephantgroup.one.net.NetJsonAPI
import okhttp3.ResponseBody


import java.util.HashMap

import rx.android.schedulers.AndroidSchedulers

class LoginPresenter(view: LoginContract.View) : BasePresenterImpl<LoginContract.View>(view), LoginContract.Presenter {

    override fun login(username: String, password: String, aesKey: String, message: String) {
        try {
            val params = HashMap<String, String>()
            params["loginid"] = username
            params["password"] = password
            params["aeskey"] = aesKey
            params["encrypt"] = "1"
            val jsonRequest = NetJsonAPI.getJsonRequest("user", "login", false, params)
            BaseModelImpl.getInstance().login(jsonRequest?.toString())
                    .subscribeOn(rx.schedulers.Schedulers.io())
                    .doOnSubscribe { }
                    .map<ResponseBody> { bean -> bean }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ bean -> view.onResult(bean, message) }, { throwable -> view.onError(throwable, message) })
        } catch (e: Exception) {
            view.onError(e, message)
        }

    }
}
