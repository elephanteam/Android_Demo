package com.elephantgroup.one.util;

import android.content.Context;

import com.elephantgroup.one.R;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Abnormal log collection classes
 */
public class CrashHandler implements UncaughtExceptionHandler {

	/**
	 * CrashHandler instance
	 * */
	private static CrashHandler instance;

	private Context mContext;

	/**
	 * The system default UncaughtException processing class
	 * */
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	/** Ensure that only a CrashHandler instance */
	private CrashHandler() { }

	/**
	 * Get CrashHandler instance, singleton pattern
	 * */
	public static CrashHandler getInstance() {
		if (instance == null)
			instance = new CrashHandler();
		return instance;
	}

	/**
	 * Initialization, register the Context object,
	 * access to the system default UncaughtException processor,
	 * sets the default this CrashHandler for application processor
	 */
	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * When UncaughtException happened into the function to deal with
	 */
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			//If the user does not deal with the system default exception handler to handle
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	/**
	 * custom error handling, collection error message send error reports are completed in this operation.
	 * The developer can customize according to the exception handling logic
	 * @ param ex
	 * @ return true: if the exception handling information;Otherwise it returns false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return true;
		}
		ex.printStackTrace();
	    saveCrashInfoToFile(ex);
		return true;
	}

	/**
	 * Save error information to a file
	 */
	private void saveCrashInfoToFile(Throwable ex) {
		String chanel = "Limit";
		try {
			String result = getErrorInfo(ex);
			String sb = mContext.getString(R.string.crash_mobile_models) + android.os.Build.MODEL + "\n" +
					mContext.getString(R.string.crash_system_version) + android.os.Build.VERSION.RELEASE + "\n" +
					mContext.getString(R.string.crash_app_version) + Utils.getVersionName(mContext) + "\n" +
					mContext.getString(R.string.crash_app_channel) + chanel + "\n" +
					mContext.getString(R.string.crash_crash_time) + DateFormatUtil.yMdHm(System.currentTimeMillis() / 1000) + "\n" +
					result;
			SDCardCtrl.saveCrashInfoToFile(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 
　　* Get the wrong information
　　* @param arg1 
　　* @return 
　　*/ 
	private String getErrorInfo(Throwable arg1) {
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		arg1.printStackTrace(pw); 
		pw.close(); 
		String error= writer.toString();
		return error; 
	} 
}