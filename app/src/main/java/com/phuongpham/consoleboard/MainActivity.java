package com.phuongpham.consoleboard;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ProgressDialog pDialog;
    VideoView streamView;
    //WebView streamView;
    //WebView webView;
    WebView play;
    MediaController mediaController;
    ImageView downloadedImage;
    ImageView zoomImage;
    Bitmap download_img;
    private static final String SEVRER_ADDRESS = "http://localhost/db_upload.php";


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    // JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton left = (ImageButton) findViewById(R.id.left);
        ImageButton right = (ImageButton) findViewById(R.id.right);
        ImageButton up = (ImageButton) findViewById(R.id.up);
        ImageButton down = (ImageButton) findViewById(R.id.down);

        ImageButton my_left = (ImageButton) findViewById(R.id.my_left);
        ImageButton my_right = (ImageButton) findViewById(R.id.my_right);
        ImageButton start = (ImageButton) findViewById(R.id.start);
        ImageButton back = (ImageButton) findViewById(R.id.back);

        ImageButton circle = (ImageButton) findViewById(R.id.circle);
        ImageButton triangle = (ImageButton) findViewById(R.id.triangle);
        ImageButton square = (ImageButton) findViewById(R.id.square);
        ImageButton cross = (ImageButton) findViewById(R.id.cross);


        streamView = (VideoView)findViewById(R.id.streamview);
        //streamView = (WebView) findViewById(R.id.streamview);
        downloadedImage = (ImageView) findViewById(R.id.downloadedImage);
        zoomImage = (ImageView) findViewById(R.id.zoomImage);

        left.setOnClickListener(this);
        right.setOnClickListener(this);
        up.setOnClickListener(this);
        down.setOnClickListener(this);

        circle.setOnClickListener(this);
        triangle.setOnClickListener(this);
        cross.setOnClickListener(this);
        square.setOnClickListener(this);

        my_left.setOnClickListener(this);
        my_right.setOnClickListener(this);
        start.setOnClickListener(this);
        back.setOnClickListener(this);

        //webView = (WebView) findViewById(R.id.streamview);
        //webView.setWebViewClient(new WebViewClient());

        play = (WebView) findViewById(R.id.play);
        play.setWebViewClient(new WebViewClient());
        play.setVisibility(View.INVISIBLE);

        downloadedImage.setOnClickListener(this);
        zoomImage.setOnClickListener(this);
        //streamView.setOnClickListener(this);
        streamView.setMediaController(new MediaController(this));

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left:
                new UploadMyCommand("left", "http://10.4.95.101/cgi-bin/motors_mini.cgi?s=turnleft").execute();
                break;
            case R.id.right:
                new UploadMyCommand("right", "http://10.4.95.101/cgi-bin/motors_mini.cgi?s=turnright").execute();
                break;
            case R.id.up:
                new UploadMyCommand("up", "http://10.4.95.101/cgi-bin/motors_mini.cgi?s=forward").execute();
                break;
            case R.id.down:
                new UploadMyCommand("down", "http://10.4.95.101/cgi-bin/motors_mini.cgi?s=backward").execute();
                break;
            case R.id.start:
                //StartVideo("http://10.4.95.101/cgi-bin/streamer.cgi");
                 StartVideo("http://10.4.95.101:8090");
                //webView.loadUrl("http://10.4.95.99/pi/stream.html");
                //new UploadMyCommand("stream","http://192.168.1.93/stream.html").execute();
                ///streamView.loadUrl("http://192.168.1.93/stream.html");
                //new UploadMyCommand("streaming","http://10.4.95.101/cgi-bin/streamer.cgi").execute();
                //StartVideo("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp");
                //StartVideo("http://vevoplaylist-live.hls.adaptive.level3.net/vevo/ch1/appleman.m3u8");
                break;
            case R.id.my_right:
                //new UploadMyCommand("open_streamer", "http://10.4.95.101/cgi-bin/player.cgi").execute();
                play.loadUrl("http://10.4.95.101/cgi-bin/http_player.cgi");
                break;
            case R.id.back:
                new DownloadImage("http://10.4.95.101/test.jpg").execute();
                break;
            case R.id.my_left:
                new UploadMyCommand("close_streamer", "http://10.4.95.101/cgi-bin/cam.cgi").execute();
                //new DownloadImage("http://10.4.95.101/cam.jpg").execute();
                //new DownloadImage("https://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png").execute();
                break;
            case R.id.cross:
                new UploadMyCommand("close_streamer", "http://10.4.95.101/cgi-bin/close_streamer.cgi").execute();
                break;
            case R.id.downloadedImage:
                zoomImage.setVisibility(View.VISIBLE);
                new DownloadZoominImage("http://10.4.95.101/test.jpg").execute();
                savePhoto(download_img);
                break;
            case R.id.zoomImage:
                zoomImage.setVisibility(View.INVISIBLE);
                if (download_img == null)
                    Toast.makeText(getApplicationContext(), "image downloaded is null", Toast.LENGTH_SHORT).show();
                else if (download_img != null)
                    Toast.makeText(getApplicationContext(), "image downloaded is not null", Toast.LENGTH_SHORT).show();
                break;
		/*
			case R.id.cross:
				new UploadMyCommand("cross").execute();
				break;
			case R.id.circle:
				new UploadMyCommand("circle").execute();
				break;
			case R.id.square:
				new UploadMyCommand("square").execute();
				break;
			case R.id.triangle:
				new UploadMyCommand("triangle").execute();
				break;
			case R.id.my_left:
				new UploadMyCommand("my_left").execute();
				break;
			case R.id.my_right:
				new UploadMyCommand("my_right").execute();
				break;
			case R.id.back:
				new UploadMyCommand("back").execute();
				break;
		*/
        }

    }

        private void StartVideo (String src){
            Uri UriSrc = Uri.parse(src);
            if(UriSrc == null){
                Toast.makeText(MainActivity.this,
                        "UriSrc == null", Toast.LENGTH_LONG).show();
            }else{
                streamView.setVideoURI(UriSrc);
                streamView.setMediaController(new MediaController(this));
                streamView.start();

                Toast.makeText(MainActivity.this,
                        "Connect: " + src,
                        Toast.LENGTH_LONG).show();
            }
        }


    /*
	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.phuongpham.consoleboard/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.phuongpham.consoleboard/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}
*/
    private class UploadMyCommand extends AsyncTask<Void, Void, Void> {

        String move;
        String url;
        //String url ="http://192.168.1.92/db_upload.php";
        //String url ="http://10.4.95.99/db_upload.php";
        //String url ="http://10.4.95.101/cgi-bin/motors_mini?s=forward";

        public UploadMyCommand(String move, String url) {
            this.move = move;
            this.url = url;
        }

        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("Description", move));

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);

            try {

                // post.setEntity(new UrlEncodedFormEntity(dataToSend));
                //httpclient.execute(post);

                //post.setEntity(new UrlEncodedFormEntity(dataToSend, HTTP.UTF_8));
                HttpResponse response = httpclient.execute(post);
                //finish();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "COMMAND UPLOADED ON SERVER", Toast.LENGTH_SHORT).show();
        }
    }


    // DOWNLOAD IMAGE
    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
        //String name;
        String url;

        public DownloadImage(String url) {
            //  this.name = name;
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            try {
                //URLConnection connection = new URL(url).openConnection();
                URL my_url = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) my_url.openConnection();
                // connection.setConnectTimeout(1000 * 30);
                //  connection.setReadTimeout(1000 * 30);
                InputStream is = connection.getInputStream();
                download_img = BitmapFactory.decodeStream(is);
                //Bitmap check = BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);
                return download_img;
                //return BitmapFactory.decodeStream(connection.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);


            if (bitmap == null) {
                Toast.makeText(getApplicationContext(), "bitmap is null", Toast.LENGTH_SHORT).show();
            }

            if (bitmap != null) {
                downloadedImage.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "image downloaded", Toast.LENGTH_SHORT).show();
            }

            // Toast.makeText(getApplicationContext(), "image downloaded", Toast.LENGTH_SHORT).show();
        }
    }


    private class DownloadZoominImage extends AsyncTask<Void, Void, Bitmap> {
        //String name;
        String url;

        public DownloadZoominImage(String url) {
            //  this.name = name;
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            try {
                //URLConnection connection = new URL(url).openConnection();
                URL my_url = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) my_url.openConnection();
                // connection.setConnectTimeout(1000 * 30);
                //  connection.setReadTimeout(1000 * 30);
                InputStream is = connection.getInputStream();
                Bitmap img = BitmapFactory.decodeStream(is);
                //Bitmap check = BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);
                return img;
                //return BitmapFactory.decodeStream(connection.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);


            if (bitmap == null) {
                Toast.makeText(getApplicationContext(), "bitmap is null", Toast.LENGTH_SHORT).show();
            }

            if (bitmap != null) {
                zoomImage.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "image downloaded", Toast.LENGTH_SHORT).show();
            }

            // Toast.makeText(getApplicationContext(), "image downloaded", Toast.LENGTH_SHORT).show();
        }
    }

    // save photo
    private void savePhoto(Bitmap bmp) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        FileOutputStream out = null;
        Calendar c = Calendar.getInstance();
        String date = fromInt(c.get(Calendar.MONTH))
                + fromInt(c.get(Calendar.DAY_OF_MONTH))
                + fromInt(c.get(Calendar.YEAR))
                + fromInt(c.get(Calendar.HOUR_OF_DAY))
                + fromInt(c.get(Calendar.MINUTE))
                + fromInt(c.get(Calendar.SECOND));
        File imageFileName = new File(path, date.toString() + ".jpg");
        try {
            out = new FileOutputStream(imageFileName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            scanPhoto(imageFileName.toString());
            out = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(MainActivity.this, "Saved to gallery", Toast.LENGTH_SHORT).show();
    }

    private String fromInt(int val) {
        return String.valueOf(val);
    }

    private void scanPhoto(String imageFileName) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imageFileName);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
/*
    //permission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
*/

}
