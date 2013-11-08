package kr.hs.kumoh.indieplatform.indie.navi.view.activity;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.util.Constant;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public ProgressDialog dialog = null;
	private ImageView loginBtn;
	private ImageView signupBtn;
	// test 
	private EditText idEdit;
	private EditText pwEdit;
	private HttpPost httppost;
	
    private HttpResponse response;
    private HttpClient httpclient;
    private String name;
    private String pw;
    private List<NameValuePair> nameValuePairs;
    
    public static final int REQUEST_CODE = 101;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		idEdit = (EditText) findViewById(R.id.idEdit);
		pwEdit = (EditText) findViewById(R.id.pwEdit);
//		idEdit.setTypeface(new Util().fontLight(this, pwEdit));
		loginBtn = (ImageView) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = ProgressDialog.show(LoginActivity.this, "", 
                        "로그인중입니다....", true);
                 new Thread(new Runnable() {  // 쓰레드시작후 로그
                        public void run() {
                        	
                        	name = idEdit.getText().toString().trim();
                            pw = pwEdit.getText().toString().trim();
                            
                            try {
								signupPost(name, pw);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                                                 
                        }
                      }).start(); 
			}
		});
		signupBtn = (ImageView) findViewById(R.id.signupBtn);
		signupBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this, SignupActivity.class);
				startActivityForResult(i, REQUEST_CODE);
				// TODO Auto-generated method stub
//				startActivityForResult(new Intent(LoginActivity.this, SignupActivity.class, requestCode), 0);
			}
		});
		

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
			case REQUEST_CODE: // requestCode가 B_ACTIVITY인 케이스
			if(resultCode == RESULT_OK){ //B_ACTIVITY에서 넘겨진 resultCode가 OK일때만 실행
				idEdit.setText(data.getExtras().getString("id"));
				pwEdit.setText(data.getExtras().getString("pw"));
				
//				data.getExtras.getInt("data");//등과 같이 사용할 수 있는데, 여기서 getXXX()안에 들어있는 파라메터는 꾸러미 속 데이터의 이름표라고 보면된다.
			}
		}
		
	}
	private void signupPost(String name, String pass) throws IOException{
		URL url = new URL(Constant.SERVER_URL+"apps/server/indie/signin.php");
		HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();

		httpUrl.setDefaultUseCaches(false);
		httpUrl.setDoInput(true);
		httpUrl.setDoOutput(true);
		httpUrl.setRequestMethod("POST");
		httpUrl.setRequestProperty("content-type", "application/x-www-form-urlencoded");
//		$name = $_REQUEST['name'];
//		$password = $_REQUEST['password'];

		StringBuffer sb = new StringBuffer();
		sb.append("name").append("=").append(name).append("&");
		sb.append("password").append("=").append(pass);
		
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpUrl.getOutputStream(), "UTF-8"));
		pw.write(sb.toString());
		pw.flush();
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), "UTF-8"));
		StringBuilder buff = new StringBuilder();
		String line;
		while((line = bf.readLine())!=null) {
			buff.append(line);
			if(line.equalsIgnoreCase("OK")){
				runOnUiThread(new Runnable() {
                  public void run() {
                	  Toast.makeText(LoginActivity.this,"로그인 성공", Toast.LENGTH_SHORT).show();
                  }
              });		
				putSharedPreference(name, pass, true);
                Constant.USER_NAME = name;
                startActivity(new Intent(this, MainActivity.class));
                finish();
			} else {
				dialog.dismiss();
				showAlert();
			}
		}
	}
	void putSharedPreference(String name, String pw, boolean login){
		SharedPreferences userinfo = getSharedPreferences("userinfo", MODE_PRIVATE);
		SharedPreferences.Editor edits = userinfo.edit();  // 에디터 객체를 생성하여 
		edits.putString("id", name);  // 으로 저장
		edits.putString("pw", pw);
		edits.putBoolean("login", login);
		edits.commit();
	}
	public void showAlert(){
		LoginActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("로그인 실패.");
                builder.setMessage("계정정보를 찾을수 없습니다. 다시시도해보세요.")  
                       .setCancelable(false)
                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                           }
                       });                     
                AlertDialog alert = builder.create();
                alert.show();               
            }
        });
    }
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		FlurryAgent.onStartSession(this, Constant.FLURRY_API_KEY);
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		FlurryAgent.onEndSession(this);
	}
}
