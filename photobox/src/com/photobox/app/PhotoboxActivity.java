package com.photobox.app;

import com.photobox.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;

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
                }
            }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cleanOldViews(findViewById(R.id.photoView));
        System.gc();
    }

    private void cleanOldViews(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && (view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                cleanOldViews(((ViewGroup) view).getChildAt(i));
            }
        ((ViewGroup) view).removeAllViews();
        }
    }
}
