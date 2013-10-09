package kr.hs.kumoh.indieplatform.indie.navi.model.adapter;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ConcertReplyData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ConcertReplyAdapter extends ArrayAdapter<ConcertReplyData> {

	public ConcertReplyAdapter(Context context, int resource,
			ConcertReplyData[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		if (v == null) {
	    	LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        v = vi.inflate(R.layout.concert_reply_listview, null);
	    }
		ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);    
		if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(R.id.id_holder, holder);
        }        
		ConcertReplyData reply = getItem(position);
        
        holder.userNameTv.setText(reply.getUserName());
		holder.replyTv.setText(reply.getReplyText());
		holder.dateTv.setText(reply.getReplyDate());
		
		
		return v;
	}
	private class ViewHolder {
//		ArtistData artist = new ArtistData(artistImgURL, artistName, labelName, debutYear, genreName, likeCnt)
		TextView userNameTv;
		TextView replyTv;
		TextView dateTv;
       
        public ViewHolder(View v) {
        	userNameTv = (TextView) v.findViewById(R.id.replyUserName);
        	replyTv = (TextView) v.findViewById(R.id.replyText);
        	dateTv = (TextView) v.findViewById(R.id.replyDate);
            
        	
            v.setTag(this);
        }
    }
	

}
