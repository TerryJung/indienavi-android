package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.controller.net.MyVolley;
import kr.hs.kumoh.indieplatform.indie.navi.model.adapter.ArtistAlbumAdapter;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.AlbumData;
import kr.hs.kumoh.indieplatform.indie.navi.util.Constant;
import kr.hs.kumoh.indieplatform.indie.navi.view.activity.ArtistDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flurry.android.FlurryAgent;
public class ArtistAlbumFragment extends SherlockFragment {
	String name = ArtistDetailActivity.ArtistName;
	private String encodeResult;
	ListView lv;
	private boolean mHasData = false;
    private boolean mInError = false;
    private ArrayList<AlbumData> albumData = new ArrayList<AlbumData>();
	private ArtistAlbumAdapter albumAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			encodeResult = URLEncoder.encode(name, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		View root = inflater.inflate(R.layout.artist_album_fragment, container, false);
		// TODO Auto-generated method stub
		albumData = new ArrayList<AlbumData>();
		lv = (ListView) root.findViewById(R.id.albumList);
		albumAdapter = new ArtistAlbumAdapter(getSherlockActivity(), 0, albumData, MyVolley.getImageLoader());
		lv.setAdapter(albumAdapter);
		lv.setClickable(false);
		return root;
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		FlurryAgent.onStartSession(getActivity(), Constant.FLURRY_API_KEY);
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		FlurryAgent.onEndSession(getActivity());
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

//        int startIndex = albumData.size();
        JsonObjectRequest myReq = new JsonObjectRequest
        						(Method.GET, 
        								Constant.SERVER_URL+"apps/server/indie/artist_album.php?name="+encodeResult,
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
                    JSONArray entries = feed.getJSONArray("artist_album");
                    JSONObject entry;
//                    Log.d("Artist AlbumFragment", Integer.toString(entries.length()));
                    for (int i = 0; i < entries.length(); i++) {
//                    	Log.d("Artist AlbumFragment", Integer.toString(i));
                    	// 포문안에서 파싱이 제대로 안됨 
                    	entry = entries.getJSONObject(i);         
                    	Log.d("Artist AlbumFragment", entry.getString("album_cover_url"));
                    	albumData.add(new AlbumData(entry.getString("album_cover_url"), entry.getString("album_title"), 
                        					entry.getString("year"), entry.getString("title_song")));
                    }
                    albumAdapter.notifyDataSetChanged();
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
