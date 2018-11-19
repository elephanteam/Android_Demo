package com.elephantgroup.one.base

import io.reactivex.disposables.Disposable

interface BasePresenter{

    // 初始化
    fun start()

    // Activity destroy 置空view
    fun detach()

    // 将网络请求的disposable添加入compositeDisposable,退出时销毁
    fun addDisposable(subscription: Disposable)

    // 注销所有请求
    fun unDisposable()
}