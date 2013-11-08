package kr.hs.kumoh.indieplatform.indie.navi.view.activity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import kr.hs.kumoh.indieplatform.indie.navi.R;
import kr.hs.kumoh.indieplatform.indie.navi.util.Constant;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.androidquery.AQuery;
import com.flurry.android.FlurryAgent;

public class ImageActivity extends SherlockActivity {

	private ImageView mImageView;
	private PhotoViewAttacher mAttacher;
	private AQuery aq;
	private ImageView downBtn;
	private String imageURL;
	private String imageName;
	
	private File rootDir = Environment.getExternalStorageDirectory();
	private ProgressDialog mProgressDialog;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
//	private ProgressBar pb;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image);
		Intent intent = getIntent();
		checkAndCreateDirectory("/indienavi");
//		ArtistName = intent.getExtras().getString("imageURL");
//		ArtistImg = intent.getExtras().getString("artistImg");
		aq = new AQuery(this);
		mImageView = (ImageView) findViewById(R.id.detailImage);
		imageURL = intent.getExtras().getString("imageURL");
		Log.d("URL", imageURL);
		imageName = intent.getExtras().getString("imageName");
		aq.id(R.id.detailImage).image(imageURL);
		
	    mAttacher = new PhotoViewAttacher(mImageView);
	    mAttacher.update();
	    downBtn = (ImageView) findViewById(R.id.downloadBtn);
	    downBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DownloadFileAsync().execute(imageURL);
//				Toast.makeText(this, "다운로드 완료", Toast.LENGTH_LONG).show();
//				Toast.makeText(getApplicationContext(), "다운로드가 완료되었습니다", Toast.LENGTH_LONG);
			}
		});
	}
	public void checkAndCreateDirectory(String dirName){
        File new_dir = new File( rootDir + dirName );
        if( !new_dir.exists() ){
            new_dir.mkdirs();
        }
    }
class DownloadFileAsync extends AsyncTask<String, String, String> {
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
//            mProgressDialog.show();
        }

        
        @Override
        protected String doInBackground(String... aurl) {

            try {
                //connecting to url
                URL u = new URL(aurl[0]);
                Log.d("URL", imageURL);
                URLConnection c =  u.openConnection();
//                c.setRequestMethod("GET");
//                c.setDoOutput(true);
                c.connect();
                
                //lenghtOfFile is used for calculating download progress
                
                int lenghtOfFile = c.getContentLength();
                //this is where the file will be seen after the download
                InputStream input = new BufferedInputStream(u.openStream(), 8192);
                OutputStream f = new FileOutputStream(new File(rootDir + "/indienavi/", imageName+".jpg"));
                //file input is from the url
//                InputStream in = c.getInputStream();
                
                //here's the download code
                byte[] buffer = new byte[1024];
                int len1 = 0;
                long total = 0;
                
                while ((len1 = input.read(buffer)) > 0) {
                    total += len1; //total = total + len1
                    publishProgress("" + (int)((total*100)/lenghtOfFile));
                    f.write(buffer, 0, len1);
                }
                f.flush();
                f.close();
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse("file://"+Environment.getExternalStorageDirectory()+"/indienavi")));
                
            } catch (Exception e) {
                Log.d("Image Download", e.getMessage());
            }
            
            return null;
        }
        
        protected void onProgressUpdate(String... progress) {
             Log.d("Image Download",progress[0]);
             mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            //dismiss the dialog after the file was downloaded
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            Toast.makeText(getApplicationContext(), "다운로드가 완료되었습니다", Toast.LENGTH_LONG).show();
            
//        	mProgressDialog.dismiss();
        }
    }
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
        	case DIALOG_DOWNLOAD_PROGRESS: //we set this to 0
        		mProgressDialog = new ProgressDialog(this);
        		mProgressDialog.setMessage("Downloading file...");
        		mProgressDialog.setIndeterminate(false);
        		mProgressDialog.setMax(100);
        		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        		mProgressDialog.setCancelable(true);
        		mProgressDialog.show();
        		return mProgressDialog;
        	default:
        		return null;
		}
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		FlurryAgent.onStartSession(this, Constant.FLURRY_API_KEY);
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		FlurryAgent.onEndSession(this);
	}

}
