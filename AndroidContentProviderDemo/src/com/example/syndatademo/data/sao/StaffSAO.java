package com.example.syndatademo.data.sao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.syndatademo.business.model.Staff;
import com.example.syndatademo.data.dao.DatabaseHelper;
import com.qsoft.androidnetwork.ServiceHelper;

public class StaffSAO {
	private ServiceHelper serviceHelper;

	public StaffSAO() {
		serviceHelper = new ServiceHelper();
	}

	public List<Staff> getAllStaff(String url) throws ConnectTimeoutException,
			IOException, JSONException {

		List<Staff> staffs = new ArrayList<Staff>();
		String data = serviceHelper.httpGetString(url);
		JSONArray list = new JSONArray(data);
		int length = list.length();
		for (int i = 0; i < length; i++) {
			JSONObject staff = list.getJSONObject(i);
			staffs.add(parserStaff(staff));
		}
		return staffs;
	}

	private Staff parserStaff(JSONObject json) throws JSONException {
		Staff staff = new Staff();
		staff.setName(json.getString(DatabaseHelper.NAME));
		staff.setAddress(json.getString(DatabaseHelper.ADDRESS));
		return staff;
	}
}
