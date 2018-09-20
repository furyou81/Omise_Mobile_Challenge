package com.example.furyou.omisechallenge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.InputStream;
import java.net.URL;

/**
 * AsyncTask to get the logo image of the charity from the url
 **/

public class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {

    ImageView imageView;

    public DownloadImageTask(ImageView imageView){

        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String...urls){
        String urlOfImage = urls[0];
        Bitmap logo = null;
        // getting and decoding the image from the url
        try{
            InputStream is = new URL(urlOfImage).openStream();
            logo = BitmapFactory.decodeStream(is);
        }catch(Exception e){
            e.printStackTrace();
        }
        return logo;
    }

    protected void onPostExecute(Bitmap result){
        // setting the logo of the charity to the ImageView
        imageView.setImageBitmap(result);
    }
}
