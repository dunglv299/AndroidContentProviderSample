package com.qsoft.androidnetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.util.Log;

/**
 * @author quynhlt
 * 
 *         HOW TO USE
 * 
 *         ServiceHelper serviceHelper = new ServiceHelper();
 * 
 *         String response = serviceHelper.httpGetString(url);
 * 
 *         try {
 * 
 *         // create a json object from a string
 * 
 *         JSONObject jsonObj = new JSONObject(response);
 * 
 *         //TODO
 * 
 *         } catch (JSONException e) { e.printStackTrace(); }
 * 
 * 
 */
public class ServiceHelper {
	private final String tag = "[ServiceHelper]";
	public static final int TIME_OUT_CON = 10000;
	public static final int TIME_OUT_SOCKET = 10000;
	public static String SESSION_VALUE = "";
	public static String SESSION_NAME = "SESSION_VALUE";

	/**
	 * 
	 * The GET method means retrieve whatever information (in the form of an
	 * entity) is identified by the Request-URI. If the Request-URI refers to a
	 * data-producing process, it is the produced data which shall be returned
	 * as the entity in the response and not the source text of the process,
	 * unless that text happens to be the output of the process.
	 * 
	 * @param url
	 *            A specific url to send get request.
	 * 
	 * @return String An HTTP response.
	 * @throws IOException
	 *             The exception related to IO. Signals that the target server
	 *             failed to respond with a valid HTTP response.
	 * 
	 */
	public String httpGetString(String url) throws ConnectTimeoutException, IOException {
		StringBuilder builder = new StringBuilder();
		HttpEntity entity = httpGetEntity(url);
		if (entity != null) {
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			Log.e("[URL HttpGet]", url + "");
			Log.e("RESPONSE HttpGet", builder.toString() + "");
		}
		return builder.toString();

	}

	/**
	 * 
	 * The GET method means retrieve whatever information (in the form of an
	 * entity) is identified by the Request-URI. If the Request-URI refers to a
	 * data-producing process, it is the produced data which shall be returned
	 * as the entity in the response and not the source text of the process,
	 * unless that text happens to be the output of the process.
	 * 
	 * @param url
	 *            A specific url to send get request.
	 * 
	 * @return HttpEntity An HTTP response.
	 * @throws IOException
	 *             The exception related to IO. Signals that the target server
	 *             failed to respond with a valid HTTP response.
	 * 
	 */
	public HttpEntity httpGetEntity(String url) throws ConnectTimeoutException, IOException {
		HttpEntity entity = null;
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Cookie", SESSION_VALUE);
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, TIME_OUT_CON);
		HttpConnectionParams.setSoTimeout(httpParameters, TIME_OUT_SOCKET);
		DefaultHttpClient client = new DefaultHttpClient(httpParameters);
		HttpResponse response = client.execute(httpGet);
		Log.d("Cookie httpGet", client.getCookieStore().getCookies() + "");
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		Log.i("[URL statusCode]", statusCode + "");
		if (statusCode == 200) {
			entity = response.getEntity();
		}
		return entity;
	}

	/**
	 * 
	 * The POST method is used to request that the origin server accept the
	 * entity enclosed in the request as a new subordinate of the resource
	 * identified by the Request-URI in the Request-Line.
	 * 
	 * @param url
	 *            A specific url to send post request.
	 * @param params
	 *            A List is a collection parameters to send.
	 * @return String An HTTP response.
	 * @throws IOException
	 *             The exception related to IO. Signals that the target server
	 *             failed to respond with a valid HTTP response.
	 */

	public String httpPostString(String url, List<NameValuePair> params) throws ConnectTimeoutException, IOException {
		StringBuilder builder = new StringBuilder();
		HttpEntity entity = httpPostEntity(url, params);
		if (entity != null) {
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			Log.e(tag, "RESPONSE httpPost" + builder.toString());
		}
		return builder.toString();
	}

	/**
	 * 
	 * The POST method is used to request that the origin server accept the
	 * entity enclosed in the request as a new subordinate of the resource
	 * identified by the Request-URI in the Request-Line.
	 * 
	 * @param url
	 *            A specific url to send post request.
	 * @param params
	 *            A List is a collection parameters to send.
	 * @return HttpEntity An HTTP response.
	 * @throws IOException
	 *             The exception related to IO. Signals that the target server
	 *             failed to respond with a valid HTTP response.
	 */

	public HttpEntity httpPostEntity(String url, List<NameValuePair> params) throws ConnectTimeoutException, IOException {
		// Create a new HttpClient and Post Header
		HttpEntity entity = null;
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Cookie", SESSION_VALUE);
		httppost.setHeader("x-requested-with", "XMLHttpRequest");
		httppost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		// Add your data
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, TIME_OUT_CON);
		HttpConnectionParams.setSoTimeout(httpParameters, TIME_OUT_SOCKET);
		DefaultHttpClient client = new DefaultHttpClient(httpParameters);
		httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		Log.e(tag, "[URL HttpPost]" + url + ", sesion= " + SESSION_VALUE);
		Log.e(tag, "[SEND HttpPost]" + params.toString());
		// Execute HTTP Post Request
		HttpResponse response = client.execute(httppost);
		Log.d("Cookie httpGet", client.getCookieStore().getCookies() + "");
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		Log.i("[URL statusCode]", statusCode + "");
		if (statusCode == 200) {
			entity = response.getEntity();
		}
		return entity;
	}

	/**
	 * The POST method is used to request that the origin server accept the
	 * entity enclosed in the request as a new subordinate of the resource
	 * identified by the Request-URI in the Request-Line.
	 * 
	 * @param url
	 *            A specific url to send post request.
	 * @param entity
	 *            The entity to send. Support send params as String and file -
	 *            multipart data
	 * @return String An HTTP response.
	 * @throws IOException
	 *             The exception related to IO. Signals that the target server
	 *             failed to respond with a valid HTTP response.
	 */

	public String httpPostMultipartString(String url, HttpEntity entity) throws ConnectTimeoutException, IOException {
		StringBuilder builder = new StringBuilder();
		HttpEntity result = httpPostMultipartEntity(url, entity);
		if (result != null) {
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			Log.e(tag, "RESPONSE httpPost " + builder.toString());
		}
		return builder.toString();
	}

	/**
	 * The POST method is used to request that the origin server accept the
	 * entity enclosed in the request as a new subordinate of the resource
	 * identified by the Request-URI in the Request-Line.
	 * 
	 * @param url
	 *            A specific url to send post request.
	 * @param entity
	 *            The entity to send. Support send params as String and file -
	 *            multipart data
	 * @return HttpEntity An HTTP response.
	 * @throws IOException
	 *             The exception related to IO. Signals that the target server
	 *             failed to respond with a valid HTTP response.
	 */

	public HttpEntity httpPostMultipartEntity(String url, HttpEntity entity) throws ConnectTimeoutException, IOException {
		// Create a new HttpClient and Post Header
		HttpEntity result = null;
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Cookie", SESSION_VALUE);
		// Add your data
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, TIME_OUT_CON);
		HttpConnectionParams.setSoTimeout(httpParameters, TIME_OUT_SOCKET);
		DefaultHttpClient client = new DefaultHttpClient(httpParameters);
		httppost.setEntity(entity);
		Log.e(tag, "[URL HttpPost] " + url);
		Log.e(tag, "[SEND HttpPost] " + entity.toString());
		// Execute HTTP Post Request
		HttpResponse response = client.execute(httppost);
		Log.d("Cookie httpGet", client.getCookieStore().getCookies() + "");
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		Log.i("[URL statusCode]", statusCode + "");
		if (statusCode == 200) {
			result = response.getEntity();
		}
		return result;
	}

	/**
	 * The DELETE method requests that the origin server delete the resource
	 * identified by the Request-URI. [...] The client cannot be guaranteed that
	 * the operation has been carried out, even if the status code returned from
	 * the origin server indicates that the action has been completed
	 * successfully.
	 * 
	 * Summary
	 * 
	 * @param url
	 *            A specific url to send post request.
	 * @param newentity
	 *            The entity to send.
	 * @return String An HTTP response.
	 * @throws IOException
	 *             The exception related to IO. Signals that the target server
	 *             failed to respond with a valid HTTP response.
	 */
	public String httpDelete(String url) throws ConnectTimeoutException, IOException {
		StringBuilder builder = new StringBuilder();
		HttpEntity entity = httpDeleteEntity(url);
		if (entity != null) {
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			Log.e(tag + ", [RESPONSE HttpDelete]", builder.toString());
		}
		return builder.toString();
	}

	/**
	 * The DELETE method requests that the origin server delete the resource
	 * identified by the Request-URI. [...] The client cannot be guaranteed that
	 * the operation has been carried out, even if the status code returned from
	 * the origin server indicates that the action has been completed
	 * successfully.
	 * 
	 * Summary
	 * 
	 * @param url
	 *            A specific url to send post request.
	 * @param newentity
	 *            The entity to send.
	 * @return HttpEntity An HTTP response.
	 * @throws IOException
	 *             The exception related to IO. Signals that the target server
	 *             failed to respond with a valid HTTP response.
	 */
	public HttpEntity httpDeleteEntity(String url) throws ConnectTimeoutException, IOException {
		// Create a new HttpClient and Delete Header
		HttpEntity entity = null;
		HttpDelete httpdelete = new HttpDelete(url);
		Log.e(tag + ", [URL HttpDelete]", url);
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, TIME_OUT_CON);
		HttpConnectionParams.setSoTimeout(httpParameters, TIME_OUT_SOCKET);
		DefaultHttpClient client = new DefaultHttpClient(httpParameters);
		httpdelete.setParams(httpParameters);
		HttpResponse response = client.execute(httpdelete);
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		Log.i("[URL statusCode]", statusCode + "");
		if (statusCode == 200) {
			entity = response.getEntity();
		}
		return entity;
	}
}
