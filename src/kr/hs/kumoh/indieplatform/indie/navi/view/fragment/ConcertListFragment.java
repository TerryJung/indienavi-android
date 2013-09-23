package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class ConcertListFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View root = inflater.inflate(R.layout.concert_fragment, container, false);
		return root;
	}
}
