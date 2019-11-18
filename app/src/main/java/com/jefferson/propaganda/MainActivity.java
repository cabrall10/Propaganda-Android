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

public class MainActivity extends AppCompatActivity {

    private TextView myadd;
    private ViewFlipper viewFlipper;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
			.build();
        ImageLoader.getInstance().init(config);

        //banner de texto
        myadd = (TextView)this.findViewById(R.id.ads);
        myadd.setSelected(true);

        configurarViewFlipper();

        AsyncViewFliperActivity viewFliperActivity = new AsyncViewFliperActivity() {
            @Override
            protected void onPostExecute(Object s) {
                super.onPostExecute(s);
                try {

                    JSONArray array = new JSONArray(s.toString());
                    for (int i = 0; i < array.length(); i++) {
                        if (array.getString(i) != null)
                            preencherViewFlipper(array.getString(i));
                    }
                } catch (Exception e) {
                    Log.d("error", e.getMessage());
                }
            }
        };
        viewFliperActivity.execute();
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
