package kr.hs.kumoh.indieplatform.indie.navi.view.activity;



import com.androidquery.AQuery;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.Window;

public class IntroActivity extends Activity {
	
	LoginActivity la = new LoginActivity();
	private String name;
	private String pw;
//	la.dialog = null
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_intro);
		getSharedPreference();
		final Handler handler = new Handler();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				if(name == null ){
					Intent i = new Intent(IntroActivity.this, LoginActivity.class);
					startActivity(i);
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					finish();
					
					//la.login(id, pw);
				} else if (name != null && pw != null) {
					la.login(name, pw);
					handler.post(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							la.dialog = ProgressDialog.show(IntroActivity.this, "", 
			                        "로그인중입니다....", true);
						}
						
					});
					
					finish();
//					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				}
			}
		}).start();
	}
	void getSharedPreference() {
		SharedPreferences userinfo = getSharedPreferences("userinfo", MODE_PRIVATE);
		SharedPreferences.Editor editor = userinfo.edit();    
	    name = userinfo.getString("id", "");
	    pw = userinfo.getString("pw", "");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.intro, menu);
		return true;
	}

}
