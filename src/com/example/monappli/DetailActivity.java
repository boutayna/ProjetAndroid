package com.example.monappli;


import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class DetailActivity extends Activity {
	public static final String TAG="MyActivity";
	/** Called when the activity is first created. */

	  @Override
	  public void onCreate(Bundle bundle) {
	    super.onCreate(bundle);
	    setContentView(R.layout.information_layout);
	    Bundle extras = getIntent().getExtras();
	    if (extras == null) {
	      return;
	    }
	    String nom = extras.getString("name");
	    String quartier = extras.getString("quartier");
	    String secteur = extras.getString("secteur");
	    String infos = extras.getString("infos");
	    if (nom != null && quartier != null && secteur != null && infos != null) {
	      TextView name = (TextView) findViewById(R.id.name);
	      TextView quart = (TextView) findViewById(R.id.quartier);
	      TextView sec = (TextView) findViewById(R.id.secteur);
	      TextView inf = (TextView) findViewById(R.id.infos);
	      name.setText(nom);
	      quart.setText(quartier);
	      sec.setText(secteur);
	      inf.setText(Html.fromHtml(infos));
	  }

}
}
