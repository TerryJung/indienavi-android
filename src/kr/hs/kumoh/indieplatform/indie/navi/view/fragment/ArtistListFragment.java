package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.controller.net.MyVolley;
import kr.hs.kumoh.indieplatform.indie.navi.model.adapter.ArtistAdapter;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ArtistData;
import kr.hs.kumoh.indieplatform.indie.navi.view.activity.ArtistDetailActivity;
import kr.hs.kumoh.indieplatform.indie.navi.view.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class ArtistListFragment extends SherlockFragment {
	
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
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), ArtistDetailActivity.class);
//				i.putE
				startActivity(i);
				
			}
		});
		lv.setOnScrollListener(new EndlessScrollListener());
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

        int startIndex = artistData.size();
        JsonObjectRequest myReq = new JsonObjectRequest
        						(Method.GET, 
        						"http://chilchil.me/apps/server/indie/artist_list.php?start="+startIndex+"&alt=10" ,
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
                    	String url = entry.getString("artist_img_url");
                    	Log.d("TAG", url);
                        artistData.add(new ArtistData(entry.getString("artist_img_url"), entry.getString("artist_name"), 
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
	 public class EndlessScrollListener implements OnScrollListener {
	        // how many entries earlier to start loading next page
	        private int visibleThreshold = 5;
	        private int currentPage = 0;
	        private int previousTotal = 0;
	        private boolean loading = true;

	        public EndlessScrollListener() {
	        }
	        public EndlessScrollListener(int visibleThreshold) {
	            this.visibleThreshold = visibleThreshold;
	        }
	        
	        public int getCurrentPage() {
	            return currentPage;
	        }
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
				// TODO Auto-generated method stub
				 if (loading) {
		                if (totalItemCount > previousTotal) {
		                    loading = false;
		                    previousTotal = totalItemCount;
		                    currentPage++;
		                }
		            }
		            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
		                // I load the next page of gigs using a background task,
		                // but you can call any function here.
		                loadPage();
		                loading = true;
		            }
			}
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
	    }
}
