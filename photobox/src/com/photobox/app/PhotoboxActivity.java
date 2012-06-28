package com.photobox.app;

import com.photobox.R;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PhotoboxActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.intro);

        ((Button)findViewById(R.id.showPicturesButton)).setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.photo);
                    File[] photos = new File(Environment.getExternalStorageDirectory(), "photobox").listFiles();
                    ((PhotoView)findViewById(R.id.photoView)).loadPhotos(photos);
                }
            }
        );
    }

}
