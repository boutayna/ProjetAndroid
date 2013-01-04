
package com.example.monappli;
import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;


public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem>{

	private ArrayList<OverlayItem> myOverlays;
	
	
	public MyItemizedOverlay(Drawable drawable) {
		super(boundCenterBottom(drawable));
		myOverlays = new ArrayList<OverlayItem>();
	}

	public void addOverlay (OverlayItem overlay) {
		myOverlays.add(overlay);
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return myOverlays.get(i);
	}
	
	@Override
	protected boolean onTap(int i){
		GeoPoint gpoint = myOverlays.get(i).getPoint();
		double lat = gpoint.getLatitudeE6()/1e6;
		double lon = gpoint.getLongitudeE6()/1e6;
		String toast = "Title : " + myOverlays.get(i).getTitle();
		toast += "\nText: "+myOverlays.get(i).getSnippet();
		toast += "\nSymbol coordinates : Lat = " +lat+ " Lon= "+lon+"(microdegrees)";
		Toast.makeText(MaMapActivity.context, toast, Toast.LENGTH_LONG).show();
		return(true);
	}
	

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return myOverlays.size();
	}

}
