package kr.hs.kumoh.indieplatform.the.band.view.activity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.hs.kumoh.indieplatform.theBand.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public ProgressDialog dialog = null;
	private Button loginBtn;
	private Button signupBtn;
	
	private EditText idEdit;
	private EditText pwEdit;
	private HttpPost httppost;
	
    private HttpResponse response;
    private HttpClient httpclient;
    
    private List<NameValuePair> nameValuePairs;
    
    public static final int REQUEST_CODE = 101;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		idEdit = (EditText) findViewById(R.id.idEdit);
		pwEdit = (EditText) findViewById(R.id.pwEdit);
		
		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = ProgressDialog.show(LoginActivity.this, "", 
                        "로그인중입니다....", true);
                 new Thread(new Runnable() {  // 쓰레드시작후 로그
                        public void run() {
                        	String name = idEdit.getText().toString().trim();
                            String pw = pwEdit.getText().toString().trim();
                        	login(name, pw);
                                                 
                        }
                      }).start(); 
			}
		});
		signupBtn = (Button) findViewById(R.id.signupBtn);
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
	public void login(String name, String pw){
		try{            
            
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://chilchil.me/apps/server/indie/signin.php"); // make sure the url is correct.
            //add your post data
            nameValuePairs = new ArrayList<NameValuePair>(2);
            // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
            nameValuePairs.add(new BasicNameValuePair("name",name));  // $Edittext_value = $_POST['Edittext_value'];
            nameValuePairs.add(new BasicNameValuePair("password",pw)); 
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //Execute HTTP Post Request
            response = httpclient.execute(httppost);
            // edited by James from coderzheaven.. from here....
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            System.out.println("Response : " + response); 
            runOnUiThread(new Runnable() {
                public void run() {
                   // tv.setText("Response from PHP : " + response);
                    dialog.dismiss();
                }
            });
             
            if(response.equalsIgnoreCase("1")){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoginActivity.this,"로그인 성공", Toast.LENGTH_SHORT).show();
                    }
                });
                putSharedPreference(name, pw);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }else{}
             
        }catch(IOException e){
            dialog.dismiss();
            System.out.println("IOException : " + e.getMessage());
        }
		
	}
	void putSharedPreference(String name, String pw){
		SharedPreferences userinfo = getSharedPreferences("userinfo", MODE_PRIVATE);
		SharedPreferences.Editor edits = userinfo.edit();  // 에디터 객체를 생성하여 
		edits.putString("id", name);  // 으로 저장
		edits.putString("pw", pw);
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
}
