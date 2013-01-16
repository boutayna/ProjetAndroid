package com.boutayna.monappli.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.boutayna.monappli.domain.Result;
import com.example.monappli.R;
import com.example.monappli.R.drawable;
import com.example.monappli.R.id;
import com.example.monappli.R.layout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageAdapter extends BaseAdapter {
	LayoutInflater inflater;
	ImageLoader imageLoader;
	List<Result> results;
	List<Result> origresults;
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	String favoris;
	ImageView addFavoris;
	Result res;
	Context currentContext;

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
		settings = parent.getContext().getSharedPreferences("PRIVATE_FAVORIS",
				0);
		editor = settings.edit();
		favoris = settings.getString("favoris", "");
		Log.d(TAG, favoris);
		Log.d(TAG, "passFav");
		if (convertView == null) {
			Log.v("test", "convertView is null");
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.json_layout, null);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.quartier = (TextView) convertView.findViewById(R.id.car);
			holder.secteur = (TextView) convertView.findViewById(R.id.sec);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.favoris = (ImageView) convertView.findViewById(R.id.addFavoris);
			holder.categorie=(ImageView) convertView.findViewById(R.id.categorie);
			convertView.setTag(holder);
		} else {
			Log.v("test", "convertView is not null");
			holder = (ViewHolder) convertView.getTag();
		}

		holder = (ViewHolder) convertView.getTag();
		
		currentContext = convertView.getContext();
		res = results.get(position);
		holder.name.setText(res.getName());
		holder.quartier.setText(res.getQuartier());
		holder.secteur.setText(res.getSecteur());
		if (favoris.indexOf(String.valueOf(";"+res.getId())) >=0) {
			holder.favoris.setImageResource(R.drawable.ic_favoris);
		} else {
			Log.d(TAG, String.valueOf(";" + res.getId()));
			holder.favoris.setImageResource(R.drawable.ic_addfavoris);
			holder.favoris.setId(res.getId());
			holder.favoris.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					favoris += ";" + String.valueOf(v.getId());
					Log.d(TAG, favoris);
					editor.putString("favoris", favoris);
					editor.commit();
					((ImageView) v).setImageResource(R.drawable.ic_favoris);
					for(int i=0;i<results.size();i++)  
				    {  
				              
				                if(results.get(i).getId()==v.getId())  
				                {      
				                   Toast.makeText(currentContext,
											results.get(i).getName() + " a été ajouté aux favoris.",
											Toast.LENGTH_SHORT).show();
				                  
				                }
				    }  
				}
			});
		}
		
		if(res.getCategorie().equals("1"))
		{
			holder.categorie.setImageResource(R.drawable.ic_cat1);
		}
		if(res.getCategorie().equals("2"))
		{
			holder.categorie.setImageResource(R.drawable.ic_cat2);
		}
		if(res.getCategorie().equals("3"))
		{
			holder.categorie.setImageResource(R.drawable.ic_cat3);
		}
		if(res.getCategorie().equals("4"))
		{
			holder.categorie.setImageResource(R.drawable.ic_cat4);
		}

		imageLoader.displayImage(res.getUrlImage(), holder.image);
		return convertView;
	}

	private class ViewHolder {
		TextView name;
		TextView quartier;
		TextView secteur;
		ImageView image;
		ImageView favoris;
		ImageView categorie;
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

	public void filter(String filter) {
		List<Result> filteredArrList = new ArrayList<Result>();
		results = origresults;
		if (filter != null || filter.length() != 0) {

			for (int i = 0; i < results.size(); i++) {
				Result data = results.get(i);
				if (data.getName().toLowerCase().startsWith(filter.toString())
						|| data.getSecteur().toLowerCase()
								.startsWith(filter.toString())
						|| data.getQuartier().toLowerCase()
								.startsWith(filter.toString())) {
					filteredArrList.add(data);
					//Log.d(TAG, "*** data: " + data.toString());
				}
			}

		} else {
			filteredArrList.clear();
			for (int i = 0; i < origresults.size(); i++) {
				filteredArrList.add(origresults.get(i));
			}

		}
		this.results = filteredArrList;
		notifyDataSetChanged();

	}
	public void filterCategorie(String filter) {
		List<Result> filteredCategorieArrList = new ArrayList<Result>();
		results = origresults;
		if (filter.equals("catégorie 1")) {
			filteredCategorieArrList.clear();
			for (int i = 0; i < results.size(); i++) {
				Result data = results.get(i);
				if (data.getCategorie().equals("1"))
					filteredCategorieArrList.add(data);
			}
					
		}
		if (filter.equals("catégorie 2")) {
			filteredCategorieArrList.clear();
			for (int i = 0; i < results.size(); i++) {
				Result data = results.get(i);
				if (data.getCategorie().equals("2"))
					filteredCategorieArrList.add(data);
			}
		}
		if (filter.equals("catégorie 3")) {
			filteredCategorieArrList.clear();
			for (int i = 0; i < results.size(); i++) {
				Result data = results.get(i);
				if (data.getCategorie().equals("3"))
					filteredCategorieArrList.add(data);
			}
		}
		if (filter.equals("catégorie 4")) {
			filteredCategorieArrList.clear();
			for (int i = 0; i < results.size(); i++) {
				Result data = results.get(i);
				if (data.getCategorie().equals("4"))
					filteredCategorieArrList.add(data);
			}
		}
		if (filter.equals("all categories")) {
			filteredCategorieArrList.clear();
			for (int i = 0; i < origresults.size(); i++) {
				filteredCategorieArrList.add(origresults.get(i));
			}

		}
		
		this.results = filteredCategorieArrList;
		notifyDataSetChanged();

	}


}
