package com.photobox.app;

import java.io.File;

import com.photobox.files.ImportHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.photobox.R;

public class ListActivity extends Activity {

    private File currentPath;
    private String[] currentItems;

    private ListView list;
    private TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.files);

        list = (ListView)findViewById(R.id.fileList);
        text = (TextView)findViewById(R.id.pathLabel);

        setCurrentPath(Environment.getExternalStorageDirectory());

        final ListActivity activity = this;

        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File newPath = new File(activity.currentPath, activity.currentItems[position]);
                if (newPath.isDirectory()) {
                    activity.setCurrentPath(newPath);
                }
            }
        });

        Button selectFolderButton = (Button)findViewById(R.id.selectFolderButton);
        selectFolderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ImportHandler.hasPhotos(activity.currentPath)) {
                    Intent intent = new Intent(getBaseContext(), PhotoboxActivity.class);
                    intent.putExtra("action", "dir");
                    intent.putExtra("dir", activity.currentPath.getAbsolutePath());
                    startActivity(intent);
                } else {
                    activity.showNoPhotosPopup();
                }
            }
        });

        Button backFolderButton = (Button)findViewById(R.id.backFolderButton);
        backFolderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File parentPath = activity.currentPath.getParentFile();
                if (parentPath != null) {
                    activity.setCurrentPath(parentPath);
                }
            }
        });
    }

    private void showNoPhotosPopup() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("No photos found");
        alertDialog.setMessage("No .png, .jpg, or .jpeg photos found in this directory.");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    private void setCurrentPath(File path) {
        currentPath = path;

        currentItems = currentPath.list();

        text.setText(currentPath.getPath());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                currentItems);

        list.setAdapter(adapter);
    }
}
