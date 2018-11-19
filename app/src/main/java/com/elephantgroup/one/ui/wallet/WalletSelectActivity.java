package com.elephantgroup.one.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.elephantgroup.one.R;
import com.elephantgroup.one.base.BaseActivity;
import com.elephantgroup.one.base.BasePresenter;
import com.elephantgroup.one.ui.wallet.entity.WalletEventBean;
import com.elephantgroup.one.ui.wallet.util.WalletConstants;
import com.elephantgroup.one.util.LoadingDialog;
import com.elephantgroup.one.util.OptionsUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletSelectActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.createWallet)
    TextView createWallet;
    @BindView(R.id.importWallet)
    TextView importWallet;

    @Override
    public int getLayoutId() {
        return R.layout.wallet_select_layout;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        setTitle(getString(R.string.wallet_create));
        EventBus.getDefault().register(this);
        ViewCompat.setTransitionName(titleName, OptionsUtil.OPTION_INFO);
        OptionsUtil.setSharedElementEnterTransition(WalletSelectActivity.this);
    }

    @OnClick({R.id.createWallet, R.id.importWallet})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createWallet:
                startActivity(new Intent(WalletSelectActivity.this,WalletCreateActivity.class));
                break;
            case R.id.importWallet:
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    @Override
    public void onResult(Object result, String message) {

    }

    @Override
    public void onError(Throwable throwable, String message) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(Object msg) {
        try {
            LoadingDialog.close();
            if (msg instanceof WalletEventBean){
                WalletEventBean walletEventBean = (WalletEventBean) msg;
                switch (walletEventBean.getEventCode()){
                    case WalletConstants.WALLET_SUCCESS:
                        finish();
                        break;
                }
            }else if (TextUtils.isDigitsOnly((CharSequence) msg)){
                int code = (int) msg;
                switch (code){
                    case WalletConstants.WALLET_SUCCESS:
                        finish();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
