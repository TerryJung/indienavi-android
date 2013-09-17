package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArtistListFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.artist_fragment, container, false);
		// TODO Auto-generated method stub
		TextView tv = (TextView) root.findViewById(R.id.textView1);
//		tv.setText("wwwww");
		return root;
	}
}
