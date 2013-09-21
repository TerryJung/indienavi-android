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
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class ArtistAdapter extends ArrayAdapter<ArtistData> {
	

	private Context mContext;
	private int artistResource;
	private ArrayList<ArtistData> artistList;
    private LayoutInflater artistInflater;
    RequestQueue mRequestQueue = Volley.newRequestQueue(getContext());
//    private RequestQueue mRequestQueue = Volley.newRequestQueue(mContext);
    private ImageLoader mImageLoader;
    public ArtistAdapter(Context context, int resource, ArrayList<ArtistData> objects, ImageLoader imageLoader) {
		super(context, resource, objects);
		this.mContext = context;
		this.artistResource = resource;
		this.artistList = objects;
		this.artistInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mImageLoader = imageLoader;
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
	    	LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        v = vi.inflate(R.layout.artist_fragment_listview, null);
	    }
		ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);       
        
        if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(R.id.id_holder, holder);
        }        
        
        ArtistData artist = getItem(position);
        if (artist.getArtistImgURL() != null) {
            holder.artistImg.setImageUrl(artist.getArtistImgURL(), mImageLoader);
        } else {
            holder.artistImg.setImageResource(R.drawable.no_image);
        }
        
        holder.artistNameTv.setText(artist.getArtistName());
		holder.labelNameTv.setText(artist.getLabelName());
		holder.debutYearTv.setText(artist.getDebutYear());
		holder.genreNameTv.setText(artist.getGenreName());
		
		return v;
		
	}
	private class ViewHolder {
        NetworkImageView artistImg;
        TextView artistNameTv; 
        TextView labelNameTv;
        TextView debutYearTv;
        TextView genreNameTv;
        public ViewHolder(View v) {
        	artistImg = (NetworkImageView) v.findViewById(R.id.artistImg);
            artistNameTv = (TextView) v.findViewById(R.id.artistName);
            labelNameTv = (TextView) v.findViewById(R.id.lableName);
            debutYearTv = (TextView) v.findViewById(R.id.debutYearTv);
            genreNameTv = (TextView) v.findViewById(R.id.genreName);
            v.setTag(this);
        }
    }

}
