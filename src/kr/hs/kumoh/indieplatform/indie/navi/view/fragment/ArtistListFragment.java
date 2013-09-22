package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.controller.net.MyVolley;
import kr.hs.kumoh.indieplatform.indie.navi.model.adapter.ArtistAdapter;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ArtistData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class ArtistListFragment extends Fragment {
	
//	ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
	ListView lv;
	private boolean mHasData = false;
    private boolean mInError = false;
	private ArrayList<ArtistData> artistData = new ArrayList<ArtistData>();
	private ArtistAdapter artistAdapter;
	private ArtistData data;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.artist_fragment, container, false);
		
		artistData = new ArrayList<ArtistData>();
		lv = (ListView) root.findViewById(R.id.listView1);
		artistAdapter = new ArtistAdapter(getActivity(),0, artistData, MyVolley.getImageLoader());
		lv.setAdapter(artistAdapter);
		
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

        int startIndex = 1 + artistData.size();
        JsonObjectRequest myReq = new JsonObjectRequest
        						(Method.GET, 
        						"http://chilchil.me/apps/server/indie/artist_list.php?alt=10" ,
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
                    JSONArray entries = feed.getJSONArray("artist");
                    JSONObject entry;
                    for (int i = 0; i < entries.length(); i++) {
                    	entry = entries.getJSONObject(i);                   
                        
                        artistData.add(new ArtistData("http://img.maniadb.com/images/artist/134/134096.jpg", entry.getString("artist_name"), 
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
