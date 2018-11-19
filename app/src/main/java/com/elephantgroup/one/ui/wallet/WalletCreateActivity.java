package com.elephantgroup.one.ui.wallet;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.elephantgroup.one.R;
import com.elephantgroup.one.base.BaseActivity;
import com.elephantgroup.one.base.BasePresenter;
import com.elephantgroup.one.db.UserDataBase;
import com.elephantgroup.one.ui.wallet.entity.WalletEventBean;
import com.elephantgroup.one.ui.wallet.thread.WalletThread;
import com.elephantgroup.one.ui.wallet.util.WalletConstants;
import com.elephantgroup.one.util.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class WalletCreateActivity extends BaseActivity{

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.walletName)
    EditText walletName;
    @BindView(R.id.walletPwd)
    EditText walletPwd;
    @BindView(R.id.walletConfirmPwd)
    EditText walletConfirmPwd;
    @BindView(R.id.walletPwdTip)
    EditText walletPwdTip;
    @BindView(R.id.createWallet)
    TextView createWallet;
    @BindView(R.id.inputWalletName)
    TextInputLayout inputWalletName;
    @BindView(R.id.inputWalletPwd)
    TextInputLayout inputWalletPwd;
    @BindView(R.id.inputConfirmWalletPwd)
    TextInputLayout inputConfirmWalletPwd;
    @BindView(R.id.inputWalletPwdTip)
    TextInputLayout inputWalletPwdTip;

    @Override
    public int getLayoutId() {
        return R.layout.wallet_create_layout;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        setTitle(getString(R.string.wallet_create));
        EventBus.getDefault().register(this);
    }

    @OnTextChanged(value = R.id.walletName,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void walletNameAfterChanged(Editable editable){
        if (editable.length() > 12){
            inputWalletName.setErrorEnabled(true);
            inputWalletName.setError(getString(R.string.wallet_name_error));
        }else{
            inputWalletName.setErrorEnabled(false);
        }
    }

    @OnTextChanged(value = R.id.walletPwd,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void walletPwdAfterChanged(Editable editable){
        if (editable.length() > 18 || (editable.length() < 8 && editable.length() > 0)){
            inputWalletPwd.setErrorEnabled(true);
            inputWalletPwd.setError(getString(R.string.wallet_pwd_error));
        }else{
            inputWalletPwd.setErrorEnabled(false);
        }
    }

    @OnTextChanged(value = R.id.walletConfirmPwd,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void walletConfirmPwdAfterChanged(Editable editable){
        if (editable.length() > 18 || (editable.length() < 8 && editable.length() > 0)){
            inputConfirmWalletPwd.setErrorEnabled(true);
            inputConfirmWalletPwd.setError(getString(R.string.wallet_pwd_error));
        }else{
            inputConfirmWalletPwd.setErrorEnabled(false);
        }
    }

    @OnTextChanged(value = R.id.walletPwdTip,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void walletPwdTipAfterChanged(Editable editable){
        if (editable.length() > 20){
            inputWalletPwdTip.setErrorEnabled(true);
            inputWalletPwdTip.setError(getString(R.string.wallet_pwd_error));
        }else{
            inputWalletPwdTip.setErrorEnabled(false);
        }
    }

    @OnClick(R.id.createWallet)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.createWallet:
                createWalletMethod();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    private void createWalletMethod(){
        if (walletName.length() > 12 || walletName.length() <= 0){
            showToast(getString(R.string.wallet_name_error));
            return;
        }
        if (walletPwd.length() > 18 || walletPwd.length() < 8){
            showToast(getString(R.string.wallet_pwd_error));
            return;
        }
        String password = walletPwd.getText().toString().trim();
        String name = walletName.getText().toString().trim();
        String pwdInfo = walletPwdTip.getText().toString().trim();
        if (UserDataBase.getInstance().checkWalletNameExist(name)){
            showToast(getString(R.string.wallet_name_exist));
            return;
        }
        if (TextUtils.equals(password,walletConfirmPwd.getText().toString().trim())){
            LoadingDialog.show(WalletCreateActivity.this,getString(R.string.wallet_create_ing));
            new WalletThread(getApplicationContext(),name,password,pwdInfo,null,0,false).start();
        }else{
            showToast(getString(R.string.wallet_pwd_match_error));
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
                        String mnemonic = walletEventBean.getMnemonic();
                        String walletAddress = walletEventBean.getAddress();
                        createSuccess(mnemonic,walletAddress);
                        break;
                }
            }else if (msg instanceof  CharSequence && TextUtils.isDigitsOnly((CharSequence) msg)){
                int code = (int) msg;
                switch (code){
                    case WalletConstants.WALLET_SUCCESS:
                        showToast(getString(R.string.wallet_create_success));
                        break;
                    case WalletConstants.WALLET_NORMAL_ERROR:
                        showToast(getString(R.string.wallet_create_error));
                        break;
                    case WalletConstants.WALLET_MEMORY_ERROR:
                        showToast(getString(R.string.wallet_memory_error));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSuccess(String mnemonic,String walletAddress){
        LoadingDialog.close();
        Intent intent = new Intent(WalletCreateActivity.this,WalletMnemonicActivity.class);
        intent.putExtra(WalletConstants.WALLET_MNEMONIC,mnemonic);
        intent.putExtra(WalletConstants.WALLET_ADDRESS,walletAddress);
        startActivity(intent);
        showToast(getString(R.string.wallet_create_success));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
