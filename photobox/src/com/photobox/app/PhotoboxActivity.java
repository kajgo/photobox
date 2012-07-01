package com.photobox.app;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.photobox.R;

public class PhotoboxActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.intro);

        ((Button)findViewById(R.id.showDemoPicturesButton)).setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.photo);
                    ((PhotoView)findViewById(R.id.photoView)).loadDemoPhotos();
                }
            }
        );
        ((Button)findViewById(R.id.showSdcardPicturesButton)).setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.photo);
                    File photoDir = new File(Environment.getExternalStorageDirectory(), "photobox");
                    ((PhotoView)findViewById(R.id.photoView)).loadPhotosFromDir(photoDir);
                }
            }
        );
    }

}
