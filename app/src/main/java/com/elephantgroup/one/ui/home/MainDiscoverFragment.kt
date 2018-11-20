package com.elephantgroup.one.ui.home

import android.os.Handler
import android.view.View
import android.widget.TextView

import com.elephantgroup.one.R
import com.elephantgroup.one.base.BaseFragment
import com.elephantgroup.one.db.UserDataBase
import com.elephantgroup.one.ui.wallet.WalletAccountActivity
import com.elephantgroup.one.ui.wallet.WalletSelectActivity
import com.elephantgroup.one.ui.wallet.entity.StorableWallet
import com.elephantgroup.one.util.OptionsUtil

import java.util.ArrayList

import butterknife.BindView
import butterknife.OnClick

class MainDiscoverFragment : BaseFragment<MainDiscoverContract.Presenter>(), MainDiscoverContract.View {

    @BindView(R.id.discoverWallet)
    lateinit var discoverWallet: TextView

    override fun getLayoutId(): Int {
        return R.layout.main_discover_layout
    }

    override fun createPresenter(): MainDiscoverPresenter {
        return MainDiscoverPresenter(this)
    }

    override fun initData() {
        setTitle(getString(R.string.main_discover))
    }

    @OnClick(R.id.discoverWallet)
    fun onClick(v: View) {
        when (v.id) {
            R.id.discoverWallet -> Handler().postDelayed({
                val wallets = UserDataBase.getInstance().allWallet
                if (wallets == null || wallets.size <= 0) {
                    OptionsUtil.StartOptionsActivity(activity, WalletSelectActivity::class.java, discoverWallet)
                } else {
                    OptionsUtil.StartOptionsActivity(activity, WalletAccountActivity::class.java, discoverWallet)
                }
            }, 200)
        }
    }

    override fun onResult(result: Any, message: String) {

    }

    override fun onError(throwable: Throwable, message: String) {

    }

}
