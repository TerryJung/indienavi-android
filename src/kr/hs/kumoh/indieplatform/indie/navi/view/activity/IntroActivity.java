package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.util.Constant;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.widget.Toast;

public class IntroActivity extends Activity {
	
	LoginActivity la = new LoginActivity();
	private String name;
	private String pw;
	private boolean login;
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
				if(login == false ){
					Intent i = new Intent(IntroActivity.this, LoginActivity.class);
					startActivity(i);
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					finish();
					
					//la.login(id, pw);
				} else if (login == true) {
					
					handler.post(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							la.dialog = ProgressDialog.show(IntroActivity.this, "", 
			                        "로그인중입니다....", true);
							Intent i = new Intent(IntroActivity.this, MainActivity.class);
							startActivity(i);
							overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
							Toast.makeText(IntroActivity.this,"로그인 성공", Toast.LENGTH_SHORT).show();
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
		Constant.USER_NAME = userinfo.getString("id", "");
	    name = userinfo.getString("id", "");
	    pw = userinfo.getString("pw", "");
	    login = userinfo.getBoolean("login", false);
	    
	}

}
