package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.ArtistAlbumFragment;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.ArtistConcertFragment;
import kr.hs.kumoh.indieplatform.indie.navi.view.fragment.ArtistDetailFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class ArtistDetailActivity extends SherlockFragmentActivity {
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private Button albumInfoBtn;
	private Button concertInfoBtn;
	private Button panclubBtn;
	FragmentTransaction ft;
	
	ArtistAlbumFragment aaf = new ArtistAlbumFragment();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist_detail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.ArtistDetailFrame, new ArtistDetailFragment());
		
		
		
		
		fragmentTransaction.commit();
		albumInfoBtn = (Button) findViewById(R.id.albumInfoBtn);
		albumInfoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ft = fragmentManager.beginTransaction().replace(R.id.ArtistDetailFrame, aaf);
				ft.addToBackStack("w");
				ft.commit();
				
			}
		});
		concertInfoBtn = (Button) findViewById(R.id.concertInfoBtn);
		concertInfoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ft = fragmentManager.beginTransaction().replace(R.id.ArtistDetailFrame, new ArtistConcertFragment());
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		panclubBtn = (Button) findViewById(R.id.fanclubBtn);
		panclubBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
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
//	String artistName, String labelName, String artistDesc, String likeCnt, String artistImgURL
	
}
