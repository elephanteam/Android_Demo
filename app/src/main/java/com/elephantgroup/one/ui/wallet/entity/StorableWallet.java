package com.elephantgroup.one.ui.wallet.entity;

import java.io.Serializable;


public class StorableWallet implements Serializable {

    private String localId;

   //The wallet address 、public key
    private String publicKey;

    //Name of the wallet
    private String walletName;

    //The currently selected purse
    private String walletImageId;

    // 0 the default wallet 1 observe the purse
    private int walletType;

    //Password prompt information
    private String pwdInfo;

    //false can't export, true can be derived
    private boolean canExportPrivateKey;

    //The currently selected purse
    private boolean isSelect;

    //is backup true or false
    private boolean isBackup;

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getWalletImageId() {
        return walletImageId;
    }

    public void setWalletImageId(String walletImageId) {
        this.walletImageId = walletImageId;
    }

    public int getWalletType() {
        return walletType;
    }

    public void setWalletType(int walletType) {
        this.walletType = walletType;
    }

    public String getPwdInfo() {
        return pwdInfo;
    }

    public void setPwdInfo(String pwdInfo) {
        this.pwdInfo = pwdInfo;
    }

    public boolean isCanExportPrivateKey() {
        return canExportPrivateKey;
    }

    public void setCanExportPrivateKey(boolean canExportPrivateKey) {
        this.canExportPrivateKey = canExportPrivateKey;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isBackup() {
        return isBackup;
    }

    public void setBackup(boolean backup) {
        isBackup = backup;
    }
}
