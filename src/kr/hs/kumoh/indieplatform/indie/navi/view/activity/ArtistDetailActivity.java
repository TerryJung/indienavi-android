package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ArtistData;

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

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;

public class ArtistDetailActivity extends SherlockFragmentActivity {

	AQuery aq = new AQuery(getParent());
	
	private ImageView artistImg;
	
	private TextView artistName;
	private TextView artistFan;
	private TextView panTv;
	private TextView labelName;
	private TextView artistText;
	
	private Button albumInfoBtn;
	private Button concertInfoBtn;
	private Button panclubBtn;
	
	private String name = "¸ù´Ï";
	
	private ArrayList<ArtistData> artistData = new ArrayList<ArtistData>();
	
	private boolean mHasData = false;
    private boolean mInError = false;
    String artistImgURLStr;
    String artistNameStr;
    String artistFanStr;
    String artistLabelStr;
    String artistTextStr;
    ArtistData artist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist_detail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		artistImg = (ImageView) findViewById(R.id.artistImgDetail);
//		artistImg.s
//		aq.id(R.id.artistImgDetail).image(ArtistData.IMAGE_URL+artist.getArtistImgURL(),true, true, R.drawable.no_image, AQuery.FADE_IN);
		artistName = (TextView) findViewById(R.id.artistName);
		artistFan = (TextView) findViewById(R.id.artistPan);
		panTv = (TextView) findViewById(R.id.PanTv);
		labelName = (TextView) findViewById(R.id.labelName);
		artistText = (TextView) findViewById(R.id.artistText);
		albumInfoBtn = (Button) findViewById(R.id.albumInfoBtn);
		albumInfoBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		concertInfoBtn = (Button) findViewById(R.id.concertInfoBtn);
		concertInfoBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		panclubBtn = (Button) findViewById(R.id.fanclubBtn);
		panclubBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		final Handler handler = new Handler();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String readArtist = readArtist();
				try {
					JSONArray jsonArray = new JSONArray(readArtist);
					Log.i(ArtistDetailActivity.class.getName(),
					          "Number of entries " + jsonArray.length());
					JSONObject jsonObject = jsonArray.getJSONObject(0);
			        Log.i(ArtistDetailActivity.class.getName(), jsonObject.getString("artist_img_url"));
			        Log.i(ArtistDetailActivity.class.getName(), jsonObject.getString("artist_name"));
					artistImgURLStr = jsonObject.getString("artist_img_url");
		            artistNameStr = jsonObject.getString("artist_name");
		            artistFanStr = jsonObject.getString("like_count");
		            artistLabelStr = jsonObject.getString("label");
		            artistTextStr = jsonObject.getString("artist_text");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.d(ArtistDetailActivity.class.getName()+ "AritstName", artistNameStr);
				handler.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						aq.id(R.id.artistImgDetail).image(ArtistData.IMAGE_URL+artistImgURLStr,true, true, R.drawable.no_image, AQuery.FADE_IN);
						artistName.setText(artistNameStr);
						artistFan.setText(artistFanStr);
						labelName.setText(artistLabelStr);
						artistText.setText(artistTextStr);
					}
					
				});
			}
		}).start();
	}
	
	public String readArtist() {
	    StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet("http://chilchil.me/apps/server/indie/artist_detail.php?artist="+name);
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
	    return builder.toString();
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
//	String artistName, String labelName, String artistDesc, String likeCnt, String artistImgURL
	
}
