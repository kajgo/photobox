package com.photobox.app;

import java.io.File;

import com.photobox.files.ImportHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.*;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;

import com.photobox.R;

public class PicasaActivity extends Activity {

    private File currentPath;
    private String[] currentItems;

    private ListView list;
    private TextView text;
    private EditText usernameEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.picasa);

        usernameEditText = (EditText)findViewById(R.id.usernameEditText);

        Button loadAlbumsButton = (Button)findViewById(R.id.loadAlbumsButton);
        loadAlbumsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("", "du heter " + usernameEditText.getText());
            }
        });
    }

}
