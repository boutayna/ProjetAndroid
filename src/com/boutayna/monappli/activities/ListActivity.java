package com.boutayna.monappli.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.boutayna.monappli.adapters.ImageAdapter;
import com.boutayna.monappli.domain.Result;
import com.example.monappli.R;
import com.example.monappli.R.id;
import com.example.monappli.R.layout;
import com.example.monappli.R.style;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ListActivity extends Activity {

	static JSONObject jObj = null;
	static String json = "";
	private static final String TAG_QUARTIER = "quartier";
	private static final String TAG_SECTEUR = "secteur";
	private static final String TAG_INFORMATIONS = "informations";
	private static final String TAG_NAME = "nom";
	private static final String TAG_IMAGE = "image";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LON = "lon";
	private static final String TAG_ID = "id";
	private static final String TAG_CATEGORIE = "categorie_id";
	private Spinner spinner;
	ListView myListView;
	Intent intent;
	Result result;
	ImageAdapter adapter;
	List<Result> results = new ArrayList<Result>();
	// Search EditText
    EditText inputSearch;

	public static final String TAG = "MyActivity";
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "OnResume");
		results.clear();
		addItemsOnSpinner();
		addListenerOnSpinnerItemSelection();
		
		// getting JSON string from URL
		JSONObject json = getJSONFromUrl("http://cci.corellis.eu/pois.php");
		if (json == null)
			Log.d(TAG, "null");

		try {

			JSONArray jsonArray = json.getJSONArray("results");

			Log.i(TAG, "Number of entries" + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject j = jsonArray.getJSONObject(i);
				//Log.d(TAG, j.toString());
				result = new Result(j.getInt(TAG_ID),j.getString(TAG_NAME),
						j.getString(TAG_SECTEUR), j.getString(TAG_QUARTIER),j.getString(TAG_INFORMATIONS),j.getString(TAG_IMAGE),j.getDouble(TAG_LON),j.getDouble(TAG_LAT),j.getString(TAG_CATEGORIE));
				results.add(result);

			}

			myListView = (ListView) findViewById(R.id.listView1);
			inputSearch = (EditText) findViewById(R.id.inputSearch);
			adapter = new ImageAdapter(this, results);
			myListView.setTextFilterEnabled(true);
			setTheme(R.style.WidgetBackground);

			myListView.setAdapter(adapter);
			
			inputSearch.addTextChangedListener(new TextWatcher() {
				 
	            @Override
	            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	                // When user changed the Text
	            	 adapter.filter(cs.toString());
	            	 Log.i(TAG, "*** Search value changed: " + cs.toString());
 
	               
	                
	            }
	 
	            @Override
	            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                    int arg3) {
	                // TODO Auto-generated method stub
	 
	            }
	 
	            @Override
	            public void afterTextChanged(Editable arg0) {
	                // TODO Auto-generated method stub
	            	
		            
	            }

	        });
			
			intent = new Intent(this, DetailActivity.class);

			myListView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// When clicked, show a toast with the TextView text
					

					intent.putExtra("name",((Result)(adapter.getItem(position))).getName());
					intent.putExtra("quartier", ((Result)(adapter.getItem(position))).getQuartier());
					intent.putExtra("secteur",((Result)(adapter.getItem(position))).getSecteur());
					intent.putExtra("image", ((Result)(adapter.getItem(position))).getUrlImage());
					intent.putExtra("infos", ((Result)(adapter.getItem(position))).getInformations());
					intent.putExtra("lon", ((Result)(adapter.getItem(position))).getLon());
					intent.putExtra("lat", ((Result)(adapter.getItem(position))).getLat());
					intent.putExtra("id", ((Result)(adapter.getItem(position))).getId());
					intent.putExtra("categorie", ((Result)(adapter.getItem(position))).getCategorie());
					
					startActivity(intent);

				}
			});
			//

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.mylistview);

		

	}
	
	public void addItemsOnSpinner() {
		 
		spinner = (Spinner) findViewById(R.id.spinner);
		
		List<String> list = new ArrayList<String>();
		list.add("all categories");
		list.add("catégorie 1");
		list.add("catégorie 2");
		list.add("catégorie 3");
		list.add("catégorie 4");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	  }
	public void addListenerOnSpinnerItemSelection() {
		spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	  }
	// constructor
	public ListActivity() {

	}

	public JSONObject getJSONFromUrl(String url) {

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse != null) {
				String line = "";
				InputStream is = httpResponse.getEntity().getContent();
				line = convertStreamToString(is);
				// Toast.makeText(this, line, Toast.LENGTH_SHORT).show();

				jObj = new JSONObject(line);

			} else {
				Toast.makeText(this, "enable to complete your request",
						Toast.LENGTH_SHORT).show();

			}

		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jObj;

	}

	public String convertStreamToString(InputStream is) {
		String line = "";
		StringBuilder total = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		try {
			while ((line = rd.readLine()) != null) {
				total.append(line + "/n");
			}

		} catch (Exception e) {
			Toast.makeText(this, "Stream Exception", Toast.LENGTH_SHORT).show();
		}
		return total.toString();

	}
	class CustomOnItemSelectedListener implements OnItemSelectedListener {
		 
		  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
			adapter.filterCategorie(parent.getItemAtPosition(pos).toString());
		  }
		 
		  @Override
		  public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		  }
		 
		}
}
