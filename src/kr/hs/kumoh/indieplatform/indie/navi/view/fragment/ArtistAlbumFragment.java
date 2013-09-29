package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.controller.net.MyVolley;
import kr.hs.kumoh.indieplatform.indie.navi.model.adapter.ArtistAlbumAdapter;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.AlbumData;

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

public class ArtistAlbumFragment extends SherlockFragment {
	String name = "몽니";
	ListView lv;
	private boolean mHasData = false;
    private boolean mInError = false;
    private ArrayList<AlbumData> albumData = new ArrayList<AlbumData>();
	private ArtistAlbumAdapter albumAdapter;
	private AlbumData data;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.artist_album_fragment, container, false);
		// TODO Auto-generated method stub
		albumData = new ArrayList<AlbumData>();
		lv = (ListView) root.findViewById(R.id.albumList);
		albumAdapter = new ArtistAlbumAdapter(getSherlockActivity(), 0, albumData, MyVolley.getImageLoader());
		lv.setAdapter(albumAdapter);
		lv.setClickable(false);
//		loadPage();========
		return root;
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
        						"http://chilchil.me/apps/server/indie/artist_album.php?name=몽니",
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
                    for (int i = 0; i < entries.length(); i++) {
                    	entry = entries.getJSONObject(i);         
                    	
                    	albumData.add(new AlbumData(null, entry.getString("album_title"), 
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
