package com.elephantgroup.one.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * The user information database
 */
public class UserDbHelper extends SQLiteOpenHelper {

	public static final int DB_VERSION = 1;
	
	public UserDbHelper(Context context, String dbName) {
		super(context, dbName, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createMnemonicTable(db);
		createWalletTable(db);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		createMnemonicTable(db);
		createWalletTable(db);
	}

	private void createWalletTable(SQLiteDatabase db){
		//trams table
		String sql_wallet  = "CREATE TABLE IF NOT EXISTS "
				+ TableField.TABLE_WALLET			 + "("
				+ TableField._ID					 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TableField.FIELD_RESERVED_DATA1	 + " text,"            //  WALLET_ADDRESS
				+ TableField.FIELD_RESERVED_DATA2	 + " text,"      	   //  WALLET_NAME
				+ TableField.FIELD_RESERVED_DATA3	 + " integer,"         //  WALLET_SELECT
				+ TableField.FIELD_RESERVED_DATA4	 + " integer,"         //  WALLET_EXTRA
				+ TableField.FIELD_RESERVED_DATA5	 + " text,"            //  WALLET_BACKUP
				+ TableField.FIELD_RESERVED_DATA6	 + " text,"            //  WALLET_IMAGE
				+ TableField.FIELD_RESERVED_DATA7	 + " text,"            //  LOCAL＿ID
				+ TableField.FIELD_RESERVED_DATA8	 + " text,"            //  password info
				+ TableField.FIELD_RESERVED_DATA9	 + " integer,"
				+ TableField.FIELD_RESERVED_DATA10	 + " integer,"
				+ TableField.FIELD_RESERVED_DATA11	 + " text,"
				+ TableField.FIELD_RESERVED_DATA12	 + " text)"
				;
		db.execSQL(sql_wallet);
	}

	private void createMnemonicTable(SQLiteDatabase db){
		//trams table
		String sql_trans  = "CREATE TABLE IF NOT EXISTS "
				+ TableField.TABLE_MNEMONIC			 + "("
				+ TableField._ID					 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TableField.FIELD_RESERVED_DATA1	 + " text,"      // wallet address
				+ TableField.FIELD_RESERVED_DATA2	 + " text,"      //mnemonic
				+ TableField.FIELD_RESERVED_DATA3	 + " text,"      //key
				+ TableField.FIELD_RESERVED_DATA4	 + " text,"
				+ TableField.FIELD_RESERVED_DATA5	 + " text,"
				+ TableField.FIELD_RESERVED_DATA6	 + " text,"
				+ TableField.FIELD_RESERVED_DATA7	 + " text,"
				+ TableField.FIELD_RESERVED_DATA8	 + " text,"
				+ TableField.FIELD_RESERVED_DATA9	 + " integer,"
				+ TableField.FIELD_RESERVED_DATA10 + " integer)"
				;

		db.execSQL(sql_trans);
	}

}
