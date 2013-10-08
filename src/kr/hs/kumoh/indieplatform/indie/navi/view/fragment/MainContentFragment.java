package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;

public class MainContentFragment extends SherlockFragment{
	ImageView concertBtn;
	ImageView artistBtn;
	ImageView settingBtn;
	ImageView fanclubBtn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.main_fragment, container, false);
		// TODO Auto-generated method stub
		
		return root;
	}

}
