package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.controller.net.MyVolley;
import kr.hs.kumoh.indieplatform.indie.navi.model.adapter.ArtistConcertAdapter;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ConcertData;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.Constant;
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

public class ArtistConcertFragment extends SherlockFragment {
	String name = ArtistDetailActivity.ArtistName;
	private String encodeResult;
	ListView lv;
	private boolean mHasData = false;
    private boolean mInError = false;
	private ArrayList<ConcertData> concertData = new ArrayList<ConcertData>();
	private ArtistConcertAdapter arconcertAdapter;
	private ConcertData data;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			encodeResult = URLEncoder.encode(name, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("encode URL " , encodeResult);
		View root = inflater.inflate(R.layout.artist_concert_fragment, container, false);
		// TODO Auto-generated method stub
		concertData = new ArrayList<ConcertData>();
		lv = (ListView) root.findViewById(R.id.artist_concert);
		arconcertAdapter = new ArtistConcertAdapter (getSherlockActivity(), 0, concertData, MyVolley.getImageLoader());
		lv.setAdapter(arconcertAdapter);
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
        								Constant.SERVER_URL+"apps/server/indie/artist_concert.php?name="+encodeResult,
        						null, createMyReqSuccessListener(),
                                createMyReqErrorListener());

        queue.add(myReq);
    }
	private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                	JSONObject feed = response.getJSONObject("feed");
                    JSONArray entries = feed.getJSONArray("artist_concert");
                    Log.d("Artist Concert", "json Array artist concert");
                    JSONObject entry;
                    for (int i = 0; i < entries.length(); i++) {
                    	Log.d("Artist Concert", "json Array artist concert parse for");
                    	// 포문안에서 파싱이 제대로 안됨 
                    	entry = entries.getJSONObject(i);         

                    	concertData.add(new ConcertData(entry.getString("concert_name"), 
                    						entry.getString("place"), entry.getString("concert_date"), entry.getString("concert_img_url")));
                    }
                    Log.d("Artist Concert", "json Array artist concert parse end");
                    arconcertAdapter.notifyDataSetChanged();
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
