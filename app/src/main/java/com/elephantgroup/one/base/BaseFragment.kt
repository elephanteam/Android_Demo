package com.elephantgroup.one.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.elephantgroup.one.R

abstract class BaseFragment<P : BasePresenter> : Fragment(), BaseView {

    private var mUnBinder: Unbinder? = null
    protected var mTitle: TextView? = null
    protected var mBack: ImageView? = null
    protected var mPresenter: P? = null

    var tempView : View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var contentView = inflater.inflate(getLayoutId(), container, false)
        tempView = contentView;
        mUnBinder = ButterKnife.bind(this,contentView)
        initView()
        mPresenter = createPresenter()
        initData()
        if (contentView == null){
            return super.onCreateView(inflater, container, savedInstanceState)
        }
        return contentView
    }

    private fun initView() {
        mBack = tempView!!.findViewById(R.id.titleBack)
        mTitle = tempView!!.findViewById(R.id.titleName)
        if (mBack != null) {
            mBack!!.visibility = View.GONE
        }
    }

    protected fun setTitle(title: String) {
        if (mTitle != null) {
            mTitle!!.text = title
        }
    }

    abstract fun createPresenter(): P

    abstract fun getLayoutId(): Int

    protected abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        mUnBinder!!.unbind()
    }
}