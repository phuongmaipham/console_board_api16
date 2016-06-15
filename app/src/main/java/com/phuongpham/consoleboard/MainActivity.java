package com.phuongpham.consoleboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ProgressDialog pDialog;
    private static final String SEVRER_ADDRESS = "http://localhost/db_upload.php";
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


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left:
                new UploadMyCommand("left").execute();
                break;
            case R.id.right:
                new UploadMyCommand("right").execute();
                break;
            case R.id.up:
                new UploadMyCommand("up").execute();
                break;
            case R.id.down:
                new UploadMyCommand("down").execute();
                break;
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
            case R.id.start:
                new UploadMyCommand("start").execute();
                break;
            case R.id.back:
                new UploadMyCommand("back").execute();
                break;
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
        //String url ="http://192.168.1.92/db_upload.php";
        String url = "http://10.4.95.94/cgi-bin/testing.cgi?s=shutdown";

        public UploadMyCommand (String move){
            this.move = move;
        }

        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("Description",move));

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);

            try {

                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                httpclient.execute(post);

                //post.setEntity(new UrlEncodedFormEntity(dataToSend, HTTP.UTF_8));
                //HttpResponse response = httpclient.execute(post);
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

}
