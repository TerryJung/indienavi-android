package kr.hs.kumoh.indieplatform.indie.navi.model.adapter;

import kr.hs.kumoh.indieplatform.indie.navi.model.data.ConcertData;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ConcertAdapter extends ArrayAdapter<ConcertData> {

	public ConcertAdapter(Context context, int resource, ConcertData[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		return super.getView(position, convertView, parent);
	}
}
