package com.elephantgroup.one.ui.wallet.util;

import android.text.TextUtils;

import com.elephantgroup.one.ui.wallet.web3j.Wallet;
import com.elephantgroup.one.ui.wallet.web3j.WalletFile;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class MyWalletUtils extends WalletUtils {

    /**
     * create a full wallet
     * @ param "password," password
     * @ param destinationDirectory file content
     * */
    public static String generateFullNewWalletFile(String password, File destinationDirectory)
            throws NoSuchAlgorithmException, NoSuchProviderException,
            InvalidAlgorithmParameterException, CipherException, IOException {
        return generateNewWalletFile(password, destinationDirectory, true);
    }

    /**
     * create a light purse (faster than full purse)
     * @ param "password," password
     * @ param destinationDirectory file content
     * */
    public static String generateLightNewWalletFile(String password, File destinationDirectory)
            throws NoSuchAlgorithmException, NoSuchProviderException,
            InvalidAlgorithmParameterException, CipherException, IOException {
        return generateNewWalletFile(password, destinationDirectory, false);
    }

    /**
     * create a wallet
     * @ param password, password
     * @ param useFullScrypt whether an entire purse
     * */
    public static WalletFile generateNewWalletFile(String password, boolean useFullScrypt)
            throws CipherException, IOException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, NoSuchProviderException {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        return generateWalletFile(password, ecKeyPair, useFullScrypt);
    }
    /**
     * import wallet
     * @ param password, password
     * @ param keys secret key pairs
     * @ param useFullScrypt whether an entire purse
     * */
    public static WalletFile importNewWalletFile(String password, ECKeyPair keys, boolean useFullScrypt)
            throws CipherException, IOException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, NoSuchProviderException {
        return generateWalletFile(password, keys, useFullScrypt);
    }

    /**
     * import wallet
     * @ param password,password
     * @ param ecKeyPair secret key pairs
     * @ param useFullScrypt whether an entire purse
     * @ return the wallet address
     * */
    public static WalletFile generateWalletFile(String password, ECKeyPair ecKeyPair, boolean useFullScrypt) throws CipherException, IOException {
        WalletFile walletFile;
        if (useFullScrypt) {
            walletFile = Wallet.createStandard(password, ecKeyPair);
        } else {
            walletFile = Wallet.createLight(password, ecKeyPair);
        }
        return walletFile;
    }

    public static String getWalletFileName(WalletFile walletFile) {
        String address = walletFile.getAddress();
        if(TextUtils.isEmpty(address)){
            return  null;
        }else if (!address.startsWith("0x")){
            address = "0x" + address;
        }
        return address;
    }
}
