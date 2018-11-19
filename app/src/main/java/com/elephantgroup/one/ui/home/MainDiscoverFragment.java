package com.elephantgroup.one.ui.home;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.elephantgroup.one.R;
import com.elephantgroup.one.base.BaseFragment;
import com.elephantgroup.one.db.UserDataBase;
import com.elephantgroup.one.ui.wallet.WalletAccountActivity;
import com.elephantgroup.one.ui.wallet.WalletSelectActivity;
import com.elephantgroup.one.ui.wallet.entity.StorableWallet;
import com.elephantgroup.one.util.OptionsUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainDiscoverFragment extends BaseFragment<MainDiscoverContract.Presenter> implements MainDiscoverContract.View {

    @BindView(R.id.discoverWallet)
    TextView discoverWallet;

    @Override
    public int getLayoutId() {
        return R.layout.main_discover_layout;
    }

    @Override
    public MainDiscoverPresenter createPresenter() {
        return new MainDiscoverPresenter(this);
    }

    @Override
    protected void initData() {
        setTitle(getString(R.string.main_discover));
    }

    @OnClick({R.id.discoverWallet})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.discoverWallet:
                new Handler().postDelayed(() -> {
                    ArrayList<StorableWallet> wallets = UserDataBase.getInstance().getAllWallet();
                    if (wallets == null || wallets.size() <= 0){
                        OptionsUtil.StartOptionsActivity(getActivity(),WalletSelectActivity.class,discoverWallet);
                    }else{
                        OptionsUtil.StartOptionsActivity(getActivity(),WalletAccountActivity.class,discoverWallet);
                    }
                }, 200);
                break;
        }
    }

    @Override
    public void onResult(Object result, String message) {

    }

    @Override
    public void onError(Throwable throwable, String message) {

    }

}
