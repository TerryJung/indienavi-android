package kr.hs.kumoh.indieplatform.indie.navi.model.adapter;

import kr.hs.kumoh.indieplatform.indie.navi.model.data.ArtistData;
import android.content.Context;
import android.widget.ArrayAdapter;

public class ArtistAdapter extends ArrayAdapter<ArtistData> {

	public ArtistAdapter(Context context, int resource, int textViewResourceId,
			ArtistData[] objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

}
