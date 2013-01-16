package com.boutayna.monappli.overlays;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverlayCarte extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> myOverlays;
	private Context context;
	public static final String TAG = "MyActivity";
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	String favoris;
	public MyItemizedOverlayCarte(Drawable drawable, Context context) {
		super(boundCenterBottom(drawable));
		myOverlays = new ArrayList<OverlayItem>();
		this.context = context;
	}

	public void addOverlay(OverlayItem overlay) {
		myOverlays.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return myOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return myOverlays.size();
	}

}
