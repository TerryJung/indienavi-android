package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import java.io.InputStream;
import java.net.URL;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.androidquery.AQuery;

public class ImageActivity extends SherlockActivity {

	private ImageView mImageView;
	private PhotoViewAttacher mAttacher;
	private AQuery aq;
//	private ProgressBar pb;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image);
		Intent intent = getIntent();
		
//		ArtistName = intent.getExtras().getString("imageURL");
//		ArtistImg = intent.getExtras().getString("artistImg");
		aq = new AQuery(this);
		mImageView = (ImageView) findViewById(R.id.detailImage);
		aq.id(R.id.detailImage).image(intent.getExtras().getString("imageURL"));
	    mAttacher = new PhotoViewAttacher(mImageView);
	    mAttacher.update();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_image, menu);
		return true;
	}

}
