package com.elephantgroup.one.ui.wallet;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.elephantgroup.one.R;
import com.elephantgroup.one.base.BaseActivity;
import com.elephantgroup.one.base.BasePresenter;
import com.elephantgroup.one.custom.flowtag.FlowTagLayout;
import com.elephantgroup.one.dialog.AlertDialog;
import com.elephantgroup.one.ui.wallet.entity.MnemonicBean;
import com.elephantgroup.one.ui.wallet.util.WalletConstants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletMnemonicActivity extends BaseActivity {

    @BindView(R.id.mnemonicNext)
    TextView mnemonicNext;
    @BindView(R.id.mnemonicAddFlowTag)
    FlowTagLayout mnemonicAddFlowTag;

    private WalletMnemonicAdapter  mMnemonicAdapter;

    private ArrayList<MnemonicBean> mnemonicList;

    private String mnemonic;
    private String walletAddress;

    @Override
    public int getLayoutId() {
        getPassData();
        return R.layout.wallet_mnemonic_layout;
    }

    private void getPassData() {
        mnemonic = getIntent().getStringExtra(WalletConstants.WALLET_MNEMONIC);
        walletAddress = getIntent().getStringExtra(WalletConstants.WALLET_ADDRESS);
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        setTitle(getString(R.string.wallet_mnemonic_13));
        if (!TextUtils.isEmpty(mnemonic)){
            mnemonicList = new ArrayList<>();
            String[] mnemonics = mnemonic.split(" ");
            MnemonicBean mnemonicBean;
            for (int i = 0 ; i < mnemonics.length ; i++){
                mnemonicBean = new MnemonicBean();
                mnemonicBean.setHasSelected(true);
                mnemonicBean.setMnemonic(mnemonics[i]);
                mnemonicList.add(mnemonicBean);
            }
        }
        mMnemonicAdapter = new WalletMnemonicAdapter(this);
        mnemonicAddFlowTag.setAdapter(mMnemonicAdapter,false);
        mMnemonicAdapter.loadData(mnemonicList);
        showMnemonicDialog();
    }

    private void showMnemonicDialog() {
//        Intent intent = new Intent(WalletMnemonicActivity.this, AlertDialog.class);
//        intent.putExtra("type", 8);
//        startActivity(intent);
//        overridePendingTransition(0, 0);
    }

    @OnClick({R.id.mnemonicNext,R.id.titleBack})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.mnemonicNext:
                showToast("暂未完成");
                break;
            case R.id.titleBack:
                startActivity(new Intent(this,WalletAccountActivity.class));
                finish();
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
}
