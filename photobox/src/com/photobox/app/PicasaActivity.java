package com.photobox.app;

import java.io.File;
import java.util.*;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.*;

import com.photobox.R;
import com.photobox.files.*;

public class PicasaActivity extends Activity {

    private ListView albumList;
    private EditText usernameEditText;
    private PicasaActivity thisActivity;
    private List<PicasaAlbum> albums;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        thisActivity = this;

        setContentView(R.layout.picasa);

        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        albumList = (ListView)findViewById(R.id.albumList);

        Button loadAlbumsButton = (Button)findViewById(R.id.loadAlbumsButton);
        loadAlbumsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                thisActivity.albums = new PicasaApi("" + usernameEditText.getText()).getAlbums();
                String[] items;
                if (thisActivity.albums != null) {
                    items = getAlbumTitlesArray(thisActivity.albums);
                } else {
                    items = new String[] { "No albums found" };
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        thisActivity,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        items);
                albumList.setAdapter(adapter);
            }
        });

        albumList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("PicasaActivity", "you clicked album:");
                Log.d("PicasaActivity", " id   = " + thisActivity.albums.get(position).getId());
                Log.d("PicasaActivity", " name = " + thisActivity.albums.get(position).getName());
            }
        });
    }

    private String[] getAlbumTitlesArray(List<PicasaAlbum> albumList) {
        List<String> albumStrings = new ArrayList<String>();
        for(PicasaAlbum album : albumList) {
            albumStrings.add(album.getName());
        }
        return albumStrings.toArray(new String[] {"dummy array to get correct type"});
    }

}
