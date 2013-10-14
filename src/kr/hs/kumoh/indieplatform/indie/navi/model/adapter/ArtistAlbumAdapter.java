package kr.hs.kumoh.indieplatform.indie.navi.model.adapter;

import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.AlbumData;
import kr.hs.kumoh.indieplatform.indie.navi.util.Constant;
import kr.hs.kumoh.indieplatform.indie.navi.util.Util;
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

public class ArtistAlbumAdapter extends ArrayAdapter<AlbumData> {
	AQuery aq;
    RequestQueue mRequestQueue; 
    private ImageLoader mImageLoader;
	public ArtistAlbumAdapter(Context context, int resource, ArrayList<AlbumData> objects, ImageLoader imageLoader) {
		super(context, resource, objects);
		mRequestQueue = Volley.newRequestQueue(context);
		mImageLoader = imageLoader;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		aq = new AQuery(v);
		if (v == null) {
	    	LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        v = vi.inflate(R.layout.artist_album_fragment_listview, null);
	    }
		ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);     
		if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(R.id.id_holder, holder);
        }        
		AlbumData album = getItem(position);
        if (album.getAlbumCoverURL() == null) {

        	holder.albumCover.setImageResource(R.drawable.no_image);
        } else {
        	Log.d("album Adapter ", Constant.IMAGE_URL+album.getAlbumCoverURL());
        	aq.id(R.id.albumCover).image(Constant.IMAGE_URL+album.getAlbumCoverURL(),true, true, v.getWidth(), R.drawable.no_image, null, AQuery.FADE_IN);
       }
        
        holder.albumTitle.setText(album.getAlbumTitle());

		holder.albumYear.setText(album.getAlbumYear());
		holder.albumTitleSong.setText(album.getAlbumTitleSong());
		
		Util.fontGeneral(getContext(), holder.albumTitle);
		Util.fontGeneral(getContext(), holder.albumYear);
		Util.fontGeneral(getContext(), holder.albumTitleSong);
		return v;
	}
	private class ViewHolder {
//		ArtistData artist = new ArtistData(artistImgURL, artistName, labelName, debutYear, genreName, likeCnt)
		ImageView albumCover;
        TextView albumTitle; 
        TextView albumYear;
        TextView albumTitleSong;
        public ViewHolder(View v) {
//        	NetworkImageView.class.cast
        	albumCover = (ImageView) v.findViewById(R.id.albumCover);
        	albumTitle = (TextView) v.findViewById(R.id.albumTitle);
        	albumYear = (TextView) v.findViewById(R.id.albumYear);
        	albumTitleSong = (TextView) v.findViewById(R.id.albumTitleSong);
            v.setTag(this);
        }
    }

}
