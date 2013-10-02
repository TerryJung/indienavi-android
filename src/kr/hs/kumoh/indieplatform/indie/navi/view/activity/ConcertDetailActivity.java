package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.hs.kumoh.indieplatform.indie.navi.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;

public class ConcertDetailActivity extends SherlockActivity {
	AQuery aq = new AQuery(this);
	ImageView concertImage;
	TextView concertNameTv;
	TextView concertPlaceTv;
	TextView concertDateTv;
	TextView concertDescriptionTv;
	String concertName;
	private String encodeResult;
	String imgURL;
	String nameStr;
	String placeStr; 
	String dateStr;
	String descStr;
	String linkStr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_concert_detail);
//		View v = null;
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF00edc6));
		Intent intent = getIntent();
		concertName = intent.getExtras().getString("concertName");
		placeStr = intent.getExtras().getString("placeName");
		dateStr = intent.getExtras().getString("concertDate");
		Log.d("concert Detail", concertName);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		concertImage = (ImageView) findViewById(R.id.concertImgDetail);
		concertNameTv = (TextView) findViewById(R.id.concertDetailName);
		concertPlaceTv = (TextView) findViewById(R.id.concertDetailPlace);
		concertDateTv = (TextView) findViewById(R.id.concertDetailDate);
		concertDescriptionTv = (TextView) findViewById(R.id.concertDetailText);
		
		final Handler handler = new Handler();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String readArtist = readConcert();
				try {
					JSONArray jsonArray = new JSONArray(readArtist);
					Log.i(ConcertDetailActivity.class.getName(),
					          "Number of entries " + jsonArray.length());
					JSONObject jsonObject = jsonArray.getJSONObject(0);
			        imgURL = jsonObject.getString("concert_img_url");
		            descStr = jsonObject.getString("concert_text");
		            linkStr = jsonObject.getString("link");
		        } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.d("ImageURL " , imgURL);
						concertNameTv.setText(concertName);
						concertPlaceTv.setText(placeStr);
						concertDateTv.setText(dateStr);
						concertDescriptionTv.setText(descStr);
					}
					
				});
				
			}
		}).start();
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
	public String readConcert() {
		
	    StringBuilder builder = new StringBuilder();
	    
	   
	    Log.d("concert Detail", concertName);
	    try {
			encodeResult = URLEncoder.encode(concertName, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Log.d("concert Detail", encodeResult);
	    new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    return builder.toString();
	}
	

}
