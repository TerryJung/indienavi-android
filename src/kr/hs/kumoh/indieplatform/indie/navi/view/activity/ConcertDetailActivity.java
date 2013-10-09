package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.controller.net.MyVolley;
import kr.hs.kumoh.indieplatform.indie.navi.model.adapter.ArtistAlbumAdapter;
import kr.hs.kumoh.indieplatform.indie.navi.model.adapter.ConcertReplyAdapter;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.AlbumData;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ConcertReplyData;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.Constant;

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

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;

public class ConcertDetailActivity extends SherlockActivity {
	AQuery aq = new AQuery(this);
	ImageView concertImage;
	TextView concertNameTv;
	TextView concertPlaceTv;
	TextView concertDateTv;
	TextView concertDescriptionTv;
	TextView concertReplyUserName;
	
	EditText replyEditText;
	Button replySubmit;
	ListView replyList;
	
	String concertName;
	private String encodeResult;
	String imgURL;
	String nameStr;
	String placeStr; 
	String dateStr;
	String descStr;
	String linkStr;
	String concertImgStr;
	
	private boolean mHasData = false;
    private boolean mInError = false;
    private ArrayList<ConcertReplyData> replyData = new ArrayList<ConcertReplyData>();
	private ConcertReplyAdapter replyAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_concert_detail);
		try {
				encodeResult = URLEncoder.encode(concertName, "UTF8");
		} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} // 내일 테스트해봐야함
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
		
		replyData = new ArrayList<ConcertReplyData>();

		concertImage = (ImageView) findViewById(R.id.concertImgDetail);
		concertNameTv = (TextView) findViewById(R.id.concertDetailName);
		concertPlaceTv = (TextView) findViewById(R.id.concertDetailPlace);
		concertDateTv = (TextView) findViewById(R.id.concertDetailDate);
		concertDescriptionTv = (TextView) findViewById(R.id.concertDetailText);
		
		// Reply 부분 
		concertReplyUserName = (TextView) findViewById(R.id.userName);
		replyEditText = (EditText) findViewById(R.id.replyEdit);
		replySubmit = (Button) findViewById(R.id.replySubmit);
		replyList = (ListView) findViewById(R.id.replyList);
		replyAdapter = new ConcertReplyAdapter(this, 0, replyData);
		replyList.setAdapter(replyAdapter);
				
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
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
		
		if (!mHasData && !mInError) {
            loadReply();
		}
	}
	private void loadReply() {
        RequestQueue queue = Volley.newRequestQueue(this);

//        int startIndex = albumData.size();
        JsonObjectRequest myReq = new JsonObjectRequest
        						(Method.GET, 
        								Constant.SERVER_URL+"apps/server/indie/concert_reply.php?concert="+encodeResult,
        						null, createMyReqSuccessListener(),
                                createMyReqErrorListener());

//        Log.d("loadpage", "http://chilchil.me/apps/server/indie/artist_album.php?name="+name);
        queue.add(myReq);
    }
	private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                	
                	JSONObject feed = response.getJSONObject("feed");
                    JSONArray entries = feed.getJSONArray("concert_reply");
                    JSONObject entry;
                    for (int i = 0; i < entries.length(); i++) {
                    	
                    	entry = entries.getJSONObject(i);         
                    	
                    	
                    	replyData.add(new ConcertReplyData(entry.getString("name"), entry.getString("reply"), entry.getString("date")));
                    }
                    replyAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    showErrorDialog();
                }
            }
        };
    }
	 private Response.ErrorListener createMyReqErrorListener() {
	        return new Response.ErrorListener() {
	            @Override
	            public void onErrorResponse(VolleyError error) {
	                showErrorDialog();
	            }
	        };
	 }
	 private void showErrorDialog() {
	        mInError = true;
	        
	        AlertDialog.Builder b = new AlertDialog.Builder(this);
	        b.setMessage("불러올수 없습니다");
	      
	        b.show();
	 }   
	
	 // 공연 정보 받아오는 메소드
	public String readConcert() {
	    StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    Log.d("concert Detail", concertName);
	   
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
