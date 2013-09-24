package kr.hs.kumoh.indieplatform.indie.navi.model.adapter;


import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ArtistData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;

public class ArtistAdapter extends ArrayAdapter<ArtistData> {
	AQuery aq;
    RequestQueue mRequestQueue; 
//    private RequestQueue mRequestQueue = Volley.newRequestQueue(mContext);
    private ImageLoader mImageLoader;
//    initImageLo
    public ArtistAdapter(Context context, int resource, ArrayList<ArtistData> objects, ImageLoader imageLoader) {
		super(context, resource, objects);
		mRequestQueue = Volley.newRequestQueue(context);
		mImageLoader = imageLoader;
//		aq = new AQuery(act)
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		aq = new AQuery(v);
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

        	holder.artistImg.setImageResource(R.drawable.no_image);
        	

        } else {
        	Log.d("Artist Adapter ", ArtistData.IMAGE_URL+artist.getArtistImgURL());
        	aq.id(R.id.artistImg).image(ArtistData.IMAGE_URL+artist.getArtistImgURL(),true, true, v.getWidth(), R.drawable.no_image, null, AQuery.FADE_IN);
//        	holder.artistImg.setImageUrl(artist.getArtistImgURL(), mImageLoader);
//        	mImageLoader.get(artist.getArtistImgURL(), ImageLoader.getImageListener(holder.artistImg, R.drawable.ic_launcher, R.drawable.no_image));
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
		ImageView artistImg;
        TextView artistNameTv; 
        TextView labelNameTv;
        TextView debutYearTv;
        TextView genreNameTv;
        TextView likeCntTv;
        public ViewHolder(View v) {
//        	NetworkImageView.class.cast
        	artistImg = (ImageView) v.findViewById(R.id.artistImg);
            artistNameTv = (TextView) v.findViewById(R.id.artistName);
            labelNameTv = (TextView) v.findViewById(R.id.lableName);
            debutYearTv = (TextView) v.findViewById(R.id.debutYearTv);
            genreNameTv = (TextView) v.findViewById(R.id.genreName);
            likeCntTv = (TextView) v.findViewById(R.id.panInt);
            v.setTag(this);
        }
    }

}
