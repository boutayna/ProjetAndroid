package com.example.monappli;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	private TabHost tabHost;
	private TabSpec tabSpec;

	@Override

	public void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	Intent intent = new Intent(this, ListActivity.class);


	tabHost = getTabHost();
	intent.putExtra("valeur", "liste");

	tabSpec = tabHost.newTabSpec("liste").setIndicator("Liste",getResources().getDrawable(R.drawable.iconliste)).setContent(intent);
	tabHost.addTab(tabSpec);

	intent.putExtra("valeur", "carte");

	tabSpec = tabHost.newTabSpec("carte").setIndicator("Carte",getResources().getDrawable(R.drawable.iconcarte)).setContent(intent);
	tabHost.addTab(tabSpec);
	
	intent.putExtra("valeur", "favoris");

	
	tabSpec = tabHost.newTabSpec("favoris").setIndicator("Favoris",getResources().getDrawable(R.drawable.iconfavoris)).setContent(intent);
	tabHost.addTab(tabSpec);
	


	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
