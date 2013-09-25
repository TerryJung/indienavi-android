package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.hs.kumoh.indieplatform.indie.navi.R;

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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {
	public ProgressDialog dialog = null;
	private Bundle extra  = new Bundle();
	 
	private Intent data = new Intent();
	
	private boolean sign = false;
	private Button submitBtn;
	private Button cancelBtn;
	
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
		
		idEdit = (EditText) findViewById(R.id.idEdit);
		pwEdit = (EditText) findViewById(R.id.pwEdit);
		emailEdit = (EditText) findViewById(R.id.emailEdit);
		
		submitBtn = (Button) findViewById(R.id.submitBtn);
		submitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				dialog = ProgressDialog.show(SignupActivity.this, "", 
                        "회원가입중입니다.....", true);
                 new Thread(new Runnable() {  // 쓰레드시작후 로그
                        public void run() {
                        	signup(idEdit.getText().toString(),  pwEdit.getText().toString(), emailEdit.getText().toString());
                        }
                      }).start(); 
                 if(sign == true) {
                	 finishSignup();  
                 }
			}
		});
		cancelBtn = (Button) findViewById(R.id.cancelBtn);
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	void signup(String id, String pw, String email){
		try{            
            
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://chilchil.me/apps/server/indie/signin.php"); // make sure the url is correct.
            //add your post data
            nameValuePairs = new ArrayList<NameValuePair>();
            // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
            nameValuePairs.add(new BasicNameValuePair("name",id));  // $Edittext_value = $_POST['Edittext_value'];
            nameValuePairs.add(new BasicNameValuePair("password",pw)); 
            nameValuePairs.add(new BasicNameValuePair("email", email));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //Execute HTTP Post Request
            response = httpclient.execute(httppost);
            // edited by James from coderzheaven.. from here....
//            Log.d("ww", response);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responses = httpclient.execute(httppost, responseHandler);
            System.out.println("Response : " + responses); 
            runOnUiThread(new Runnable() {
                public void run() {
                   // tv.setText("Response from PHP : " + response);
                    dialog.dismiss();
                }
            });//
             
            if(responses.equals("1")){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(SignupActivity.this,"회원가입 성공", Toast.LENGTH_SHORT).show();
                    }
                });
                sign = true;
//                putSharedPreference(name, pw);
                
            }else{}
             
        }catch(IOException e){
            dialog.dismiss();
            System.out.println("IOException : " + e.getMessage());
            	
        }
		
	}

	void finishSignup(){
		extra.putString("id", idEdit.getText().toString().trim());
		extra.putString("pw", pwEdit.getText().toString().trim());
		data.putExtras(extra);
		setResult(RESULT_OK, data); // 성공했다는 결과값을 보내면서 데이터 꾸러미를 지고 있는 intent를 함께 전달한다.
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}

}
