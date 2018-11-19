package com.elephantgroup.one.ui.wallet.thread;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.elephantgroup.one.db.UserDataBase;
import com.elephantgroup.one.ui.wallet.entity.StorableWallet;
import com.elephantgroup.one.ui.wallet.entity.WalletEventBean;
import com.elephantgroup.one.ui.wallet.util.MyWalletUtils;
import com.elephantgroup.one.ui.wallet.util.WalletConstants;
import com.elephantgroup.one.ui.wallet.util.WalletUtils;
import com.elephantgroup.one.ui.wallet.web3j.Wallet;
import com.elephantgroup.one.ui.wallet.web3j.WalletFile;
import com.elephantgroup.one.util.Constants;
import com.elephantgroup.one.util.SDCardCtrl;
import com.elephantgroup.one.util.Utils;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.ObjectMapperFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create or import the wallet
 */
public class WalletThread extends Thread {

    private String walletName;//Name of the wallet
    private String password;//The wallet password
    private String pwdInfo;//Password prompt information
    private String source;//Type 1 is a privatekey 2 is a keyStore
    private int type;//Type 0 create wallets, 1 private key import wallet, 2 keyStore into the purse  3 mnemonic into wallet
    private Context context;
    private boolean isPrivateHex;

    private String mnemonic;

    private String createMnemonic;
    
    public WalletThread(Context context, String walletName, String password, String pwdInfo, String source, int type, boolean isPrivateHex) {
        this.context = context;
        this.walletName = walletName;
        this.password = password;
        this.pwdInfo = pwdInfo;
        this.source = source;
        this.type = type;
        this.isPrivateHex = isPrivateHex;
    }

    public void setMnemonic(String tempMnemonic){
        this.mnemonic = tempMnemonic;
    }

    
    @Override
    public void run() {
        try {
            String walletAddress;
            if (type == 0) { // Generate a new address 0 x...
                walletAddress = createWallet();
            } else if (type == 1) { // By private key into the new address
                walletAddress = importPrivateKey();
            } else if (type == 2){//Through the keyStore import new address
                walletAddress = importKeyStore();
            }else{
                if (TextUtils.isEmpty(mnemonic)){
                    return;
                }
                long creationTimeSeconds = System.currentTimeMillis() / 1000;
                DeterministicSeed seed = new DeterministicSeed(Arrays.asList(mnemonic.split(" ")), null, "", creationTimeSeconds);
                DeterministicKeyChain chain = DeterministicKeyChain.builder().seed(seed).build();
                List<ChildNumber> keyPath = HDUtils.parsePath("M/44H/60H/0H/0/0");
                DeterministicKey key = chain.getKeyByPath(keyPath, true);
                BigInteger tempPrivateKey = key.getPrivKey();
                source = tempPrivateKey.toString(16);
                walletAddress = importPrivateKey();
            }
            if (TextUtils.isEmpty(walletAddress)){
                return;
            }
            saveWallet(walletAddress);
            return;
        } catch (CipherException e) {
            e.printStackTrace();
            EventBus.getDefault().post(WalletConstants.WALLET_PASSWORD_ERROR);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            EventBus.getDefault().post(WalletConstants.WALLET_MEMORY_ERROR);
            return;
        } catch (RuntimeException e) {
            e.printStackTrace();
            EventBus.getDefault().post(WalletConstants.WALLET_NORMAL_ERROR);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventBus.getDefault().post(WalletConstants.WALLET_NORMAL_ERROR);
    }

    /**
     * create wallet    create mnemonic
     * */
    private String createWallet() throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        long creationTimeSeconds = System.currentTimeMillis() / 1000;
        DeterministicSeed deterministicSeed = new DeterministicSeed(secureRandom, 128, "", creationTimeSeconds);
        List<String> mnemonicCode = deterministicSeed.getMnemonicCode();
        if (mnemonicCode != null) {
            createMnemonic = convertMnemonicList(mnemonicCode);
        }
        DeterministicSeed seed = new DeterministicSeed(Arrays.asList(createMnemonic.split(" ")), null, "", creationTimeSeconds);
        DeterministicKeyChain chain = DeterministicKeyChain.builder().seed(seed).build();
        List<ChildNumber> keyPath = HDUtils.parsePath("M/44H/60H/0H/0/0");
        DeterministicKey key = chain.getKeyByPath(keyPath, true);
        BigInteger tempPrivateKey = key.getPrivKey();
        ECKeyPair keys = ECKeyPair.create(tempPrivateKey);
        WalletFile walletFile = MyWalletUtils.generateWalletFile(password, keys, false);
        String walletAddress = writeValue(walletFile,false);
        if (TextUtils.isEmpty(walletAddress)){
            return null;
        }else{
            return walletAddress;
        }
    }


//    private void saveMnemonic(String walletAddress ,String tempMnemonic){
//        try {
//            MnemonicBean mnemonicVo = new MnemonicBean();
//            mnemonicVo.setAddress(walletAddress);
//            String secretKey = Utils.makeRandomKey(16);
//            String secretMnemonic = Aes.encodePKCS5Padding(secretKey,tempMnemonic);
//            mnemonicVo.setSecretKey(secretKey);
//            mnemonicVo.setMnemonic(secretMnemonic);
//            FinalUserDataBase.getInstance().insertMnemonic(mnemonicVo);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    /**
     * import private key
     * */
    private String importPrivateKey() throws Exception {
        ECKeyPair keys;
        if (isPrivateHex) {
            keys = ECKeyPair.create(new BigInteger(source));
        } else {
            String bigInteger = new BigInteger(source, 16).toString(10);
            keys = ECKeyPair.create(new BigInteger(bigInteger));
        }
        WalletFile walletFile = MyWalletUtils.generateWalletFile(password, keys, false);
        String walletAddress = writeValue(walletFile,true);
        if (TextUtils.isEmpty(walletAddress)){
            return null;
        }else{
            return walletAddress;
        }
    }

    /**
     * import key store
     * */
    private String importKeyStore() throws Exception {
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        WalletFile walletFile = objectMapper.readValue(source, WalletFile.class);
        Credentials credentials = Credentials.create(Wallet.decrypt(password, walletFile));
        credentials.getEcKeyPair().getPublicKey();
        walletFile = MyWalletUtils.importNewWalletFile(password, credentials.getEcKeyPair(), false);
        String walletAddress = writeValue(walletFile,true);
        if (TextUtils.isEmpty(walletAddress)){
            return null;
        }else{
            return walletAddress;
        }
    }

    /**
     * check wallet is exist
     * return wallet address
     * */
    private String writeValue(WalletFile walletFile, boolean needCheckExists) throws Exception {
        String walletAddress = MyWalletUtils.getWalletFileName(walletFile);
        if (TextUtils.isEmpty(walletAddress)){
            EventBus.getDefault().post(WalletConstants.WALLET_NORMAL_ERROR);
            return null;
        }
        if (needCheckExists){
            boolean exists = UserDataBase.getInstance().hasWallet(walletAddress);
            if (exists) {
                EventBus.getDefault().post(WalletConstants.WALLET_REPEAT_ERROR);
                return null;
            }
        }
        File destination = new File(new File(context.getFilesDir(), SDCardCtrl.WALLERPATH),WalletUtils.getInstance().initLocalKeyStore(walletAddress));
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        objectMapper.writeValue(destination, walletFile);
        return walletAddress;
    }

    /**
     * save wallet info
     * @param walletAddress   wallet address
     * */
    private void saveWallet(String walletAddress){
        if (TextUtils.isEmpty(walletName)) {//When import operation
            walletName = WalletUtils.getInstance().getWalletName(context);
        }

        if (!walletAddress.startsWith("0x")){
            walletAddress = "0x" + walletAddress;
        }

        StorableWallet storableWallet = new StorableWallet();
        storableWallet.setPublicKey(walletAddress);
        storableWallet.setWalletName(walletName);
        storableWallet.setPwdInfo(pwdInfo);
        storableWallet.setWalletImageId(WalletUtils.getInstance().setWalletImageId());
        if (type == 0) {
            storableWallet.setCanExportPrivateKey(true);
        } else {
            storableWallet.setBackup(true);
        }
        if (UserDataBase.getInstance().getAllWallet().size() <= 0) {
            storableWallet.setSelect(true);
        }
        UserDataBase.getInstance().insertWallet(storableWallet);
        if (!TextUtils.isEmpty(createMnemonic)){
            WalletEventBean eventBean = new WalletEventBean();
            eventBean.setEventCode(WalletConstants.WALLET_SUCCESS );
            eventBean.setAddress(walletAddress);
            eventBean.setMnemonic(createMnemonic);
            EventBus.getDefault().post(eventBean);
            createMnemonic = "";
        }else{
            EventBus.getDefault().post(WalletConstants.WALLET_SUCCESS);
        }
    }

    /**
     * covert mnemonic string to List<String>
     */
    private static String convertMnemonicList(List<String> mnemonics) {
        StringBuilder sb = new StringBuilder();
        for (String mnemonic : mnemonics) {
            sb.append(mnemonic);
            sb.append(" ");
        }
        return sb.toString();
    }
}
