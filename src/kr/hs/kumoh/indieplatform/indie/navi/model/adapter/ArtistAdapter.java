package kr.hs.kumoh.indieplatform.indie.navi.model.adapter;


import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ArtistData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class ArtistAdapter extends ArrayAdapter<ArtistData> {
	

	private Context mContext;
	private int artistResource;
	private ArrayList<ArtistData> artistList;
    private LayoutInflater artistInflater;
    RequestQueue mRequestQueue = Volley.newRequestQueue(getContext());
//    private RequestQueue mRequestQueue = Volley.newRequestQueue(mContext);
    private ImageLoader imload = new ImageLoader(mRequestQueue, null);
    public ArtistAdapter(Context context, int resource, ArrayList<ArtistData> objects) {
		super(context, resource, objects);
		this.mContext = context;
		this.artistResource = resource;
		this.artistList = objects;
		this.artistInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		View v = convertView;
		ArtistData artist = artistList.get(position);
		
		
		// TODO Auto-generated method stub
		ImageView artistImg = (ImageView) convertView.findViewById(R.id.artistImg);
		TextView artistNameTv = (TextView) convertView.findViewById(R.id.artistName);
		TextView labelNameTv = (TextView) convertView.findViewById(R.id.lableName);
		TextView debutYearTv = (TextView) convertView.findViewById(R.id.debutYearTv);
		TextView genreNameTv = (TextView) convertView.findViewById(R.id.genreName);
		
		
		artistImg.setImageResource(R.drawable.ic_launcher);
		artistNameTv.setText(artist.getArtistName());
		labelNameTv.setText(artist.getLabelName());
		debutYearTv.setText(artist.getDebutYear());
		genreNameTv.setText(artist.getGenreName());
		return convertView;
	}

}
