package com.photobox.app;

import java.io.File;

import android.app.Activity;
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
                activity.setCurrentPath(new File(activity.currentPath, activity.currentItems[position]));
            }
        });

        Button selectFolderButton = (Button)findViewById(R.id.selectFolderButton);
        selectFolderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PhotoboxActivity.class);
                intent.putExtra("action", "dir");
                intent.putExtra("dir", activity.currentPath.getAbsolutePath());
                startActivity(intent);
            }
        });

        Button backFolderButton = (Button)findViewById(R.id.backFolderButton);
        backFolderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activity.setCurrentPath(activity.currentPath.getParentFile());
            }
        });

    }

}
