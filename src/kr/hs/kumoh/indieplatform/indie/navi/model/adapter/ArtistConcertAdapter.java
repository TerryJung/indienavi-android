package kr.hs.kumoh.indieplatform.indie.navi.model.adapter;

import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ConcertData;
import kr.hs.kumoh.indieplatform.indie.navi.util.Constant;
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
import com.androidquery.AQuery;

public class ArtistConcertAdapter extends ArrayAdapter<ConcertData> {

	private static final long serialVersionUID = -3997061417105569441L;
	AQuery aq;
    RequestQueue mRequestQueue; 
//    private RequestQueue mRequestQueue = Volley.newRequestQueue(mContext);
    private ImageLoader mImageLoader;
//    initImageLo
    public ArtistConcertAdapter(Context context, int resource, ArrayList<ConcertData> objects, ImageLoader imageLoader) {
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
	        v = vi.inflate(R.layout.artist_concert_listview, null);
	    }
		ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);       
        
        if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(R.id.id_holder, holder);
        }        
        
        ConcertData concert = getItem(position);
        if (concert.getConcertImgURL() == null) {

        	holder.concertImage.setImageResource(R.drawable.no_image);
        	
        } else {
//        	Log.d("Artist Adapter ", ArtistData.IMAGE_URL+artist.getArtistImgURL());
        	aq.id(R.id.artistImg).image(Constant.IMAGE_URL+concert.getConcertImgURL() ,true, true, v.getWidth(), R.drawable.no_image, null, AQuery.FADE_IN);
        }
        
        holder.concertNameTv.setText(concert.getConcertName());
		holder.concertPlaceNameTv.setText(concert.getPlaceName());
		holder.concertDateTv.setText(concert.getConcertDate());

		
		return v;
		
	}
	private class ViewHolder {
//		ArtistData artist = new ArtistData(artistImgURL, artistName, labelName, debutYear, genreName, likeCnt)
		ImageView concertImage;
        TextView concertNameTv; 
        TextView concertPlaceNameTv;
        TextView concertDateTv;
       
        public ViewHolder(View v) {

        	concertImage = (ImageView) v.findViewById(R.id.artistConcertImage);
        	concertNameTv = (TextView) v.findViewById(R.id.artistConcertTitle);
        	concertPlaceNameTv = (TextView) v.findViewById(R.id.artistConcertPlace);
        	concertDateTv = (TextView) v.findViewById(R.id.artistConcertDate);
            v.setTag(this);
        }
    }

}
