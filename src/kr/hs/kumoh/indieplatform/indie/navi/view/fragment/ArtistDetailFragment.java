package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ArtistData;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.Constant;
import kr.hs.kumoh.indieplatform.indie.navi.view.activity.ArtistDetailActivity;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.androidquery.AQuery;

public class ArtistDetailFragment extends SherlockFragment{
	AQuery aq;// = new AQuery(getSherlockActivity());
	private ImageView artistImg;
	String encodeResult;
	private TextView artistName;
	private TextView artistFan;
	private TextView panTv;
	private TextView labelName;
	private TextView artistText;
	
	private String name = ArtistDetailActivity.ArtistName;
	String artistImgURLStr;
    String artistNameStr;
    String artistFanStr;
    String artistLabelStr;
    String artistTextStr;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		View root = inflater.inflate(R.layout.artist_detail_fragment, container, false);
		aq = new AQuery(getActivity(), root);
		Log.d("Artist Detail Constant", Constant.IMAGE_URL+"/artist/monni.jpg");
		aq.id(R.id.artistImgDetail).image(Constant.IMAGE_URL+"/artist/monni.jpg",true, true, R.drawable.no_image, AQuery.FADE_IN);
		artistImg = (ImageView) root.findViewById(R.id.artistImgDetail);
		artistName = (TextView) root.findViewById(R.id.artistName);
		artistFan = (TextView) root.findViewById(R.id.artistPan);
		panTv = (TextView) root.findViewById(R.id.PanTv);
		labelName = (TextView) root.findViewById(R.id.labelName);
		artistText = (TextView) root.findViewById(R.id.artistText);
		
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
						Log.d("URL", Constant.IMAGE_URL+artistImgURLStr);
						artistName.setText(artistNameStr);
						artistFan.setText(artistFanStr);
						labelName.setText(artistLabelStr);
						artistText.setText(artistTextStr);
					}
					
				});
			}
		}).start();
		return root;
	}
	public String readArtist() {
		try {
			encodeResult = URLEncoder.encode(name, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(Constant.SERVER_URL+"apps/server/indie/artist_detail.php?artist="+encodeResult);
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
}
