package com.elephantgroup.one.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.elephantgroup.one.MyApplication;
import com.elephantgroup.one.entity.UserInfoVo;
import com.elephantgroup.one.ui.wallet.entity.MnemonicBean;
import com.elephantgroup.one.ui.wallet.entity.StorableWallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDataBase {

    private static UserDataBase instance;
    private UserDbHelper helper;
    private SQLiteDatabase db;

    private static Map<String, Integer> map;

    private UserDataBase() {
        if (MyApplication.myInfo == null && MyApplication.mContext != null) {
            MyApplication.myInfo = new UserInfoVo().readMyUserInfo(MyApplication.mContext);
        }

        if (MyApplication.myInfo != null) {
            helper = new UserDbHelper(MyApplication.mContext, MyApplication.myInfo.getLocalId());
            db = helper.getWritableDatabase();
        }else{
            helper = new UserDbHelper(MyApplication.mContext, "-1");
            db = helper.getWritableDatabase();
        }
    }

    public static synchronized UserDataBase getInstance() {
        if (instance == null) {
            instance = new UserDataBase();
            map = new HashMap<>();
        }
        return instance;
    }

    /**
     * Save the wallet list
     */
    public void insertWallet(StorableWallet storableWallet) {
        ContentValues values = new ContentValues();
        values.put(TableField.FIELD_RESERVED_DATA1, storableWallet.getPublicKey());
        values.put(TableField.FIELD_RESERVED_DATA2, storableWallet.getWalletName());
        values.put(TableField.FIELD_RESERVED_DATA3, storableWallet.isSelect());
        values.put(TableField.FIELD_RESERVED_DATA4, storableWallet.isCanExportPrivateKey());
        values.put(TableField.FIELD_RESERVED_DATA5, storableWallet.isBackup());
        values.put(TableField.FIELD_RESERVED_DATA6, storableWallet.getWalletImageId());
        values.put(TableField.FIELD_RESERVED_DATA7, storableWallet.getLocalId());
        values.put(TableField.FIELD_RESERVED_DATA8, storableWallet.getPwdInfo());
        db.insert(TableField.TABLE_WALLET, TableField._ID, values);
    }

    /**
     * update wallet
     * */
    public void updateWallet(StorableWallet storableWallet){
        boolean result = hasWallet(storableWallet.getPublicKey());
        if(result){
            ContentValues values = new ContentValues();
            values.put(TableField.FIELD_RESERVED_DATA2, storableWallet.getWalletName());
            values.put(TableField.FIELD_RESERVED_DATA3, storableWallet.isSelect());
            values.put(TableField.FIELD_RESERVED_DATA4, storableWallet.isCanExportPrivateKey());
            values.put(TableField.FIELD_RESERVED_DATA5, storableWallet.isBackup());
            values.put(TableField.FIELD_RESERVED_DATA6, storableWallet.getWalletImageId());
            values.put(TableField.FIELD_RESERVED_DATA7, storableWallet.getLocalId());
            values.put(TableField.FIELD_RESERVED_DATA8, storableWallet.getPwdInfo());
            db.update(TableField.TABLE_WALLET,values,TableField.FIELD_RESERVED_DATA1 + "=?",new String[]{storableWallet.getPublicKey()});
        }else{
            insertWallet(storableWallet);
        }
    }

    /**
     * update wallet
     * */
    public void updateWalletName(String walletAddress,String walletName){
        ContentValues values = new ContentValues();
        values.put(TableField.FIELD_RESERVED_DATA2, walletName);
        db.update(TableField.TABLE_WALLET,values,TableField.FIELD_RESERVED_DATA1 + "=?",new String[]{walletAddress});
    }

    /**
     * get all wallet
     * */
    public ArrayList<StorableWallet> getAllWallet(){
        String sql = "select * from " + TableField.TABLE_WALLET + " order by " + TableField._ID + " desc ";
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<StorableWallet> wallets = new ArrayList<>();
        StorableWallet storableWallet;
        while (cursor.moveToNext()) {
            storableWallet = new StorableWallet();
            storableWallet.setPublicKey(cursor.getString(cursor.getColumnIndex(TableField.FIELD_RESERVED_DATA1)));
            storableWallet.setWalletName(cursor.getString(cursor.getColumnIndex(TableField.FIELD_RESERVED_DATA2)));
            storableWallet.setSelect(cursor.getInt(cursor.getColumnIndex(TableField.FIELD_RESERVED_DATA3)) == 1);
            storableWallet.setCanExportPrivateKey(cursor.getInt(cursor.getColumnIndex(TableField.FIELD_RESERVED_DATA4)) == 1);
            storableWallet.setBackup(cursor.getInt(cursor.getColumnIndex(TableField.FIELD_RESERVED_DATA5)) == 1);
            storableWallet.setWalletImageId(cursor.getString(cursor.getColumnIndex(TableField.FIELD_RESERVED_DATA6)));
            storableWallet.setLocalId(cursor.getString(cursor.getColumnIndex(TableField.FIELD_RESERVED_DATA7)));
            storableWallet.setPwdInfo(cursor.getString(cursor.getColumnIndex(TableField.FIELD_RESERVED_DATA8)));
            wallets.add(storableWallet);
        }
        cursor.close();
        return wallets;
    }

    /**
     * check has wallet
     * */
    public boolean hasWallet(String address){
        String sql = "select * from " + TableField.TABLE_WALLET + " where " + TableField.FIELD_RESERVED_DATA1 + "=?" ;
        Cursor cursor = db.rawQuery(sql, new String[]{address});
        if (cursor.moveToNext()) {
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    /**
     * check has wallet
     * */
    public boolean checkWalletNameExist(String walletName){
        String sql = "select * from " + TableField.TABLE_WALLET + " where " + TableField.FIELD_RESERVED_DATA2 + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{walletName});
        if (cursor.moveToNext()) {
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }


    /**
     * Save the wallet list
     */
    public void insertMnemonic(MnemonicBean mnemonicVo) {
        ContentValues values = new ContentValues();
        values.put(TableField.FIELD_RESERVED_DATA1, mnemonicVo.getAddress());
        values.put(TableField.FIELD_RESERVED_DATA2, mnemonicVo.getMnemonic());
        values.put(TableField.FIELD_RESERVED_DATA3, mnemonicVo.getSecretKey());
        db.insert(TableField.TABLE_MNEMONIC, TableField._ID, values);
    }

    /**
     * Save the wallet list
     */
    public MnemonicBean getMnemonic(String address) {
        String sql = "select * from " + TableField.TABLE_MNEMONIC + " where " + TableField.FIELD_RESERVED_DATA1 + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{address});
        MnemonicBean vo = null;
        if (cursor.moveToNext()) {
            vo = new MnemonicBean();
            vo.setAddress(cursor.getString(cursor.getColumnIndex(TableField.FIELD_RESERVED_DATA1)));
            vo.setMnemonic(cursor.getString(cursor.getColumnIndex(TableField.FIELD_RESERVED_DATA2)));
            vo.setSecretKey(cursor.getString(cursor.getColumnIndex(TableField.FIELD_RESERVED_DATA3)));
        }
        cursor.close();
        return vo;
    }


    /**
     * delete all address token
     * @param address wallet address
     */
    public void deleteMnemonic(String address) {
        db.delete(TableField.TABLE_MNEMONIC, TableField.FIELD_RESERVED_DATA1 + "=?", new String[]{address});
    }

    /**
     * check has wallet
     * */
    public boolean hasMnemonic(String address){
        String sql = "select * from " + TableField.TABLE_MNEMONIC + " where " + TableField.FIELD_RESERVED_DATA1 + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{address});
        if (cursor.moveToNext()) {
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    /**
     * Database transaction processing
     */

    public void beginTransaction() {
        if (db != null) {
            db.beginTransaction();
        }
    }

    public void endTransactionSuccessful() {
        if (db != null) {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    public void setTransactionSuccessful() {
        if (db != null) {
            db.setTransactionSuccessful();
        }
    }

    public void endTransaction() {
        if (db != null) {
            db.endTransaction();
        }
    }


    /**
     * Close the database
     */
    public void close() {
        try {
            if (db != null)
                db.close();
            if (helper != null) {
                helper.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            instance = null;
        }
    }
}
