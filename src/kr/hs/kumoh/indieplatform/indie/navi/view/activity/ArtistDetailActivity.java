package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;

public class ArtistDetailActivity extends SherlockFragmentActivity {

	AQuery aq = new AQuery(this);
	private ImageView artistImg;
	private TextView artistName;
	private TextView artistPan;
	private TextView panTv;
	private TextView labelName;
	private TextView artistText;
	private Button albumInfoBtn;
	private Button concertInfoBtn;
	private Button panclubBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist_detail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		artistImg = (ImageView) findViewById(R.id.artistImg);
		
		artistName = (TextView) findViewById(R.id.artistName);
		artistPan = (TextView) findViewById(R.id.artistPan);
		panTv = (TextView) findViewById(R.id.PanTv);
		labelName = (TextView) findViewById(R.id.labelName);
		artistText = (TextView) findViewById(R.id.artistText);
		albumInfoBtn = (Button) findViewById(R.id.albumInfoBtn);
		albumInfoBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		concertInfoBtn = (Button) findViewById(R.id.albumInfoBtn);
		concertInfoBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		panclubBtn = (Button) findViewById(R.id.albumInfoBtn);
		panclubBtn.setOnClickListener(new View.OnClickListener() {
			
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
}
