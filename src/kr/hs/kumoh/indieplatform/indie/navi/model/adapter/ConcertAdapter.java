package kr.hs.kumoh.indieplatform.indie.navi.model.adapter;

import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ConcertData;
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

public class ConcertAdapter extends ArrayAdapter<ConcertData> {
	AQuery aq;
	RequestQueue mRequestQueue; 
//  private RequestQueue mRequestQueue = Volley.newRequestQueue(mContext);
  private ImageLoader mImageLoader;
	public ConcertAdapter(Context context, int resource, ArrayList<ConcertData> concertData, ImageLoader imageLoader) {
		super(context, resource, concertData);
		mRequestQueue = Volley.newRequestQueue(context);
		mImageLoader = imageLoader;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;	
		aq = new AQuery(v);
		if (v == null) {
	    	LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        v = vi.inflate(R.layout.concert_fragment_listview, null);
	    }
		ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);       
        
        if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(R.id.id_holder, holder);
        }        
        ConcertData concert = getItem(position);
        if (concert.getConcertImgURL() == null) {

        	holder.concertImg.setImageResource(R.drawable.no_image);
        	

        } else {
//        	Log.d("concert Adapter ", concert.getConcertImgURL());
        	aq.id(R.id.concertImg).image(concert.getConcertImgURL(),true, true, v.getWidth(), R.drawable.no_image, null, AQuery.FADE_IN);
        }
        holder.concertNameTv.setText(concert.getConcertName());
		holder.concertDateTv.setText(concert.getConcertDate());
		holder.concertPlaceTv.setText(concert.getPlaceName());

		return v;
	}
	private class ViewHolder {
		ImageView concertImg;
        TextView concertNameTv; 
        TextView concertDateTv;
        TextView concertPlaceTv;
        
        public ViewHolder(View v) {
        	concertImg = (ImageView) v.findViewById(R.id.concertImg);
        	concertNameTv = (TextView) v.findViewById(R.id.concertName);
        	concertDateTv = (TextView) v.findViewById(R.id.concertDate);
            concertPlaceTv = (TextView) v.findViewById(R.id.placeName);
            v.setTag(this);
        }
    }
}
