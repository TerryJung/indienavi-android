package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class LeftSlidingMenuFragment extends SherlockFragment{
	private ListView slidelist;
	private ArrayList<String> arrayList;
	private ArrayAdapter<String> adapter;
	public static String menuString[] = {"��������", "��Ƽ��Ʈ ����", "��Ŭ��", "����"};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.slide_left_fragment, container, false);
		// TODO Auto-generated method stub
		arrayList = new ArrayList<String>();
		arrayList.add("��������");
		arrayList.add("��Ƽ��Ʈ����");
		
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
		slidelist = (ListView) root.findViewById(R.id.leftSlideMenu);
		slidelist.setAdapter(adapter);
		slidelist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	
		
		return root;
	}
}
