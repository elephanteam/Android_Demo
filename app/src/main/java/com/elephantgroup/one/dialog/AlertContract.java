package com.elephantgroup.one.dialog;

import android.content.Context;

import com.elephantgroup.one.base.BasePresenter;
import com.elephantgroup.one.base.BaseView;

public interface AlertContract{

    public interface View extends BaseView {
        /**
         * mnemonic backup submit
         * */
        void mnemonicBackupSubmit();
    }

    public interface Presenter extends BasePresenter {


        /**
         * wallet mapping success dialog
         * */
        void mnemonicBackupDialog(Context context, String negativeMsg);
    }

}
