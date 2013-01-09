package com.example.monappli;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class DetailActivity extends Activity implements OnClickListener {
	public static final String TAG = "MyActivity";
	Button carte;
	Double lon;
	Double lat;
	String nom;
	String infos;
	Button yaller;
	Button favoris;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.information_layout);
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
		favoris = (Button) findViewById(R.id.favoris);
		carte = (Button) findViewById(R.id.carte);

		carte.setOnClickListener(this);
		yaller.setOnClickListener(this);
		favoris.setOnClickListener(this);
	}

	public void onClick(View V) {
		if (V == carte) {
			Intent intent = new Intent(this, CarteActivity.class);
			intent.putExtra("lon", lon);
			intent.putExtra("lat", lat);
			intent.putExtra("name", nom);
			intent.putExtra("infos", infos);

			startActivity(intent);
		}
		if(V==yaller)
		{
			Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("https://maps.google.fr/maps?q="+lat+","+lon));
			startActivity(intent);
		}
	}
}
