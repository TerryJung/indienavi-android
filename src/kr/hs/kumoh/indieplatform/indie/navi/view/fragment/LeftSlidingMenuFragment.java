package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.view.activity.MainActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class LeftSlidingMenuFragment extends SherlockFragment{
	private ListView slidelist;
	private ArrayList<String> arrayList;
	private ArrayAdapter<String> adapter;
	public static String menuString[] = {"공연정보", "아티스트 정보", "팬클럽", "설정"};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.slide_left_fragment, container, false);
		// TODO Auto-generated method stub
		arrayList = new ArrayList<String>();
		arrayList.add("공연정보");
		arrayList.add("아티스트정보");
		arrayList.add("팬클럽");
		arrayList.add("설정");
		
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
		slidelist = (ListView) root.findViewById(R.id.leftSlideMenu);
		slidelist.setAdapter(adapter);
		slidelist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		slidelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getSherlockActivity(), "click " + position, Toast.LENGTH_SHORT).show();
 				
			}
		});
		
		return root;
	}
	
}
