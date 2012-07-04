package com.photobox.app;

import java.io.File;

import android.util.Log;
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
        setContentView(R.layout.photo);

        String action = getIntent().getStringExtra("action");
        if (action.equals("demo")) {
            ((PhotoView)findViewById(R.id.photoView)).loadDemoPhotos();
        } else if (action.equals("dir")) {
            String dir = getIntent().getStringExtra("dir");
            ((PhotoView)findViewById(R.id.photoView)).loadPhotosFromDir(new File(dir));
        }
    }

}
