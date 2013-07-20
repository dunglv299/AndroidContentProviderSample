/**
 * 
 */
package com.qsoft.androidnetwork;

import android.os.AsyncTask;
import android.util.Log;

import com.qsoft.androidcallback.CallFunction;

/**
 * @author quynhlt
 * 
 */
public class AsyncTaskExecute extends AsyncTask<String, Void, Boolean> {
	private String methodComplete;
	private String methodError;
	private Object className;

	/**
	 * @param methodComplete
	 *            method to call when complete execute first method
	 * @param className
	 *            The name of class to execute AsyncTask
	 */
	public AsyncTaskExecute(String methodComplete, String methodError, Object className) {
		this.methodComplete = methodComplete;
		this.methodError = methodError;
		this.className = className;
	}

	/**
	 * @param firstMethob
	 *            method to call execute a instance of AsyncTaskExecute class
	 * @return Void
	 */

	protected Boolean doInBackground(String... firstMethob) {
		try {
			Log.e("Method", firstMethob[0]);
			new CallFunction(firstMethob[0], className, null);
			return true;
		} catch (Exception e) {
			Log.e("AsyncTaskExecute Exception", e.getMessage());
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		Log.e("onPostExecute", result + "");
		if (result == null) {
			new CallFunction( methodError, className, null);
		}
		new CallFunction(methodComplete, className, null);
	}
}
