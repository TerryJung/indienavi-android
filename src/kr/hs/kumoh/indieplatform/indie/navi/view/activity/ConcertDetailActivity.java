package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
	String concertImgStr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_concert_detail);
//		View v = null;
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF2ecc71));
		Intent intent = getIntent();
		concertName = intent.getExtras().getString("concertName");
		placeStr = intent.getExtras().getString("placeName");
		dateStr = intent.getExtras().getString("concertDate");
		concertImgStr = intent.getExtras().getString("concertImg");
		Log.d("URL", concertImgStr);
		aq.id(R.id.concertImgDetail).image(concertImgStr,true, true, R.drawable.no_image, AQuery.FADE_IN);
		
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

					JSONObject jsonObject = jsonArray.getJSONObject(0);
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
	    HttpClient client = new DefaultHttpClient();
	    Log.d("concert Detail", concertName);
	    try {
			encodeResult = URLEncoder.encode(concertName, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Log.d("concert Detail", encodeResult);
	    HttpGet httpGet = new HttpGet("http://chilchil.me/apps/server/indie/concert_detail.php?concert="+encodeResult);
	    try {
	    	HttpResponse response = client.execute(httpGet);
	    	StatusLine statusLine = response.getStatusLine();
	    	int statusCode = statusLine.getStatusCode();
	    	if (statusCode == 200) {
	    		HttpEntity entity = response.getEntity();
	    		InputStream content = entity.getContent();
	    		BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	    		String line;
	    		while ((line = reader.readLine()) != null) {
	    			builder.append(line);
	    		}
	    	} else {
	    		Log.e(ArtistDetailActivity.class.toString(), "Failed to download file");
	    	}
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    Log.i("",builder.toString()); 
	    return builder.toString();
	}
	
	
}
