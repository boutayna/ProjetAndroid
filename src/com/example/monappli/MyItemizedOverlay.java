package com.example.monappli;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> myOverlays;
	private Context context;
	public static final String TAG = "MyActivity";
	private String[] result;

	public MyItemizedOverlay(Drawable drawable, Context context) {
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
	protected boolean onTap(final int i) {
		GeoPoint gpoint = myOverlays.get(i).getPoint();

		// Création de l'AlertDialog
		LayoutInflater factory = LayoutInflater.from(context);
		final View alertDialogView = factory.inflate(R.layout.alert_dialog,
				null);

		// Création de l'AlertDialog

		AlertDialog.Builder dialog = new AlertDialog.Builder(context);

		dialog.setView(alertDialogView);

		TextView quart = (TextView) alertDialogView.findViewById(R.id.quartier);
		TextView sec = (TextView) alertDialogView.findViewById(R.id.secteur);
		ImageView imageView = (ImageView) alertDialogView
				.findViewById(R.id.image);
		Button yaller = (Button) alertDialogView.findViewById(R.id.yaller);
		Button favoris = (Button) alertDialogView.findViewById(R.id.favoris);
		Button detail = (Button) alertDialogView.findViewById(R.id.detail);
		dialog.setTitle(myOverlays.get(i).getTitle());

		result = myOverlays.get(i).getSnippet().split("\\|");

		sec.setText(result[0]);
		quart.setText(result[1]);
		ImageLoader imageLoader = ImageLoader.getInstance();
		// Initialize ImageLoader with configuration. Do it once.
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		// Load and display image asynchronously
		imageLoader.displayImage(result[2], imageView);

		detail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, DetailActivity.class);
				intent.putExtra("name", myOverlays.get(i).getTitle());
				intent.putExtra("quartier", result[1]);
				intent.putExtra("secteur", result[0]);
				intent.putExtra("image", result[2]);
				intent.putExtra("infos", result[3]);
				context.sendBroadcast(intent);
				context.startActivity(intent);
			}
		});

		yaller.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		favoris.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		dialog.show();

		return (true);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return myOverlays.size();
	}

}
