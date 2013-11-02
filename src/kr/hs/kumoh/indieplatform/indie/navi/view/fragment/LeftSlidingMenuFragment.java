package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.view.activity.LoginActivity;
import kr.hs.kumoh.indieplatform.indie.navi.view.activity.MainActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class LeftSlidingMenuFragment extends SherlockFragment{
	private ListView slidelist;
	private ArrayList<String> arrayList;
	private ArrayAdapter<String> adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.slide_left_fragment, container, false);
		// TODO Auto-generated method stub
		arrayList = new ArrayList<String>();
		arrayList.add("공연정보");
		arrayList.add("아티스트정보");
		arrayList.add("팬클럽");
//		arrayList.add("설정");
		arrayList.add("로그아웃");
		arrayList.add("앱 종료");
		
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
		slidelist = (ListView) root.findViewById(R.id.leftSlideMenu);
		slidelist.setAdapter(adapter);
		slidelist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		slidelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				if(position == 0 ) {
					switchFragment(new ConcertListFragment());
				} else if  (position == 1) {
					switchFragment(new ArtistListFragment());
				} else if (position == 2) {
					switchFragment(new FavoriteArtistListFragment());
//				} else if (position == 3) {
					
				} else if (position == 3) {
					LogOutDialogSimple();
				} else if (position == 4) {
					appExit();
				}
			}
		});
		return root;
	}
	private void LogOutDialogSimple(){
	    AlertDialog.Builder alt_bld = new AlertDialog.Builder(getActivity());
	    alt_bld.setMessage("로그아웃 하시겠습니까?").setCancelable(
	        false).setPositiveButton("네",
	        new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	            // Action for 'Yes' Button
	        	Intent i = new Intent(getActivity(), LoginActivity.class);
	        	startActivity(i);
	        	getActivity().finish();
	        }
	        }).setNegativeButton("아니오",
	        new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	            // Action for 'NO' Button
	            dialog.cancel();
	        }
	        });
	    AlertDialog alert = alt_bld.create();
	    // Title for AlertDialog
	    alert.setTitle("로그아읏");
	    // Icon for AlertDialog
	    alert.setIcon(R.drawable.ic_launcher);
	    alert.show();
	}
	private void appExit(){
	    AlertDialog.Builder alt_bld = new AlertDialog.Builder(getActivity());
	    alt_bld.setMessage("앱을 종료하시겠 습니까?").setCancelable(
	        false).setPositiveButton("네",
	        new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	            // Action for 'Yes' Button
//	        	MovementMethod 
	        	getActivity().moveTaskToBack(true);
	        	getActivity().finish();
	        	android.os.Process.killProcess(android.os.Process.myPid());
	        	
	        }
	        }).setNegativeButton("아니오",
	        new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	            // Action for 'NO' Button
	            dialog.cancel();
	        }
	        });
	    AlertDialog alert = alt_bld.create();
	    // Title for AlertDialog
	    alert.setTitle("앱 종료");
	    // Icon for AlertDialog
	    alert.setIcon(R.drawable.ic_launcher);
	    alert.show();
	}
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity ma = (MainActivity) getActivity();
			ma.switchContent(fragment);
		}
	}

}
