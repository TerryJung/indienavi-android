package kr.hs.kumoh.indieplatform.indie.navi.view.fragment;

import java.util.ArrayList;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.model.adapter.ArtistAdapter;
import kr.hs.kumoh.indieplatform.indie.navi.model.data.ArtistData;
import kr.hs.kumoh.indieplatform.indie.navi.view.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
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
		artistAdapter = new ArtistAdapter(getActivity(), getId(), artistData);
		lv.setAdapter(artistAdapter);
		loadPage();
		return root;
	}
	private void loadPage() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        int startIndex = 1 + artistData.size();
        JsonObjectRequest myReq = new JsonObjectRequest
        						(Method.GET, 
        						"http://chilchil.me/apps/server/indie/artist_list.php" ,
        						null, createMyReqSuccessListener(),
                                createMyReqErrorListener());

        queue.add(myReq);
    }
	private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject artist = response.getJSONObject("artist");
                    
                    JSONArray entries = artist.getJSONArray("artist");
//                    JSONObject entry;
                    for (int i = 0; i < entries.length(); i++) {
//                        entry = entries.getJSONObject(i);
                        
                        String url = null;
                        
                        artistData.add(new ArtistData(url, artist.getString("artist_name"), artist.getString("label"), artist.getString("debut_year"), artist.getString("genre")));
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
