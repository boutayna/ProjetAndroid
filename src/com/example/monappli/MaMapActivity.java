package com.example.monappli;

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

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MaMapActivity extends MapActivity{
	
	MapController mapController=null;
	MyLocationOverlay myLocation=null;
	private List<Overlay> mapOverlays;
	static Context context;
	static JSONObject jObj = null;
	static String json = "";
	private static final String TAG_QUARTIER = "quartier";
	private static final String TAG_SECTEUR = "secteur";
	private static final String TAG_NAME = "nom";
	private static final String TAG_IMAGE = "image";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LON = "lon";
	Result result;
	List<Result> results = new ArrayList<Result>();
	public static final String TAG = "MyActivity";

	private List<OverlayItem> items = new ArrayList<OverlayItem>();
	private MyItemizedOverlay itemizedOverlay1;
	private MapView mapView;
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.map_layout);
		
		/***Localisation de l'utilisateur*******/
		context=getApplicationContext();
		mapView= (MapView) findViewById(R.id.map);
		mapController=mapView.getController();
		mapView.setBuiltInZoomControls(true) ;
		
		myLocation= new MyLocationOverlay(getApplicationContext(),mapView);
		mapView.getOverlays().add(myLocation);
		myLocation.enableMyLocation();
		
		myLocation.runOnFirstFix(new Runnable() {
	
			@Override
			public void run() {
				mapController.animateTo(myLocation.getMyLocation());
				mapController.setZoom(17);
				
			}
		});
		
		
		
		JSONObject json = getJSONFromUrl("http://cci.corellis.eu/pois.php");
		if (json == null)
			Log.d(TAG, "null");

		try {

			JSONArray jsonArray = json.getJSONArray("results");

			Log.i(TAG, "Number of entries" + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject j = jsonArray.getJSONObject(i);
				Log.d(TAG, j.toString());		
				Double lat = j.getDouble(TAG_LAT)*1E6 ;
				Double lon= j.getDouble(TAG_LON)*1E6;
				items.add(new OverlayItem(new GeoPoint(lat.intValue(),lon.intValue()),j.getString(TAG_NAME),j.getString(TAG_SECTEUR)+""+j.getString(TAG_QUARTIER)));	
			}
		}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		setOverly();

	}
	
	public void setOverly(){
		mapOverlays = mapView.getOverlays();
		Drawable drawable1=this.getResources().getDrawable(R.drawable.ic_marqueur);
		itemizedOverlay1 = new MyItemizedOverlay(drawable1);
		for(int i=0;i<items.size();i++){
			itemizedOverlay1.addOverlay(items.get(i));
		}
		mapOverlays.add(itemizedOverlay1);
		mapView.postInvalidate();
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
	
}
