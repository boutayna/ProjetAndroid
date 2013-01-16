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

import com.boutayna.monappli.adapters.FavorisAdapter;
import com.boutayna.monappli.domain.Result;
import com.example.monappli.R;
import com.example.monappli.R.id;
import com.example.monappli.R.layout;
import com.example.monappli.R.style;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FavorisActivity extends Activity implements OnSharedPreferenceChangeListener {

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
	ListView myListView;
	Intent intent;
	Result result;
	FavorisAdapter adapter;
	List<Result> results = new ArrayList<Result>();
	// Search EditText
    EditText inputSearch;
    SharedPreferences settings;
    String favoris;
    boolean changed=false;

	public static final String TAG = "MyActivity";
	
	@Override
	protected void onResume() {
	    super.onResume();
	    // Set up a listener whenever a key changes
	    settings = getApplicationContext().getSharedPreferences("PRIVATE_FAVORIS", 0);
	    favoris=settings.getString("favoris", "");
	    results.clear();
	    Log.d(TAG,"onResume"+favoris);
	    JSONObject json = getJSONFromUrl("http://cci.corellis.eu/pois.php");
		if (json == null)
			Log.d(TAG, "null");

		try {

			JSONArray jsonArray = json.getJSONArray("results");

			Log.i(TAG, "Number of entries" + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject j = jsonArray.getJSONObject(i);
				if (favoris.indexOf(";" + String.valueOf(j.getInt("id"))) >= 0) {
        			result = new Result(j.getInt(TAG_ID),
        					j.getString(TAG_NAME), 
        					j.getString(TAG_SECTEUR),
        					j.getString(TAG_QUARTIER),
        					j.getString(TAG_INFORMATIONS),
        					j.getString(TAG_IMAGE),
        					j.getDouble(TAG_LAT), 
        					j.getDouble(TAG_LON),
        					j.getString(TAG_CATEGORIE));
        			results.add(result);
    			}
				//Log.d(TAG, j.toString());
				
			}

			myListView = (ListView) findViewById(R.id.listView1);
			adapter = new FavorisAdapter(this, results);
			setTheme(R.style.WidgetBackground);

			myListView.setAdapter(adapter);
			
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
		setContentView(R.layout.favoris_layout);
		
	}

	// constructor
	public FavorisActivity() {

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

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		adapter.notifyDataSetChanged();
		Log.d(TAG,"notify");
		
	}
}
