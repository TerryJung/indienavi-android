package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

//import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.ArtistListFragment;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.ConcertListFragment;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.LeftSlidingMenuFragment;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.RightSlidingMenuFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


public class MainActivity extends SherlockFragmentActivity {
	
	private SlidingMenu leftMenu; 
	private SlidingMenu rightMenu;
	public FragmentManager fragmentManager;
	public FragmentTransaction fragmentTransaction;
	ArtistListFragment alf = new ArtistListFragment();
	LeftSlidingMenuFragment lsmf = new LeftSlidingMenuFragment();
	RightSlidingMenuFragment rsmf = new RightSlidingMenuFragment();
	ConcertListFragment clf = new ConcertListFragment();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		leftMenu = new SlidingMenu(getApplicationContext());
		rightMenu = new SlidingMenu(getApplicationContext());
		leftMenu.setMode(SlidingMenu.LEFT);
		leftMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		leftMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		leftMenu.setFadeDegree(0.35f);
		leftMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		leftMenu.setMenu(R.layout.slide_left_menu);
//		leftMenu.set
				
		rightMenu.setMode(SlidingMenu.RIGHT);
		rightMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//      rightMenu.setShadowWidthRes(R.dimen.shadow_width);
//      rightMenu.setShadowDrawable(R.drawable.ic_launcher);
		rightMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		rightMenu.setFadeDegree(0.35f);
		rightMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		rightMenu.setMenu(R.layout.slide_right_menu);
		
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		
		fragmentTransaction.add(R.id.mainFrame, clf);
		fragmentTransaction.add(R.id.leftMenu, lsmf);
		fragmentTransaction.add(R.id.rightMenu, rsmf);
		fragmentTransaction.commit();
//		switchContent();
		
		
	}
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if ( leftMenu.isMenuShowing()) {
			leftMenu.toggle();
        } else if (rightMenu.isMenuShowing()) {
        	rightMenu.toggle();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            this.leftMenu.toggle();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
