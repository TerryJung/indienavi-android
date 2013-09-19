package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ArtistListFragment extends ListFragment {
	
	ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
	ListView lv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.artist_fragment, container, false);

		return root;
	}
}
