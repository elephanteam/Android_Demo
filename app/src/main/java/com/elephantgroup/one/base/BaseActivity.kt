package com.elephantgroup.one.base

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
import com.elephantgroup.one.R
import com.elephantgroup.one.util.ActivityManager
import com.elephantgroup.one.util.ToastUtil

abstract class BaseActivity<P : BasePresenter> : FragmentActivity(), BaseView, View.OnClickListener {

    protected lateinit var mPresenter: P
    protected var mTitle: TextView? = null
    protected var mBack: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityManager.getInatance().addActivity(this)
        setContentView(getLayoutId())
        ButterKnife.bind(this)
        initView()
        mPresenter = createPresenter()
        initData()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.titleBack -> finish()
        }
    }

    protected fun setTitle(title: String) {
        if (mTitle != null) {
            mTitle!!.text = title
        }
    }

    protected fun showToast(msg: String) {
        ToastUtil.showToast(this, msg)
    }

    private fun initView() {
        mBack = findViewById(R.id.titleBack)
        mTitle = findViewById(R.id.titleName)
        if (mBack != null) {
            mBack!!.setOnClickListener(this)
        }
    }

    abstract fun createPresenter(): P

    abstract fun getLayoutId(): Int

    protected abstract fun initData()

}