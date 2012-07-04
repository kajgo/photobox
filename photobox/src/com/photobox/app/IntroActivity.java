package com.photobox.app;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.photobox.R;

public class IntroActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.intro);

        findViewById(R.id.showDemoPicturesButton).setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), PhotoboxActivity.class);
                    intent.putExtra("action", "demo");
                    startActivity(intent);
                }
            }
        );
        findViewById(R.id.showSdcardPicturesButton).setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    File photoDir = new File(Environment.getExternalStorageDirectory(), "photobox");
                    Intent intent = new Intent(getBaseContext(), ListActivity.class);
                    intent.putExtra("action", "dir");
                    intent.putExtra("dir", photoDir.getAbsolutePath());
                    startActivity(intent);
                }
            }
        );
    }

}
