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
        if (artist.getArtistImgURL() == null) {
//        	holder.artistImg.setImageURI(uri)
//        	holder.artistImg.setImageResource(R.drawable.no_image);
        	

        } else {
//        	holder.artistImg.setImageUrl(artist.getArtistImgURL(), mImageLoader);
        }
        
        holder.artistNameTv.setText(artist.getArtistName());
        
        if(artist.getLabelName().equals("null"))  {
        	holder.labelNameTv.setText("");
        } else {
        	holder.labelNameTv.setText(artist.getLabelName());
        }
		
		holder.debutYearTv.setText(artist.getDebutYear());
		holder.genreNameTv.setText(artist.getGenreName());
		holder.likeCntTv.setText(artist.getLikeCnt());
		
		return v;
		
	}
	private class ViewHolder {
//		ArtistData artist = new ArtistData(artistImgURL, artistName, labelName, debutYear, genreName, likeCnt)
		final ImageView networkimage;
        TextView artistNameTv; 
        TextView labelNameTv;
        TextView debutYearTv;
        TextView genreNameTv;
        TextView likeCntTv;
        public ViewHolder(View v) {
        	networkimage = ImageView.class.cast(v.findViewById(R.id.artistImg));
//        	mImageLoader.get(ArtistData.class., ImageLoader.getImageListener(networkimage, R.drawable.ic_launcher, R.drawable.no_image));
//        	artistImg = (NetworkImageView) v.findViewById(R.id.artistImg);
            artistNameTv = (TextView) v.findViewById(R.id.artistName);
            labelNameTv = (TextView) v.findViewById(R.id.lableName);
            debutYearTv = (TextView) v.findViewById(R.id.debutYearTv);
            genreNameTv = (TextView) v.findViewById(R.id.genreName);
            likeCntTv = (TextView) v.findViewById(R.id.panInt);
            v.setTag(this);
        }
    }

}
