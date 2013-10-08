package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.Constant;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class SignupActivity extends SherlockActivity {
	public ProgressDialog dialog = null;
	private Bundle extra  = new Bundle();
	 
	private Intent data = new Intent();
	
	private boolean sign = false;
	private ImageView submitBtn;
	private ImageView cancelBtn;
	
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
		idEdit = (EditText) findViewById(R.id.idEdit);
		pwEdit = (EditText) findViewById(R.id.pwEdit);
		emailEdit = (EditText) findViewById(R.id.emailEdit);
		
		submitBtn = (ImageView) findViewById(R.id.submitBtn);
		submitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				dialog = ProgressDialog.show(SignupActivity.this, "", 
                        "ȸ���������Դϴ�.....", true);
                 new Thread(new Runnable() {  // ����������� �α�
                        public void run() {
                        	signup(idEdit.getText().toString(),  pwEdit.getText().toString(), emailEdit.getText().toString());
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
	}
	void signup(String id, String pw, String email){
		try{            
            
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(Constant.SERVER_URL+"apps/server/indie/signup.php"); // make sure the url is correct.
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
             
            if(responses.equals("OK")){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(SignupActivity.this,"ȸ������ ����", Toast.LENGTH_SHORT).show();
                    }
                });
                finishSignup();
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
		setResult(RESULT_OK, data); // �����ߴٴ� ������� �����鼭 ������ �ٷ��̸� ���� �ִ� intent�� �Բ� �����Ѵ�.
		finish();
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
}
