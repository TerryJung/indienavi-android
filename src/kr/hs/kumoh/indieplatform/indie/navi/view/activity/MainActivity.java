package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

//import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.ArtistListFragment;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.ConcertListFragment;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.FavoriteArtistListFragment;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.LeftSlidingMenuFragment;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.RightSlidingMenuFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


public class MainActivity extends SherlockFragmentActivity {
	
	Fragment mContent;
	private SlidingMenu leftMenu; 
	private SlidingMenu rightMenu;
	public FragmentManager fragmentManager;
	public FragmentTransaction fragmentTransaction;
	ArtistListFragment alf = new ArtistListFragment();
	LeftSlidingMenuFragment lsmf = new LeftSlidingMenuFragment();
	RightSlidingMenuFragment rsmf = new RightSlidingMenuFragment();
	ConcertListFragment clf = new ConcertListFragment();
	FavoriteArtistListFragment falf = new FavoriteArtistListFragment();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().setHomeButtonEnabled(true); 
//		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setIcon(R.drawable.menu);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF00edc6));
//		getSupportActionBar().setTitle("       Indie Navi");
		leftMenu = new SlidingMenu(getApplicationContext());

		leftMenu.setMode(SlidingMenu.LEFT);
		leftMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		leftMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		leftMenu.setShadowWidth(R.dimen.slidingmenu_offset);
		leftMenu.setFadeDegree(1f);
		leftMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		leftMenu.setMenu(R.layout.slide_left_menu);
//		leftMenu.setAlpha(alpha)
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		
		fragmentTransaction.add(R.id.mainFrame, alf);
		fragmentTransaction.add(R.id.leftMenu, lsmf);
		fragmentTransaction.commit();
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if ( leftMenu.isMenuShowing()) {
			leftMenu.toggle();
        } else {
            super.onBackPressed();
        }
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ( keyCode == KeyEvent.KEYCODE_MENU ) {
            this.leftMenu.toggle();
            return true;
        }
		return super.onKeyDown(keyCode, event);
	}

	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.action_menu, menu);
		return true;
	  
	 }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
        	this.leftMenu.toggle();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.mainFrame, fragment)
		.commit();
		leftMenu.showContent();
	}

}
