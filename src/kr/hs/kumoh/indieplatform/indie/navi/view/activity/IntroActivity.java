package kr.hs.kumoh.indieplatform.indie.navi.view.activity;


import kr.hs.kumoh.indieplatform.theBand.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

public class IntroActivity extends Activity {
	
	LoginActivity la = new LoginActivity();
//	la.dialog = null
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_intro);
		SharedPreferences userinfo = getSharedPreferences("userinfo", MODE_PRIVATE);
//		SharedPreferences.Editor edits = userinfo.edit(); 
		final String id = userinfo.getString("id", "").toString();
		final String pw = userinfo.getString("pw", "").toString();
		System.out.println(id);
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
//				if(id == null ){
					Intent i = new Intent(IntroActivity.this, LoginActivity.class);
					startActivity(i);
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					finish();
					
					//la.login(id, pw);
//				} else if (id != null) {
//					la.dialog = ProgressDialog.show(IntroActivity.this, "", 
//	                        "로그인중입니다....", true);
//					la.login(id, pw);
//					Intent i = new Intent(IntroActivity.this, MainActivity.class);
//					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//					finish();
//					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//				}
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.intro, menu);
		return true;
	}

}
