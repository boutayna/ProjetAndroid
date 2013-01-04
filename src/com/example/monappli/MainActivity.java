package com.example.monappli;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	private TabHost tabHost;
	private TabSpec tabSpec;

	@Override

	public void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	Intent intentList = new Intent(this, ListActivity.class);
	Intent intentMap= new Intent(this, MaMapActivity.class);


	tabHost = getTabHost();
	//intentList.putExtra("valeur", "liste");

	tabSpec = tabHost.newTabSpec("liste").setIndicator("Liste",getResources().getDrawable(R.drawable.iconliste)).setContent(intentList);
	tabHost.addTab(tabSpec);

	

	tabSpec = tabHost.newTabSpec("carte").setIndicator("Carte",getResources().getDrawable(R.drawable.iconcarte)).setContent(intentMap);
	tabHost.addTab(tabSpec);
	
	

	
	tabSpec = tabHost.newTabSpec("favoris").setIndicator("Favoris",getResources().getDrawable(R.drawable.iconfavoris)).setContent(intentList);
	tabHost.addTab(tabSpec);
	
	for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
    {
    if (i == 0) tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF8C00"));

    else tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#000000"));
    }  
	
	tabHost.setOnTabChangedListener(new OnTabChangeListener(){
		@Override
		public void onTabChanged(String tabId) {
		    // TODO Auto-generated method stub
		     for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
		        {
		          tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#000000")); //unselected
		        }
		        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#FF8C00")); // selected
		}
		});

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
