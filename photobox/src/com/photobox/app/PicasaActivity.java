package com.photobox.app;

import java.io.*;
import java.net.*;
import java.util.*;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.*;
import android.content.*;
import android.os.*;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
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
                List<String> things = new PicasaApi("" + usernameEditText.getText()).getPhotoUrlsForAlbum(thisActivity.albums.get(position).getId());
                File destinationDir = new ContextWrapper(getBaseContext()).getExternalFilesDir(null);
                new FileDownloaderAsyncTask(destinationDir, things).execute();
            }
        });
    }

    class FileDownloaderAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog;
        private File destinationDir;
        private List<String> things;

        public FileDownloaderAsyncTask(File destinationDir, List<String> things) {
            this.destinationDir = destinationDir;
            this.things = things;
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(PicasaActivity.this, "", "Loading...");
        }

        protected Void doInBackground(Void... voids) {
            new FileDownloader(destinationDir, things).start();
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();

            Intent intent = new Intent(getBaseContext(), PhotoboxActivity.class);
            intent.putExtra("action", "dir");
            intent.putExtra("dir", destinationDir.getAbsolutePath());
            startActivity(intent);
        }
    }

    class FileDownloader {
        private File folder;
        private List<String> urls;

        public FileDownloader(File folder, List<String> urls) {
            this.folder = folder;
            this.urls = urls;
        }

        public void start() {
            wipeFolder();
            downloadEachPhoto();
        }

        private void wipeFolder() {
            folder.mkdirs();
            for(File file : folder.listFiles()) {
                file.delete();
            }
        }

        private void downloadEachPhoto() {
            int i = 0;
            for(String url : urls) {
                String extension = url.substring(url.lastIndexOf("."));
                String filename = "photobox" + i + extension;
                downloadFile(new File(folder, filename), url);
                i++;
            }
        }

        private void downloadFile(File destination, String url) {
            try {
                HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("GET");
                InputStream is = connection.getInputStream();
                FileOutputStream fos = new FileOutputStream(destination);
                int c;
                while ((c = is.read()) != -1) {
                    fos.write(c);
                }
                fos.close();
                is.close();
            } catch (Exception e) {
                Log.d("PicasaApi", "failed to downloadFile: " + e.toString(), e);
            }
        }

    }

    private String[] getAlbumTitlesArray(List<PicasaAlbum> albumList) {
        List<String> albumStrings = new ArrayList<String>();
        for(PicasaAlbum album : albumList) {
            albumStrings.add(album.getName());
        }
        return albumStrings.toArray(new String[] {"dummy array to get correct type"});
    }

}
