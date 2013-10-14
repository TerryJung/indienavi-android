package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.Constant;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.ArtistAlbumFragment;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.ArtistConcertFragment;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.ArtistDetailFragment;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class ArtistDetailActivity extends SherlockFragmentActivity {
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private ImageView albumInfoBtn;
	private ImageView concertInfoBtn;
	private ImageView artistInfoBtn;
	private ImageView favoriteAddBtn;
	FragmentTransaction ft;
	public static String ArtistName;
	public static String ArtistImg;
	ArtistAlbumFragment alf = new ArtistAlbumFragment();
	private String encodeResult;
	
	private HttpPost httppost;
	
    private HttpResponse response;
    private HttpClient httpclient;
    private List<NameValuePair> nameValuePairs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist_detail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF2ecc71));
		Intent intent = getIntent();
//		alf.
		ArtistName = intent.getExtras().getString("artist");
		ArtistImg = intent.getExtras().getString("artistImg");
		Log.d("INTENT", ArtistName);
		Log.d("Intent URL ", ArtistImg);
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.ArtistDetailFrame, new ArtistDetailFragment());
		fragmentTransaction.commit();
		albumInfoBtn = (ImageView) findViewById(R.id.albumInfoBtn);
		albumInfoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ft = fragmentManager.beginTransaction().replace(R.id.ArtistDetailFrame, new ArtistAlbumFragment());
				ft.addToBackStack("w");
				ft.commit();	
			}
		});
		concertInfoBtn = (ImageView) findViewById(R.id.concertInfoBtn);
		concertInfoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ft = fragmentManager.beginTransaction().replace(R.id.ArtistDetailFrame, new ArtistConcertFragment());
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		artistInfoBtn = (ImageView) findViewById(R.id.artistInfoBtn);
		artistInfoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ft = fragmentManager.beginTransaction().replace(R.id.ArtistDetailFrame, new ArtistDetailFragment());
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		favoriteAddBtn = (ImageView) findViewById(R.id.fanAdd);
		favoriteAddBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						fanAdd(Constant.USER_NAME, ArtistName);
					}
				}).start();
				
			}
		});
	}
	public void fanAdd(String name, String artist)  {
		try{            
            
//			HttpClient cl
			httpclient = getThreadSafeClient();
            httppost = new HttpPost(Constant.SERVER_URL+"apps/server/indie/favorite_artist_add.php"); // make sure the url is correct.
            //add your post data
            nameValuePairs = new ArrayList<NameValuePair>(2);
            // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
            nameValuePairs.add(new BasicNameValuePair("name",name));  // $Edittext_value = $_POST['Edittext_value'];
            nameValuePairs.add(new BasicNameValuePair("artist",artist)); 
            Log.d("httpConn", name+" & "+artist);
            
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            //Execute HTTP Post Request
            response = httpclient.execute(httppost);
            // edited by James from coderzheaven.. from here....
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            System.out.println("Response : " + response); 
//            runOnUiThread(new Runnable() {
//                public void run() {
//                   // tv.setText("Response from PHP : " + response);
////                    dialog.dismiss();
//                }
//            });
             
            if(response.equalsIgnoreCase("OK")){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(ArtistDetailActivity.this,"팬이 추가되었습니다", Toast.LENGTH_SHORT).show();
                    }
                });

            }else{}
             
        } catch (ClientProtocolException e) {
			// TODO: handle exception
        	e.printStackTrace();
		} catch(IOException e){
//            dialog.dismiss();
			e.printStackTrace();
            System.out.println("IOException : " + e.getMessage());
        } 
		
	}
	
	
	public static DefaultHttpClient getThreadSafeClient()  {

        DefaultHttpClient client = new DefaultHttpClient();
        ClientConnectionManager mgr = client.getConnectionManager();
        HttpParams params = client.getParams();
        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, 

                mgr.getSchemeRegistry()), params);
        return client;
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
