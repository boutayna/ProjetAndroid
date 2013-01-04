package com.example.monappli;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageAdapter extends BaseAdapter{
	LayoutInflater inflater;
	ImageLoader imageLoader;
	List<Result> results;
	List<Result> origresults;

	public static final String TAG = "MyActivity";
	public ImageAdapter(Context context, List<Result> objects) {
		super();
		inflater = LayoutInflater.from(context);
		this.results = objects;
		this.origresults = objects;
		
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
	
	
	public void filter(String filter){
		List<Result> filteredArrList = new ArrayList<Result>();
		results=origresults;
		if(filter != null || filter.length() !=0 )
		{
		 
		 for (int i = 0; i < results.size(); i++) {
           Result data = results.get(i);
           if (data.getName().toLowerCase().startsWith(filter.toString()) || data.getSecteur().toLowerCase().startsWith(filter.toString()) || data.getQuartier().toLowerCase().startsWith(filter.toString()) )   {
               filteredArrList.add(data);
               Log.d(TAG, "*** data: " + data.toString());
           }
		 }
		 
		 
		}
		else
		{
			filteredArrList.clear();
			 for (int i = 0; i < origresults.size(); i++) {
			filteredArrList.add(origresults.get(i));}
		
			
		}
		this.results=filteredArrList;
		 notifyDataSetChanged();
		 
	}
	
	

}
