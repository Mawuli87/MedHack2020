package com.messieyawo.OakHacks2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.messieyawo.OakHacks2020.Adapters.CommentAdapterLive;
import com.messieyawo.OakHacks2020.models_live.YoutubeCommentModelLive;
import com.messieyawo.OakHacks2020.models_live.YoutubeDataModelLive;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class DetailsActivityLive extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static String GOOGLE_YOUTUBE_API = "AIzaSyBH8szUCt1ctKQabVeQuvWgowaKxHVjn8E";//here you should use your api key for testing purpose you can use this api also
    private YoutubeDataModelLive youtubeDataModelLive = null;
    TextView textViewName;
    TextView textViewDes;
    // ImageView imageViewIcon;
    public static final String VIDEO_ID = "c2UNv38V6y4";
    private YouTubePlayerView mYoutubePlayerView = null;
    private YouTubePlayer mYoutubePlayer = null;
    private ArrayList<YoutubeCommentModelLive> mListData = new ArrayList<>();
    private CommentAdapterLive mAdapter = null;
    private RecyclerView mList_videos = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_live);
        youtubeDataModelLive = getIntent().getParcelableExtra(YoutubeDataModelLive.class.toString());
        Log.e("", youtubeDataModelLive.getDescription());

        mYoutubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        mYoutubePlayerView.initialize(GOOGLE_YOUTUBE_API, this);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewDes = (TextView) findViewById(R.id.textViewDes);

        textViewName.setText(youtubeDataModelLive.getTitle());
        textViewDes.setText(youtubeDataModelLive.getDescription());

        mList_videos = (RecyclerView) findViewById(R.id.mList_videos);
        new RequestYoutubeCommentAPI().execute();


    }

    public void back_btn_pressed(View view) {
        finish();
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        if (!wasRestored) {
            youTubePlayer.cueVideo(youtubeDataModelLive.getVideo_id());
        }
        mYoutubePlayer = youTubePlayer;
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    public void share_btn_pressed(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String link = ("https://www.youtube.com/watch?v=" + youtubeDataModelLive.getVideo_id());
        // this is the text that will be shared
        sendIntent.putExtra(Intent.EXTRA_TEXT, link);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, youtubeDataModelLive.getTitle()
                + "Share");

        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "share"));
    }


    private ProgressDialog pDialog;


    private class RequestDownloadVideoStream extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailsActivityLive.this);
            pDialog.setMessage("Downloading file. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            InputStream is = null;
            URL u = null;
            int len1 = 0;
            int temp_progress = 0;
            int progress = 0;
            try {
                u = new URL(params[0]);
                is = u.openStream();
                URLConnection huc = (URLConnection) u.openConnection();
                huc.connect();
                int size = huc.getContentLength();

                if (huc != null) {
                    String file_name = params[1] + ".mp4";
                    String storagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/YoutubeVideos";
                    File f = new File(storagePath);
                    if (!f.exists()) {
                        f.mkdir();
                    }

                    FileOutputStream fos = new FileOutputStream(f+"/"+file_name);
                    byte[] buffer = new byte[1024];
                    int total = 0;
                    if (is != null) {
                        while ((len1 = is.read(buffer)) != -1) {
                            total += len1;
                            // publishing the progress....
                            // After this onProgressUpdate will be called
                            progress = (int) ((total * 100) / size);
                            if(progress >= 0) {
                                temp_progress = progress;
                                publishProgress("" + progress);
                            }else
                                publishProgress("" + temp_progress+1);

                            fos.write(buffer, 0, len1);
                        }
                    }

                    if (fos != null) {
                        publishProgress("" + 100);
                        fos.close();
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            pDialog.setProgress(Integer.parseInt(values[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }


    private class RequestYoutubeCommentAPI extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String VIDEO_COMMENT_URL = "https://www.googleapis.com/youtube/v3/commentThreads?part=snippet&maxResults=100&videoId=" + youtubeDataModelLive.getVideo_id() + "&key=" + GOOGLE_YOUTUBE_API;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(VIDEO_COMMENT_URL);
            Log.e("url: ", VIDEO_COMMENT_URL);
            try {
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity httpEntity = response.getEntity();
                String json = EntityUtils.toString(httpEntity);
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("response", jsonObject.toString());
                    mListData = parseJson(jsonObject);
                    initVideoList(mListData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void initVideoList(ArrayList<YoutubeCommentModelLive> mListData) {
        mList_videos.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommentAdapterLive(this, mListData);
        mList_videos.setAdapter(mAdapter);
    }

    public ArrayList<YoutubeCommentModelLive> parseJson(JSONObject jsonObject) {
        ArrayList<YoutubeCommentModelLive> mList = new ArrayList<>();

        if (jsonObject.has("items")) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);

                    YoutubeCommentModelLive youtubeObject = new YoutubeCommentModelLive();
                    JSONObject jsonTopLevelComment = json.getJSONObject("snippet").getJSONObject("topLevelComment");
                    JSONObject jsonSnippet = jsonTopLevelComment.getJSONObject("snippet");

                    String title = jsonSnippet.getString("authorDisplayName");
                    String thumbnail = jsonSnippet.getString("authorProfileImageUrl");
                    String comment = jsonSnippet.getString("textDisplay");

                    youtubeObject.setTitle(title);
                    youtubeObject.setComment(comment);
                    youtubeObject.setThumbnail(thumbnail);
                    mList.add(youtubeObject);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return mList;

    }


}
