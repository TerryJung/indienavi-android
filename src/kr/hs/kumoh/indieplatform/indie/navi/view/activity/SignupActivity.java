package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.util.Constant;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.flurry.android.FlurryAgent;

public class SignupActivity extends SherlockActivity {
	public ProgressDialog dialog = null;
	private Bundle extra  = new Bundle();
	 
	private Intent data = new Intent();
	
	private boolean sign = false;
	private ImageView submitBtn;
	private ImageView cancelBtn;
	
	private ImageView noticeBtn;
	private EditText idEdit;
	private EditText pwEdit;
	private EditText emailEdit;
	
	private HttpPost httppost;
    private HttpResponse response;
    private HttpClient httpclient;
//	private List<E> na
    private List<NameValuePair> nameValuePairs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		la = (Button) findViewById(R.id.imageButton1);
		idEdit = (EditText) findViewById(R.id.idEdit);
		pwEdit = (EditText) findViewById(R.id.pwEdit);
		emailEdit = (EditText) findViewById(R.id.emailEdit);
		
		submitBtn = (ImageView) findViewById(R.id.submitBtn);
		submitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				dialog = ProgressDialog.show(SignupActivity.this, "", 
                        "회원가입중입니다.....", true);
                 new Thread(new Runnable() {  // 쓰레드시작후 로그
                        public void run() {
//                        	signup(idEdit.getText().toString(),  pwEdit.getText().toString(), emailEdit.getText().toString());
                        	try {
								signupPost(idEdit.getText().toString(),  pwEdit.getText().toString(), emailEdit.getText().toString());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                        }
                      }).start(); 
//                 if(sign == true) {
//                	 finishSignup();  
//                 }
			}
		});
		cancelBtn = (ImageView) findViewById(R.id.cancelBtn);
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		noticeBtn = (ImageView) findViewById(R.id.noticeBtn);
		noticeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SignupActivity.this, NoticeActivity.class);
				startActivity(i);
			}
		});
	}

	private void signupPost(String id, String password, String email) throws IOException{
		URL url = new URL(Constant.SERVER_URL+"apps/server/indie/signup.php");
		HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();

		httpUrl.setDefaultUseCaches(false);
		httpUrl.setDoInput(true);
		httpUrl.setDoOutput(true);
		httpUrl.setRequestMethod("POST");
		httpUrl.setRequestProperty("content-type", "application/x-www-form-urlencoded");
//		$name = $_REQUEST['name'];
//		$password = $_REQUEST['password'];
//		$email = $_REQUEST['email'];
		StringBuffer sb = new StringBuffer();
		sb.append("name").append("=").append(id).append("&");
		sb.append("password").append("=").append(password).append("&");
		sb.append("email").append("=").append(email);
		
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
                	  Toast.makeText(SignupActivity.this,"회원가입 성공", Toast.LENGTH_SHORT).show();
                  }
              });		
				finishSignup();
			} else {
				dialog.dismiss();
				showAlert();
			}
		}
	}
	void finishSignup(){
		extra.putString("id", idEdit.getText().toString().trim());
		extra.putString("pw", pwEdit.getText().toString().trim());
		data.putExtras(extra);
		setResult(RESULT_OK, data); // 성공했다는 결과값을 보내면서 데이터 꾸러미를 지고 있는 intent를 함께 전달한다.
		finish();
	}
	public void showAlert(){
		SignupActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
        case android.R.id.home:
            this.finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
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

