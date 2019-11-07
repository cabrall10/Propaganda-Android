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

        viewFlipper = findViewById(R.id.v_fliper);

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this, R.anim.fade_out);
        viewFlipper.setOutAnimation(this, R.anim.fade_in);

        task = new  AsyncTask<Object, Object, Object>() {

            @Override
            protected Object doInBackground(Object... strings) {
                return HttpRequest.get("https://gist.githubusercontent.com/cabrall10/0db36b0d5cb0134d0e703a053a092a0e/raw/ac2a4b7877227af4164c8f26873ba630d41c8335/propagandas.json").body();
            }

            @Override
            protected void onPostExecute(Object s) {
                super.onPostExecute(s);
                try {

                    JSONArray array = new JSONArray(s.toString());
                    for (int i = 0; i < array.length(); i++) {
                        if (array.getString(i) != null) flipperImages(array.getString(i));
                    }
                } catch (Exception e) {
                    Log.d("error", e.getMessage());
                }
            }
        };
        task.execute();

//        int images[] = {R.drawable.ucam, R.drawable.blackf , R.drawable.casas_b};
//        String images[] = {"https://3.bp.blogspot.com/-3iULfH-TRUI/WG5iOOxattI/AAAAAAAADtw/8zGuk9y3FiQqmhgeKF9AO-xA5z5gmhrkQCLcB/s1600/casas%2Bbahia.png", "https://www.encontracidadedutra.com.br/wp-content/uploads/2013/02/casas-bahia-resende-1359904825.jpg"};

//
//        for(String image : images){
//            flipperImages(image);
//        }

    }

    public void flipperImages(String image){
        final ImageView imageView = new ImageView(this);
//        imageView.setBackgroundResource(image);
//        ImageLoader.getInstance().displayImage(image, imageView);
        ImageLoader.getInstance().loadImage(image, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                BitmapDrawable ob = new BitmapDrawable(getResources(), loadedImage);
                imageView.setBackground(ob);
                viewFlipper.addView(imageView);
            }
        });
    }

}
