package com.elephantgroup.one.ui.wallet

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView

import com.elephantgroup.one.R
import com.elephantgroup.one.base.BaseActivity
import com.elephantgroup.one.db.UserDataBase
import com.elephantgroup.one.ui.wallet.entity.StorableWallet
import com.elephantgroup.one.ui.wallet.entity.WalletEventBean
import com.elephantgroup.one.ui.wallet.util.WalletConstants
import com.elephantgroup.one.ui.wallet.util.WalletUtils
import com.elephantgroup.one.util.LoadingDialog
import com.elephantgroup.one.util.OptionsUtil
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

class WalletAccountActivity : BaseActivity<WalletAccountContract.Presenter>(), WalletAccountContract.View, MultiItemTypeAdapter.OnItemClickListener {
    @BindView(R.id.titleName)
    lateinit var titleName: TextView

    @BindView(R.id.recycleView)
    lateinit var recycleView: RecyclerView

    internal var isFirstLoad = true

    private var mData = ArrayList<StorableWallet>()
    private var mAdapter: CommonAdapter<StorableWallet>? = null

    override fun getLayoutId(): Int {
        return R.layout.wallet_account_layout
    }

    override fun createPresenter(): WalletAccountContract.Presenter {
        return WalletAccountPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        if (!isFirstLoad) {
            mData = UserDataBase.getInstance().allWallet
            mAdapter!!.notifyDataSetChanged()
        } else {
            isFirstLoad = false
        }
    }

    override fun initData() {
        setTitle(getString(R.string.wallet_list))
        ViewCompat.setTransitionName(titleName, OptionsUtil.OPTION_INFO)
        OptionsUtil.setSharedElementEnterTransition(this@WalletAccountActivity)
        mData = UserDataBase.getInstance().allWallet
        recycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = object : CommonAdapter<StorableWallet>(this, R.layout.item_wallet_account, mData) {
            override fun convert(holder: ViewHolder, storableWallet: StorableWallet, position: Int) {
                setData(holder, storableWallet)
            }
        }
        mAdapter!!.setOnItemClickListener(this)
        recycleView.adapter = mAdapter
    }

    override fun onResult(result: Any, message: String) {

    }

    override fun onError(throwable: Throwable, message: String) {

    }

    private fun setData(holder: ViewHolder, storableWallet: StorableWallet) {
        holder.setImageResource(R.id.walletIcon, WalletUtils.getInstance().getWalletImageId(this@WalletAccountActivity, storableWallet.walletImageId))
        holder.setText(R.id.walletName, storableWallet.walletName)
        holder.setText(R.id.walletAddress, storableWallet.publicKey)
        if (TextUtils.isEmpty(storableWallet.pwdInfo)) {
            holder.setVisible(R.id.walletInfo, false)
        } else {
            holder.setVisible(R.id.walletInfo, true)
            holder.setText(R.id.walletInfo, storableWallet.pwdInfo)
        }
    }

    @OnClick(R.id.walletCreate, R.id.walletImport)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.walletCreate -> startActivity(Intent(this, WalletCreateActivity::class.java))
            R.id.walletImport -> {
            }
            else -> super.onClick(v)
        }
    }

    override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
        val storableWallet = mData[position]
        val copyIntent = Intent(this@WalletAccountActivity, WalletExportActivity::class.java)
        copyIntent.putExtra(WalletConstants.WALLET_INFO, storableWallet)
        startActivity(copyIntent)
    }

    override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
        return false
    }

}
