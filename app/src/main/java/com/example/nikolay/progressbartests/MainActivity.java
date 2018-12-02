package com.example.nikolay.progressbartests;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String CURRENT_URL = "http://gallery.dev.webant.ru/api/photos?page=";

    private ArrayList<Picture> mPictures = new ArrayList<>();

    private int mPageCounter = 1;
    private int mScrollOutItems = 0;

    private RecyclerView mRecyclerView;

    private ProgressBar mProgressBar;

    private GridLayoutManager mManager;
    private Adapter mAdapter;

    private int mCurrentItems, mTotalItems, mScrollOutItem;
    private Boolean mIsScrolling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button button = (Button) findViewById(R.id.button);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mManager = new GridLayoutManager(this, 2);

        addPictures();

        initRecyclerView();

        for (int i = 0; i < mPictures.size(); i++) {
            Log.d(TAG, "onCreate: " +
                            "\nURL: " + mPictures.get(i).getUrl() +
                            "\nName: " + mPictures.get(i).getName() +
                            "\nDescription: " + mPictures.get(i).getDescription());
        }

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mIsScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCurrentItems = mManager.getChildCount();
                mTotalItems = mManager.getItemCount();
                mScrollOutItem = mManager.findFirstVisibleItemPosition();
                Log.d(TAG, "onScrolled: scroll scrollOutItem = "+ mScrollOutItem);
                Log.d(TAG, "onScrolled: scroll currentItems = "+ mCurrentItems);
                Log.d(TAG, "onScrolled: scroll totalItems = "+ mTotalItems);

                if (mIsScrolling && (mCurrentItems + mScrollOutItem == mTotalItems)) {
                    mIsScrolling = false;
                    fetchData();
                }
            }
        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPageCounter++;
//                new MyTask().execute();
//
//                Log.d(TAG, "onClick: mPictures's size: " + mPictures.size());
//
//                for (int i = mScrollOutItems; i < mPictures.size(); i++) {
//                    Log.d(TAG, "onPostExecute: " + mScrollOutItems +":" +
//                            "\nURL: " + mPictures.get(i).getUrl() +
//                            "\nName: " + mPictures.get(i).getName() +
//                            "\nDescription: " + mPictures.get(i).getDescription());
//                    mScrollOutItems++;
//                }
//                initRecyclerView();
//            }
//        });

        Log.d(TAG, "onCreate: started.");
    }


    private void fetchData() {
        mProgressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 8; i++) {
                    mPictures.add(new Picture (
                            mPictures.get(i).getName(),
                            mPictures.get(i).getDescription(),
                            mPictures.get(i).getUrl()));
                    mAdapter.notifyDataSetChanged();
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        }, 2000);

    }

    private void addPictures() {
        mPictures.add(new Picture(
                "brrr",
                "brrr",
                "http://gallery.dev.webant.ru/media/5baca83c2674e262290860.jpeg"
        ));

        mPictures.add(new Picture(
                "brrr",
                "brrr",
                "http://gallery.dev.webant.ru/media/5baca83c2674e262290860.jpeg"
        ));

        mPictures.add(new Picture(
                "brrr",
                "brrr",
                "http://gallery.dev.webant.ru/media/5baca83c2674e262290860.jpeg"
        ));

        mPictures.add(new Picture(
                "brrr",
                "brrr",
                "http://gallery.dev.webant.ru/media/5baca83c2674e262290860.jpeg"
        ));

        mPictures.add(new Picture(
                "brrr",
                "brrr",
                "http://gallery.dev.webant.ru/media/5baca83c2674e262290860.jpeg"
        ));

        mPictures.add(new Picture(
                "brrr",
                "brrr",
                "http://gallery.dev.webant.ru/media/5baca83c2674e262290860.jpeg"
        ));

        mPictures.add(new Picture(
                "brrr",
                "brrr",
                "http://gallery.dev.webant.ru/media/5baca83c2674e262290860.jpeg"
        ));

        mPictures.add(new Picture(
                "brrr",
                "brrr",
                "http://gallery.dev.webant.ru/media/5baca83c2674e262290860.jpeg"
        ));

        mPictures.add(new Picture(
                "brrr",
                "brrr",
                "http://gallery.dev.webant.ru/media/5baca83c2674e262290860.jpeg"
        ));

        mPictures.add(new Picture(
                "brrr",
                "brrr",
                "http://gallery.dev.webant.ru/media/5baca83c2674e262290860.jpeg"
        ));

    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recycler view");

        mAdapter = new Adapter(this, mPictures);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mManager);
    }

    private class MyTask extends AsyncTask<Void, Void, String> {

        String resultJSON = "";

        @Override
        protected String doInBackground(Void... voids) {
            return resultJSON = new Connection().getJSON(
                    "http://gallery.dev.webant.ru/api/photos?page=",
                    mPageCounter);
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            new Connection().parseItems(mPictures, resultJSON);
            Log.d(TAG, "onPostExecute: " + resultJSON);
        }
    }

}
