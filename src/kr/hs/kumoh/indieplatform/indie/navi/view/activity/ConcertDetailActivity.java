package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.model.adapter.ConcertReplyAdapter;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ConcertReplyData;
import kr.hs.kumoh.indieplatform.indie.navi.util.Constant;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
    private List<NameValuePair> nameValuePairs;
    private HttpResponse response;
	EditText replyEditText;
	Button replySubmit;
	ListView replyList;
	private Date d = new java.util.Date();
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	private String today = df.format(d); 
	String concertName;
	private String encodeResult;
	String imgURL;
	String nameStr;
	String placeStr; 
	String dateStr;
	String descStr;
	String linkStr;
	String concertImgStr;
	
	String reply;
	
	private String concertURLEncode;
	private boolean mHasData = false;
    private boolean mInError = false;
    private ArrayList<ConcertReplyData> replyData = new ArrayList<ConcertReplyData>();
	private ConcertReplyAdapter replyAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_concert_detail);
		

		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF2ecc71));
		Intent intent = getIntent();
		concertName = intent.getExtras().getString("concertName");
		try {
			encodeResult = URLEncoder.encode(concertName, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 내일 테스트해봐야함
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
		concertReplyUserName.setText(Constant.USER_NAME);
		replyEditText = (EditText) findViewById(R.id.replyEdit);
		replySubmit = (Button) findViewById(R.id.replySubmit);
		replySubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(replyEditText.getText().toString().equals("")){
					replyEditText.setHint("덧글을 작성하세요");
				} else {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
//							Looper.prepare();
							writeReply(concertReplyUserName.getText().toString(), 
									replyEditText.getText().toString());
						}
					}).start();
//					Looper.loop();
				}
				
			}
		});
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
	    
	    HttpGet httpGet = new HttpGet(Constant.SERVER_URL+"apps/server/indie/concert_detail.php?concert="+encodeResult);
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
	    		Log.e(ConcertDetailActivity.class.toString(), "Failed to download file");
	    	}
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    return builder.toString();
	}
	private void writeReply(final String userName, final String replyContent) {
		try{            
			HttpClient httpclient = getThreadSafeClient();
			HttpPost httppost = new HttpPost(Constant.SERVER_URL+"apps/server/indie/concert_reply_add.php"); // make sure the url is correct.
            //add your post data
            nameValuePairs = new ArrayList<NameValuePair>(3);
            // name concert reply
            // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
            nameValuePairs.add(new BasicNameValuePair("name",userName));             
            nameValuePairs.add(new BasicNameValuePair("concert", concertName));
            nameValuePairs.add(new BasicNameValuePair("reply",replyContent)); 
//            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8);
//            httppost.setEntity(ent);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            
            Log.d("TAG"	, userName + "/" + concertName + "/"+ replyContent);
           
            
            //Execute HTTP Post Request
            response = httpclient.execute(httppost);
            // edited by James from coderzheaven.. from here....
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            System.out.println("Response : " + response); 
            runOnUiThread(new Runnable() {
                public void run() {
                   // tv.setText("Response from PHP : " + response);
//                    dialog.dismiss();
                }
            });
             
            if(response.equalsIgnoreCase("reply_OK")){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(ConcertDetailActivity.this,"댓글 성공", Toast.LENGTH_SHORT).show();
                        replyData.add(new ConcertReplyData(userName, replyContent, today));
                        replyAdapter.notifyDataSetChanged();
                    }
                });
                
            }else{}
             
        }catch(IOException e){
//            dialog.dismiss();
            System.out.println("IOException : " + e.getMessage());
        }
	}
	private static DefaultHttpClient getThreadSafeClient()  {

        DefaultHttpClient client = new DefaultHttpClient();
        ClientConnectionManager mgr = client.getConnectionManager();
        HttpParams params = client.getParams();
        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, 
                mgr.getSchemeRegistry()), params);
        return client;
	}
}
