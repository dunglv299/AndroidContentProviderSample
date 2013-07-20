package com.qsoft.androidnetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author quynhlt
 * 
 *         HOW TO USE?
 * 
 *         NetworkAvailable networkAvailable = new NetworkAvailable(context);
 * 
 *         If networkAvailable.isAvailable() return true network is available.
 * 
 */
public class NetworkAvailable {
	private ConnectivityManager connectivityMng;
	private boolean connected = false;

	/**
	 * @param context
	 *            Context use to get system service.
	 */
	public NetworkAvailable(Context context) {
		try {
			connectivityMng = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityMng.getActiveNetworkInfo();
			connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
		} catch (Exception e) {
			connected = false;
		}
	}

	/**
	 * @return true if network is available.
	 */
	public boolean isAvailable() {
		return connected;
	}
}
