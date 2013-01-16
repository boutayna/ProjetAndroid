package com.boutayna.monappli.activities;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.boutayna.monappli.overlays.MyItemizedOverlay;
import com.example.monappli.R;
import com.example.monappli.R.drawable;
import com.example.monappli.R.id;
import com.example.monappli.R.layout;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class CarteActivity extends MapActivity {
	
	MapController mapController=null;
	public static final String TAG = "MyActivity";

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
		mapView= (MapView) findViewById(R.id.map);
		mapController=mapView.getController();
		Bundle extras = getIntent().getExtras();
		Double lon = extras.getDouble("lon")*1E6;
		Double lat = extras.getDouble("lat")*1E6;
		String name = extras.getString("name");
		String infos = extras.getString("infos");
		 //sets the zoom to see the location closer
        mapView.getController().setZoom(12);
 
        //this will let you to zoom in or out using the controllers
        mapView.setBuiltInZoomControls(true);
 
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_marqueur);
         
        MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(drawable, this);

		mapView.setBuiltInZoomControls(true) ;
		GeoPoint point = new GeoPoint(lat.intValue(), lon.intValue());
		mapView.setBuiltInZoomControls(true) ;
		mapController.setCenter(point);
		mapController.setZoom(8);
		mapController.animateTo(point);
		OverlayItem overlayitem = new OverlayItem(point,name,infos);
		itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
		
		Log.d(TAG, "animate");
	}
}
