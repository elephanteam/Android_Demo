package com.elephantgroup.one.ui.wallet.util;

import android.content.Context;
import android.text.TextUtils;

import com.elephantgroup.one.R;
import com.elephantgroup.one.db.UserDataBase;

import java.util.Random;

public class WalletUtils {

    private static volatile WalletUtils S_INST;

    public static String keystorePath = "UTC--2018-11-14--Elephant-Group--577498962";

    public static WalletUtils getInstance() {
        if (S_INST == null) {
            synchronized (WalletUtils.class) {
                if (S_INST == null) {
                    S_INST = new WalletUtils();
                }
            }
        }
        return S_INST;
    }

    public String initLocalKeyStore(String str) {
        String address = "";
        if (!TextUtils.isEmpty(str)) {
            if (str.startsWith(keystorePath)) {
                String tempStr = str.substring(keystorePath.length());
                if (!tempStr.startsWith("0x")) {
                    tempStr = "0x" + tempStr;
                }
                address = keystorePath + tempStr;
            } else {
                if (!str.startsWith("0x")) {
                    str = "0x" + str;
                }
                address = keystorePath + str;
            }
        }
        return address;
    }

    /**
     * Name to get the wallet
     * @ param context context Wallet name name is empty
     * */
    public String getWalletName(Context context){
        int index= 1;
        String walletName;
        while (true){
            walletName  = context.getString(R.string.wallet) + index;
            boolean foundSameName =false;
            if (UserDataBase.getInstance().checkWalletNameExist(walletName)){
                foundSameName = true;
            }
            if(foundSameName){
                index++;
            }else{
                break;
            }
        }
        return walletName;
    }

    /**
     * get the purse
     * @ param context context
     * What a purse @ param position
     * */
    public String setWalletImageId(){
        int index = Integer.parseInt(makeRandomInt(2));
        return "icon_static_0" + index;
    }


    /**
     * get the purse
     * @ param context context
     * What a purse @ param position
     * */
    public int getWalletImageId(Context context,String ids){
        return context.getResources().getIdentifier(ids,"mipmap", context.getPackageName());
    }

    /**
     * @ param length key length less than or equal to 16
     * @ return String
     */
    public static String makeRandomInt(int length) {
        String str = "123456789";
        Random random = new Random();
        if (length > 2) {
            length = 2;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(9);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
