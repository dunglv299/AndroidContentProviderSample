package com.example.syndatademo;

import java.io.IOException;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.syndatademo.business.model.Staff;
import com.example.syndatademo.data.DatabaseHelper;
import com.example.syndatademo.data.dao.StaffProvider;
import com.example.syndatademo.data.sao.StaffSAO;
import com.qsoft.androidnetwork.AsyncTaskExecute;

public class MainActivity extends FragmentActivity implements
		LoaderManager.LoaderCallbacks<Cursor>, OnClickListener {
	private ListView listView;
	private DatabaseHelper mDatabaseHelper;
	private List<Staff> listStaff;
	// private ListViewAdapter adapter;
	SimpleCursorAdapter mAdapter;
	private Button btnUpdate;
	private Button btnDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		listView = (ListView) findViewById(R.id.listView1);
		btnUpdate = (Button) findViewById(R.id.update_btn);
		btnDelete = (Button) findViewById(R.id.delete_btn);
		mDatabaseHelper = new DatabaseHelper(getApplicationContext());

		AsyncTaskExecute insertTask = new AsyncTaskExecute("loadComplete",
				"loadError", this);
		insertTask.execute("doLoad");
		setAdapter();
		btnUpdate.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.update_btn:
			update(1);
			break;
		case R.id.delete_btn:
			delete(3);
			break;
		default:
			break;
		}
	}

	private void setAdapter() {
		String[] uiBindFrom = { DatabaseHelper.NAME, DatabaseHelper.ADDRESS };
		int[] uiBindTo = { R.id.name_tv, R.id.address_tv };

		getSupportLoaderManager().initLoader(0, null, this);

		mAdapter = new SimpleCursorAdapter(getApplicationContext(),
				R.layout.single_item_listview, null, uiBindFrom, uiBindTo,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		// bind values fom the Cursor to views
		ViewBinder viewBinder = new ViewBinder() {

			public boolean setViewValue(View view, Cursor cursor,
					int columnIndex) {
				int viewId = view.getId();
				switch (viewId) {
				case R.id.name_tv:
					TextView nameTextView = (TextView) view
							.findViewById(R.id.name_tv);
					String name = cursor.getString(columnIndex);
					Log.e("name", name);
					nameTextView.setText("Name: " + name);
					break;
				case R.id.address_tv:
					TextView addressTextView = (TextView) view
							.findViewById(R.id.address_tv);
					String address = cursor.getString(columnIndex);
					Log.e("address", address);
					addressTextView.setText("Address: " + address);
					break;
				}

				return false;
			}
		};
		mAdapter.setViewBinder(viewBinder);
		listView.setAdapter(mAdapter);
	}

	public void update(int id) {
		// Defines an object to contain the updated values
		Log.e("update", "update");
		ContentValues mUpdateValues = new ContentValues();
		mUpdateValues.put(DatabaseHelper.NAME, "Dung lv");
		mUpdateValues.put(DatabaseHelper.ADDRESS, "abc");
		Uri uri = Uri.parse(StaffProvider.CONTENT_URI + "/" + id);
		getContentResolver().update(uri, mUpdateValues, null, null);
	}

	public void delete(int id) {
		// Defines an object to contain the deleted values
		Uri uri = Uri.parse(StaffProvider.CONTENT_URI + "/" + id);
		getContentResolver().delete(uri, null, null);
	}

	public void doLoad() {
		try {
			listStaff = new StaffSAO()
					.getAllStaff("http://192.168.1.36/accounts.json");
		} catch (ConnectTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void loadComplete() {
		Log.e("loadComplete", "aaaaassssss" + listStaff);
		for (Staff staff : listStaff) {
			ContentValues values = new ContentValues();
			values.put("name", staff.getName());
			values.put("address", staff.getAddress());
			getContentResolver().insert(StaffProvider.CONTENT_URI, values);
		}
	}

	public void loadError() {
		Log.e("run", "aaa loadError err");
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		CursorLoader cursorLoader = new CursorLoader(this,
				StaffProvider.CONTENT_URI, null, null, null, null);
		Log.e("uri", StaffProvider.CONTENT_URI + "");
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
	}

}
