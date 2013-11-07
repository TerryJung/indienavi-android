package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.controller.net.MyVolley;
import kr.hs.kumoh.indieplatform.indie.navi.model.adapter.ArtistAdapter;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ArtistData;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ConcertReplyData;
import kr.hs.kumoh.indieplatform.indie.navi.util.Constant;
import kr.hs.kumoh.indieplatform.indie.navi.view.activity.ArtistDetailActivity;
import kr.hs.kumoh.indieplatform.indie.navi.view.activity.ConcertDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class FavoriteArtistListFragment extends SherlockFragment {
	static final int DIALOG_SELECT = 0;
	ListView lv;
	private boolean mHasData = false;
    private boolean mInError = false;
	private ArrayList<ArtistData> artistData = new ArrayList<ArtistData>();
	private ArtistAdapter artistAdapter;
//	private ArtistData data;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.artist_fragment, container, false);
//		SharedPreferences userinfo = getShare
		artistData = new ArrayList<ArtistData>();
		lv = (ListView) root.findViewById(R.id.listView1);
		artistAdapter = new ArtistAdapter(getActivity(),0, artistData, MyVolley.getImageLoader());
		lv.setAdapter(artistAdapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), ArtistDetailActivity.class);
//				Intent i = new Intent(getActivity(), ArtistDetailActivity.class);
				i.putExtra("artist", artistData.get(position).getArtistName());
				i.putExtra("artistImg", artistData.get(position).getArtistImgURL());
				startActivity(i);	
				
			}
		});
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> av, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				
				
				String artistID = artistData.get(position).getArtistID();
				String artistName = artistData.get(position).getArtistName();
				dia(artistName, artistID, position);
//				deleteFavorite(Constant.USER_NAME, artistID);
				return false;
			}
		});
		return root;
	}
	private void deleteFavorite(String name, String artistID, int position) throws IOException{
		URL url = new URL(Constant.SERVER_URL+"apps/server/indie/favorite_artist_delete.php");
		HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();

		httpUrl.setDefaultUseCaches(false);
		httpUrl.setDoInput(true);
		httpUrl.setDoOutput(true);
		httpUrl.setRequestMethod("POST");
		httpUrl.setRequestProperty("content-type", "application/x-www-form-urlencoded");

		StringBuffer sb = new StringBuffer();
		sb.append("name").append("=").append(name).append("&");
		sb.append("artist_id").append("=").append(artistID);
			
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpUrl.getOutputStream(), "UTF-8"));
		pw.write(sb.toString());
		pw.flush();
			
		BufferedReader bf = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), "UTF-8"));
		StringBuilder buff = new StringBuilder();
		String line;
		while((line = bf.readLine())!=null) {
			buff.append(line);
			Log.d("RESPONSE", line);
			if(line.equalsIgnoreCase("OK")){
//				runOnUi
//				artistAdapter.remove(artistData.get(position));
			}
		}	
	}
	private void dia(String artistName, final String artistID, final int position) {
		AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
		alert_confirm.setMessage(artistName+" 을(를) 팬클럽에서 삭제하시겠습니까?")
		.setCancelable(false)
		.setPositiveButton("확인",
		new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        // 'YES'
		    	new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							deleteFavorite(Constant.USER_NAME, artistID, position);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
		    	
		    }
		}).setNegativeButton("취소",
		new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        // 'No'
		    return;
		    }
		});
		AlertDialog alert = alert_confirm.create();
		alert.show();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!mHasData && !mInError) {
	            loadPage();
	    }
	}
	
	private void loadPage() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
//        SharedPreferences userinfo = getShared
//        int startIndex = 1 + artistData.size();
        JsonObjectRequest myReq = new JsonObjectRequest
        						(Method.GET, 
        								Constant.SERVER_URL+"apps/server/indie/favorite_artist_list.php?name="+Constant.USER_NAME ,
        						null, createMyReqSuccessListener(),
                                createMyReqErrorListener());

        queue.add(myReq);
    }
	private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                	
//                	String JSON = ""
                	JSONObject feed = response.getJSONObject("feed");
                    JSONArray entries = feed.getJSONArray("favorite_artist");
                    JSONObject entry;
                    for (int i = 0; i < entries.length(); i++) {
                    	entry = entries.getJSONObject(i);         
                    	String url = entry.getString("artist_img_url");
                    	Log.d("TAG", url);
                        artistData.add(new ArtistData(entry.getString("artist_id"), entry.getString("artist_img_url"), entry.getString("artist_name"), 
                        					entry.getString("label"), entry.getString("debut_year"), 
                        					entry.getString("genre"), entry.getString("like_count")));
                    }
                    artistAdapter.notifyDataSetChanged();
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
	        
	        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
	        b.setMessage("불러올수 없습니다");
	      
	        b.show();
	 }   
}

