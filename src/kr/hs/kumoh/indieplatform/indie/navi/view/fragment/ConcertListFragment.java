package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.controller.net.MyVolley;
import kr.hs.kumoh.indieplatform.indie.navi.model.adapter.ConcertAdapter;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ConcertData;
import kr.hs.kumoh.indieplatform.indie.navi.util.Constant;
import kr.hs.kumoh.indieplatform.indie.navi.view.activity.ConcertDetailActivity;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.ArtistListFragment.EndlessScrollListener;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flurry.android.FlurryAgent;

public class ConcertListFragment extends SherlockFragment {
	ListView lv;
	private boolean mHasData = false;
    private boolean mInError = false;
	private ArrayList<ConcertData> concertData = new ArrayList<ConcertData>();
	private ConcertAdapter concertAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View root = inflater.inflate(R.layout.concert_fragment, container, false);
		concertData = new ArrayList<ConcertData>();
		lv = (ListView) root.findViewById(R.id.concertList);
		concertAdapter = new ConcertAdapter(getSherlockActivity(), 0, concertData, MyVolley.getImageLoader());
		lv.setAdapter(concertAdapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), ConcertDetailActivity.class);
				i.putExtra("concertName", concertData.get(position).getConcertName());
				i.putExtra("placeName", concertData.get(position).getPlaceName());
				i.putExtra("concertDate", concertData.get(position).getConcertDate());
				i.putExtra("concertImg", concertData.get(position).getConcertImgURL());
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

        int startIndex = concertData.size();
        JsonObjectRequest myReq = new JsonObjectRequest
        						(Method.GET, 
        								Constant.SERVER_URL+"apps/server/indie/concert_list.php?start="+startIndex+"&alt=5",
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
                    JSONArray entries = feed.getJSONArray("concert");
                    JSONObject entry;
                    for (int i = 0; i < entries.length(); i++) {
                    	entry = entries.getJSONObject(i);         
                    	String url = entry.getString("concert_img_url");
                    	Log.d("TAG", url);
                    	concertData.add(new ConcertData(entry.getString("concert_name"), 
                    						entry.getString("place"), entry.getString("concert_date"), entry.getString("concert_img_url")));
                    }
                    concertAdapter.notifyDataSetChanged();
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
}
