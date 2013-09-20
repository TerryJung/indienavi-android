package kr.hs.kumoh.indieplatform.indie.navi.model.adapter;

import kr.hs.kumoh.indieplatform.indie.navi.model.data.ArtistData;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ArtistAdapter extends ArrayAdapter<ArtistData> {

	public ArtistAdapter(Context context, int resource, int textViewResourceId,
			ArtistData[] objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		return super.getView(position, convertView, parent);
	}

}
