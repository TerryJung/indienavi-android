package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.R.layout;
import kr.hs.kumoh.indieplatform.indie.navi.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ArtistDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist_detail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.artist_detail, menu);
		return true;
	}

}
