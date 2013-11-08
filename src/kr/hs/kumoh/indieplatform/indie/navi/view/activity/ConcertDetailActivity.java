package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.model.adapter.ConcertReplyAdapter;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ConcertReplyData;
import kr.hs.kumoh.indieplatform.indie.navi.util.Constant;

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
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.flurry.android.FlurryAgent;

public class ConcertDetailActivity extends SherlockActivity {
	
	private AQuery aq;
//	private ScrollView replyScroll;
	private ImageView concertImage;
	private TextView concertNameTv;
	private TextView concertPlaceTv;
	private TextView concertDateTv;
	private TextView concertDescriptionTv;
	private TextView concertReplyUserName;
    private EditText replyEditText;
	private ImageView replySubmit;
	private ListView replyList;
	private Date d = new java.util.Date();
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	private String today = df.format(d); 
	private String concertName;
	private String encodeResult;
	private String placeStr; 
	private String dateStr;
	private String descStr;
	private String linkStr;
	private String concertImgStr;
	
	private String reply;
	
	private String concertURLEncode;
	private boolean mHasData = false;
    private boolean mInError = false;
    private ArrayList<ConcertReplyData> replyData = new ArrayList<ConcertReplyData>();
	private ConcertReplyAdapter replyAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_concert_detail);
		
		aq = new AQuery(this);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF2ecc71));
		Intent intent = getIntent();
		concertName = intent.getExtras().getString("concertName");
		try {
			encodeResult = URLEncoder.encode(concertName, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		placeStr = intent.getExtras().getString("placeName");
		dateStr = intent.getExtras().getString("concertDate");
		concertImgStr = intent.getExtras().getString("concertImg");
		concertImage = (ImageView) findViewById(R.id.concertImgDetail);
		aq.id(R.id.concertImgDetail).image(concertImgStr);//s,true, true, R.drawable.no_image, AQuery.FADE_IN);
		concertImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ConcertDetailActivity.this, ImageActivity.class);
				i.putExtra("imageURL", concertImgStr);
				i.putExtra("imageName", concertName);
				startActivity(i);
			}
		});
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		replyData = new ArrayList<ConcertReplyData>();

//		replyScroll = (ScrollView) findViewById(R.id.replyScroll);
		concertNameTv = (TextView) findViewById(R.id.concertDetailName);
		
		concertPlaceTv = (TextView) findViewById(R.id.concertDetailPlace);
		
		concertDateTv = (TextView) findViewById(R.id.concertDetailDate);
		
		concertDescriptionTv = (TextView) findViewById(R.id.concertDetailText);
		
		
//		concertDateTv.setFT
		// Reply 부분 
		concertReplyUserName = (TextView) findViewById(R.id.userName);
		concertReplyUserName.setText(Constant.USER_NAME);
		
		concertNameTv.setText(concertName);
		concertPlaceTv.setText(placeStr);
		concertDateTv.setText(dateStr);
//		initFont();
		
		replyEditText = (EditText) findViewById(R.id.replyEdit);

//		Util.fontBold(this, concertNameTv);
//		Util.fontGeneral(this, concertPlaceTv);
//		Util.fontGeneral(this, concertDateTv);
//		Util.fontGeneral(this, concertDescriptionTv);
//		Util.fontGeneral(this, concertReplyUserName);
//		Typeface type = Typeface.createFromAsset(getAssets(), "NanumBarunGothic.ttf");
//		replyEditText.setTypeface(type);
		//폰트
		replySubmit = (ImageView) findViewById(R.id.replySubmit);
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
							Looper.prepare();
							try {
								writeReply(concertReplyUserName.getText().toString(), 
									replyEditText.getText().toString());
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}).start();
					Looper.loop();
//					replyScroll.invalidate();
//					replyScroll.requestLayout();
				}
			}
		});
		replyList = (ListView) findViewById(R.id.replyList);
		replyAdapter = new ConcertReplyAdapter(this, 0, replyData);
		replyList.setAdapter(replyAdapter);
//		replyList.setScr
				
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
	private void writeReply( final String userName,  final String replyContent) throws IOException{
		URL url = new URL(Constant.SERVER_URL+"apps/server/indie/concert_reply_add.php");
		HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();

		httpUrl.setDefaultUseCaches(false);
		httpUrl.setDoInput(true);
		httpUrl.setDoOutput(true);
		httpUrl.setRequestMethod("POST");
		httpUrl.setRequestProperty("content-type", "application/x-www-form-urlencoded");

		StringBuffer sb = new StringBuffer();
		sb.append("name").append("=").append(userName).append("&");
		sb.append("concert").append("=").append(concertName).append("&");
		sb.append("reply").append("=").append(replyContent);
		
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpUrl.getOutputStream(), "UTF-8"));
		pw.write(sb.toString());
		pw.flush();
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), "UTF-8"));
		StringBuilder buff = new StringBuilder();
		String line;
		while((line = bf.readLine())!=null) {
			buff.append(line);
			if(line.equalsIgnoreCase("reply_OK")){
				runOnUiThread(new Runnable() {
                  public void run() {
                      Toast.makeText(ConcertDetailActivity.this,"댓글 성공", Toast.LENGTH_SHORT).show();
                      replyData.add(new ConcertReplyData(userName, replyContent, today));
                      replyAdapter.notifyDataSetChanged();
                      replyEditText.setText("");
                  }
              });				
			}
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
