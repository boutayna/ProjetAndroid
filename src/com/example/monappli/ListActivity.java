package com.example.monappli;


 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
 
public class ListActivity extends Activity {

    static JSONObject jObj = null;
    static String json = "";
    private static final String TAG_QUARTIER = "quartier";
    private static final String TAG_SECTEUR = "secteur";
    private static final String TAG_INFORMATIONS = "informations";
    private static final String TAG_NAME = "nom";
    ListView myListView;
    Intent intent;
    
    public static final String TAG="MyActivity";
    ArrayList<HashMap<String, String>> List = new ArrayList<HashMap<String, String>>();
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.mylistview);
        Log.d(TAG,"PASS0");
     // getting JSON string from URL
        JSONObject json = getJSONFromUrl("http://cci.corellis.eu/pois.php");
        if(json == null)
        	Log.d(TAG,"null");
        Log.d(TAG,"PASS1");
        try {
        	Log.d(TAG,"PASS2");
        	
			JSONArray jsonArray = json.getJSONArray("results");
			Log.d(TAG,"PASS3");
			Log.i(TAG,"Number of entries"+ jsonArray.length());
			for(int i=0;i<jsonArray.length();i++){
				
				JSONObject j=jsonArray.getJSONObject(i);
				HashMap<String, String> map=new HashMap<String,String>();
				
				map.put(TAG_NAME,j.getString(TAG_NAME));
				map.put(TAG_QUARTIER,j.getString(TAG_QUARTIER));
				map.put(TAG_SECTEUR,j.getString(TAG_SECTEUR));
				map.put(TAG_INFORMATIONS,j.getString(TAG_INFORMATIONS));
				
				List.add(map);
				
			}

			myListView = (ListView) findViewById(R.id.listView1);
			//ListView myListView= getListView();
			ListAdapter adapter = new SimpleAdapter(this,List,
	                R.layout.json_layout,
	                new String[] {  TAG_NAME,TAG_QUARTIER, TAG_SECTEUR }, new int[] {
	                        R.id.name, R.id.car, R.id.sec, });
			myListView.setTextFilterEnabled(true);
			setTheme(R.style.WidgetBackground);
	 
	        myListView.setAdapter(adapter);
	        
	        intent = new Intent(this, DetailActivity.class); 
	        
	        myListView.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                // When clicked, show a toast with the TextView text
	            	HashMap<String, String> objet = List.get(position);
	            	
	            	intent.putExtra("name",objet.get("nom"));
	            	intent.putExtra("quartier",objet.get("quartier"));
	            	intent.putExtra("secteur",objet.get("secteur"));
	            	intent.putExtra("infos",objet.get("informations"));
	            	startActivity(intent);
	            	
	            	
	            	
	                Toast.makeText(getApplicationContext(),objet.get("nom"),
	                    Toast.LENGTH_SHORT).show();}});
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
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
            if(httpResponse != null){
            	String line = "";
            	InputStream is=httpResponse.getEntity().getContent();
            	line = convertStreamToString(is);
            	//Toast.makeText(this, line, Toast.LENGTH_SHORT).show();
            	
            	jObj = new JSONObject(line);
            
 
            }
            else{
            	Toast.makeText(this, "enable to complete your request", Toast.LENGTH_SHORT).show();
            	
            }

        } 
        catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    	return jObj;
 
        
    }
    
    public String convertStreamToString(InputStream is){
    	String line="";
    	StringBuilder total=new StringBuilder();
    	BufferedReader rd=new BufferedReader(new InputStreamReader(is));
    	try{
    		while((line=rd.readLine())!= null){
    			total.append(line +"/n");
    		}
    		
    	}
    	catch(Exception e){
    		Toast.makeText(this, "Stream Exception",
    	            Toast.LENGTH_SHORT).show();
    	}
		return total.toString();
    	
    }
}