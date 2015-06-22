package com.asynctaskproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.apache.http.HttpConnection;

import android.os.AsyncTask;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContntView(R.layout.image_layout);

        ImageDownloader imageDownloader = new ImageDownloader();
        imageDownloader.execute("http://wanderingoak.net/bridge.png");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private class ImageDownloader extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected void onPreExecute(){

        }
        @Override
        protected Bitmap doInBackground(String... params){
            try{
                URL url = new URL(params[0]);
                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

                if (httpCon.getResponseCode() != 200)
                    throw new Exception("Failed to connect");

                InputStream is = httpCon.getInputStream();
                return BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                Log.e("Image", "Failed to load image", e);
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... params){
        // Update
        }
        @Override
        protected void onPostExecute(Bitmap img) {
            ImageView iv = (ImageView) getView().findViewById(R.id.image_layout);
            if (iv != null && img != null) {
                iv.setImageBitmap(img);
            }

        }
        @Override
        protected void onCancelled() {
            //
        }


    }

}
