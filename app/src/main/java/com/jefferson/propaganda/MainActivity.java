package com.jefferson.propaganda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    private TextView myadd;
    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myadd = (TextView)this.findViewById(R.id.ads);
        myadd.setSelected(true);

        int images[] = {R.drawable.ucam, R.drawable.blackf , R.drawable.casas_b};

        viewFlipper = findViewById(R.id.v_fliper);

        for(int image : images){
            flipperImages(image);
        }

    }

    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);

        //animacao
        viewFlipper.setInAnimation(this, R.anim.fade_out);
        viewFlipper.setOutAnimation(this, R.anim.fade_in);

    }
}
