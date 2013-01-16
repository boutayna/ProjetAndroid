package com.boutayna.monappli.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monappli.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class DetailPOIMapActivity extends Activity implements OnClickListener {
	public static final String TAG = "MyActivity";
	Button carte;
	int id;
	Double lon;
	Double lat;
	String nom;
	String infos;
	Button yaller;
	Button fav;
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	String favoris;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.information_layout2);
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		nom = extras.getString("name");
		String quartier = extras.getString("quartier");
		String secteur = extras.getString("secteur");
		infos = extras.getString("infos");
		String image = extras.getString("image");
		lon = extras.getDouble("lon");
		lat = extras.getDouble("lat");
		id=extras.getInt("id");

		if (nom != null && quartier != null && secteur != null && infos != null) {
			TextView name = (TextView) findViewById(R.id.name);
			TextView quart = (TextView) findViewById(R.id.quartier);
			TextView sec = (TextView) findViewById(R.id.secteur);
			TextView inf = (TextView) findViewById(R.id.infos);
			ImageView imageView = (ImageView) findViewById(R.id.image);
			// Get singletone instance of ImageLoader
			ImageLoader imageLoader = ImageLoader.getInstance();
			// Initialize ImageLoader with configuration. Do it once.
			imageLoader.init(ImageLoaderConfiguration.createDefault(this));
			// Load and display image asynchronously
			imageLoader.displayImage(image, imageView);
			name.setText(nom);
			quart.setText(quartier);
			sec.setText(secteur);
			inf.setText(Html.fromHtml(infos));
		}

		yaller = (Button) findViewById(R.id.yaller);
		fav = (Button) findViewById(R.id.favoris);
		
		yaller.setOnClickListener(this);
		fav.setOnClickListener(this);
	}

	public void onClick(View V) {
		
		if(V==yaller)
		{
			Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("https://maps.google.fr/maps?q="+lat+","+lon));
			startActivity(intent);
		}
		if(V==fav)
		{
			settings = this.getSharedPreferences("PRIVATE_FAVORIS",
					0);
			editor = settings.edit();
			favoris = settings.getString("favoris", "");
			favoris += ";" + String.valueOf(id);
			Log.d(TAG, favoris);
			editor.putString("favoris", favoris);
			editor.commit();
			Toast.makeText(this,
					nom + " a été ajouté aux favoris.",
					Toast.LENGTH_SHORT).show();
		}
	}
}
