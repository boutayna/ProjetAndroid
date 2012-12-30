package com.example.monappli;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageAdapter extends BaseAdapter {
	LayoutInflater inflater;
	ImageLoader imageLoader;
	List<Result> results;

	public ImageAdapter(Context context, List<Result> objects) {
		super();
		inflater = LayoutInflater.from(context);
		this.results = objects;
		imageLoader = ImageLoader.getInstance();
		// Initialize ImageLoader with configuration. Do it once.
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			Log.v("test", "convertView is null");
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.json_layout, null);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.quartier = (TextView) convertView.findViewById(R.id.car);
			holder.secteur = (TextView) convertView.findViewById(R.id.sec);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			Log.v("test", "convertView is not null");
			holder = (ViewHolder) convertView.getTag();
		}
		 
		holder = (ViewHolder) convertView.getTag();
		Result res = results.get(position);
		holder.name.setText(res.getName());
		holder.quartier.setText(res.getQuartier());
		holder.secteur.setText(res.getSecteur());
		imageLoader.displayImage(res.getUrlImage(), holder.image);
		return convertView;
	}

	private class ViewHolder {
		TextView name;
		TextView quartier;
		TextView secteur;
		ImageView image;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return results.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return results.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
}
