package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;

public class MainContentFragment extends SherlockFragment{
	private ImageView concertBtn;
	private ImageView artistBtn;
	private ImageView settingBtn;
	private ImageView fanclubBtn;
	private Date d = new java.util.Date();
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	private String today = df.format(d); 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.main_fragment, container, false);
		// TODO Auto-generated method stub
		
		return root;
	}

}
