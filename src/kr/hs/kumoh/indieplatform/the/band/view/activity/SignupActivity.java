package kr.hs.kumoh.indieplatform.the.band.view.activity;

import kr.hs.kumoh.indieplatform.theBand.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends Activity {
	Bundle extra;
	Intent data;
	
	Button submitBtn;
	Button cancelBtn;
	
	EditText idEdit;
	EditText pwEdit;
	EditText emailEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		extra = new Bundle();
		data = new Intent(); 
		
		idEdit = (EditText) findViewById(R.id.idEdit);
		pwEdit = (EditText) findViewById(R.id.pwEdit);
		emailEdit = (EditText) findViewById(R.id.emailEdit);
		
		submitBtn = (Button) findViewById(R.id.submitBtn);
		submitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				signup();
				extra.putString("id", idEdit.getText().toString().trim());
				extra.putString("pw", pwEdit.getText().toString().trim());
				data.putExtras(extra);
				setResult(RESULT_OK, data); // 성공했다는 결과값을 보내면서 데이터 꾸러미를 지고 있는 intent를 함께 전달한다.
				finish();
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
	void signup(){
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}

}
