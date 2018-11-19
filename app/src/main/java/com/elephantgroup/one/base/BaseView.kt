package com.elephantgroup.one.base

interface BaseView {

    fun onResult(result: Any, message: String)

    fun onError(throwable: Throwable, message: String)

}
