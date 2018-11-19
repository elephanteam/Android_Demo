package com.elephantgroup.one.ui.wallet;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elephantgroup.one.R;
import com.elephantgroup.one.base.BaseActivity;
import com.elephantgroup.one.base.BasePresenter;
import com.elephantgroup.one.db.UserDataBase;
import com.elephantgroup.one.ui.wallet.entity.StorableWallet;
import com.elephantgroup.one.ui.wallet.util.WalletConstants;
import com.elephantgroup.one.ui.wallet.util.WalletUtils;
import com.elephantgroup.one.util.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletExportActivity extends BaseActivity {

    @BindView(R.id.app_btn_right)
    TextView appBtnRight;
    @BindView(R.id.walletExportPwdInfo)
    TextView walletExportPwdInfo;
    @BindView(R.id.walletCopyPwdInfoLine)
    View walletCopyPwdInfoLine;
    @BindView(R.id.walletExportName)
    TextView walletExportName;
    @BindView(R.id.walletExportPrivateKey)
    TextView walletExportPrivateKey;
    @BindView(R.id.walletExportKeyStore)
    TextView walletExportKeyStore;
    @BindView(R.id.walletIcon)
    ImageView walletIcon;
    @BindView(R.id.walletAddress)
    TextView walletAddress;
    @BindView(R.id.app_title)
    TextView appTitle;

    private StorableWallet storableWallet;

    @Override
    public int getLayoutId() {
        getPassData();
        return R.layout.wallet_export_layout;
    }

    private void getPassData() {
        storableWallet = (StorableWallet) getIntent().getSerializableExtra(WalletConstants.WALLET_INFO);
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        appTitle.setText(getString(R.string.wallet_backup));
        appBtnRight.setText(getString(R.string.save));
        if (storableWallet != null){
            walletExportName.setHint(getString(R.string.wallet_export_name, storableWallet.getWalletName()));
            if (TextUtils.isEmpty(storableWallet.getPwdInfo())) {
                walletExportPwdInfo.setVisibility(View.GONE);
                walletCopyPwdInfoLine.setVisibility(View.GONE);
            } else {
                walletExportPwdInfo.setText(getString(R.string.wallet_export_pwd_info, storableWallet.getPwdInfo()));
                walletCopyPwdInfoLine.setVisibility(View.VISIBLE);
            }
            walletIcon.setImageResource(WalletUtils.getInstance().getWalletImageId(this, storableWallet.getWalletImageId()));
            walletAddress.setText(storableWallet.getPublicKey());

            //Observe the purse
            if (storableWallet.getWalletType() == 1) {
                walletExportPrivateKey.setEnabled(false);
                walletExportKeyStore.setEnabled(false);
            }
        }
    }

    /**
     * to export the private key
     * export KeyStore
     * to delete the wallet
     */
    @OnClick({R.id.walletExportPrivateKey,R.id.walletExportKeyStore,R.id.walletDelete,R.id.app_btn_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_btn_right:
                updateWalletName();
                break;
            default:
                super.onClick(v);
        }
    }

    private void updateWalletName(){
        String walletName = walletExportName.getText().toString().trim();
        String newAddress = walletAddress.getText().toString().trim();
        if (TextUtils.isEmpty(walletName)){
            showToast(getString(R.string.wallet_update_name_empty));
            return;
        }
        if (UserDataBase.getInstance().checkWalletNameExist(walletName)){
            showToast(getString(R.string.wallet_update_name_exist));
            return;
        }
        UserDataBase.getInstance().updateWalletName(newAddress,walletName);
        showToast(getString(R.string.wallet_update_name_success));
        Utils.hiddenKeyBoard(this);
    }

    @Override
    public void onResult(Object result, String message) {

    }

    @Override
    public void onError(Throwable throwable, String message) {

    }
}
