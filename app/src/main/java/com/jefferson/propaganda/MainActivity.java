package com.jefferson.propaganda;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView myadd;
    private ViewFlipper viewFlipper;
    static AsyncTask task;
    private String url = "https://gist.githubusercontent.com/cabrall10/0db36b0d5cb0134d0e703a053a092a0e/raw/877c1df95fc0d64830328a620e9d835c2a917f4c/propagandas.json";

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
			.build();
        ImageLoader.getInstance().init(config);

        myadd = (TextView)this.findViewById(R.id.ads);
        myadd.setSelected(true);

        configurarViewFlipper();

        task = new  AsyncTask<Object, Object, Object>() {

            @Override
            protected Object doInBackground(Object... strings) {
                return HttpRequest.get(url).body();
            }

            @Override
            protected void onPostExecute(Object s) {
                super.onPostExecute(s);
                try {

                    JSONArray array = new JSONArray(s.toString());
                    for (int i = 0; i < array.length(); i++) {
                        if (array.getString(i) != null) preencherViewFlipper(array.getString(i));
                    }
                } catch (Exception e) {
                    Log.d("error", e.getMessage());
                }
            }
        };
        task.execute();

    }

    public void preencherViewFlipper(String image){
        final ImageView imageView = new ImageView(this);

        ImageLoader.getInstance().loadImage(image, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                BitmapDrawable ob = new BitmapDrawable(getResources(), loadedImage);
                imageView.setBackground(ob);
                viewFlipper.addView(imageView);
            }
        });
    }

    public void configurarViewFlipper(){
        viewFlipper = findViewById(R.id.v_fliper);

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this, R.anim.fade_out);
        viewFlipper.setOutAnimation(this, R.anim.fade_in);
    }

}
