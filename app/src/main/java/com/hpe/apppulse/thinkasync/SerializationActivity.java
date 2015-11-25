package com.hpe.apppulse.thinkasync;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hpe.apppulse.thinkasync.flatbuffers.Repo;
import com.hpe.apppulse.thinkasync.flatbuffers.ReposList;
import com.hpe.apppulse.thinkasync.json.RepoJson;
import com.hpe.apppulse.thinkasync.json.ReposListJson;

import java.io.IOException;
import java.nio.ByteBuffer;

public class SerializationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serialization);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
    }

    public void onJsonClicked(View view) {
        JsonReaderAsyncTask asyncTask = new JsonReaderAsyncTask(this);
        asyncTask.execute();
    }

    public void onFlatBufferClicked(View view) {
        FlatBuffersAsyncTask asyncTask = new FlatBuffersAsyncTask(this);
        asyncTask.execute();
    }

    public class JsonReaderAsyncTask extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private RawDataReader rawDataReader;
        private ReposListJson reposListJson;
        private long readTimeinMillis = -1;

        public JsonReaderAsyncTask(Context context) {
            mContext = context;
            rawDataReader = new RawDataReader(mContext);
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {
                String repoJson = rawDataReader.loadJsonStringBlocking(R.raw.repos_json); // read json repository
                parseReposListJson(repoJson);
            } catch (IOException e) {
                // ignore
            }
            return null;
        }

        private void parseReposListJson(String reposStr) {
            long startTime = System.currentTimeMillis();

            reposListJson = new Gson().fromJson(reposStr, ReposListJson.class);
            for (int i = 0; i < reposListJson.repos.size(); i++) {
                RepoJson repo = reposListJson.repos.get(i);
                Log.d("FlatBuffers", "Repo #" + i + ", id: " + repo.id);
            }

            readTimeinMillis = System.currentTimeMillis() - startTime;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(mContext,String.format("Read %s elements from Json. Load time: %s", reposListJson.repos.size(), readTimeinMillis),Toast.LENGTH_SHORT).show();
        }
    }

    public class FlatBuffersAsyncTask extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private RawDataReader rawDataReader;
        private ReposList reposListFlat;
        private long readTimeinMillis = -1;

        public FlatBuffersAsyncTask(Context context) {
            mContext = context;
            rawDataReader = new RawDataReader(mContext);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                byte[] reposFlatBytes = rawDataReader.loadBytesBlocking(R.raw.repos_flat); // read flat buffers repository
                loadFlatBuffer(reposFlatBytes);
            } catch (IOException e) {
                // ignore
            }
            return null;
        }

        private void loadFlatBuffer(byte[] bytes) {
            long startTime = System.currentTimeMillis();
            ByteBuffer bb = ByteBuffer.wrap(bytes);
            reposListFlat = ReposList.getRootAsReposList(bb);
            for (int i = 0; i < reposListFlat.reposLength(); i++) {
                Repo repos = reposListFlat.repos(i);
                Log.d("FlatBuffers", "Repo #" + i + ", id: " + repos.id());
            }
            readTimeinMillis = System.currentTimeMillis() - startTime;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(mContext,String.format("Read %s elements from Flat Buffers data. Load time: %s",reposListFlat.reposLength(), readTimeinMillis),Toast.LENGTH_SHORT).show();
        }
    }
}
