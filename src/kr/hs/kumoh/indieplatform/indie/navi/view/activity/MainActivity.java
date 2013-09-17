package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

//import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.R;
import android.os.Bundle;
import android.view.KeyEvent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


public class MainActivity extends SherlockFragmentActivity {
	
	private SlidingMenu leftMenu; 
	private SlidingMenu rightMenu;
	
//	private Fragment leftF;
//	private Fragment rightF;

//	private FragmentManager lfm = getSupportFragmentManager();
//	private FragmentTransaction ft;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getSupportActionBar().setBackgroundDrawable(d)
		//getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("FF4444")));
		
		setContentView(R.layout.activity_main);
//		leftMenu = getSl
		leftMenu = new SlidingMenu(getApplicationContext());
		rightMenu = new SlidingMenu(getApplicationContext());
		leftMenu.setMode(SlidingMenu.LEFT);
		leftMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//      leftMenu.setShadowWidthRes(R.dimen.shadow_width);
//      leftMenu.setShadowDrawable(R.drawable.ic_launcher);
		leftMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		leftMenu.setFadeDegree(0.35f);
//		leftMenu.attachToActivity(this, SlidingMenu.)
		leftMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		leftMenu.setMenu(R.layout.slide_left_fragment);
		
		
		
		rightMenu.setMode(SlidingMenu.RIGHT);
		rightMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//      rightMenu.setShadowWidthRes(R.dimen.shadow_width);
//      rightMenu.setShadowDrawable(R.drawable.ic_launcher);
		rightMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		rightMenu.setFadeDegree(0.35f);
		rightMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		rightMenu.setMenu(R.layout.slide_right_fragment);
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
