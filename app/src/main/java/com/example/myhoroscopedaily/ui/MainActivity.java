package com.example.myhoroscopedaily.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myhoroscopedaily.R;
import com.example.myhoroscopedaily.adapter.MainAdapter;
import com.example.myhoroscopedaily.data.DetailActivity;
import com.example.myhoroscopedaily.data.RetrofitClient;
import com.example.myhoroscopedaily.data.Service;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.net.InetAddress;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainAdapter.SunsignClickListener {

    private RecyclerView mRecyclerView;
    private MainAdapter adapter;

    private TextView mErrorMessage;
    private ProgressBar mLoading;

    public static final String TAG = MainActivity.class.getSimpleName();

    private List mSunsignList;

    private AdView mAdView;

    @SuppressLint("ResourceType")
    @BindView(R.layout.sunsign_list)
    Layout LayoutSunsignList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(false);

        adapter = new MainAdapter(this);
        mRecyclerView.setAdapter(adapter);

        mErrorMessage = findViewById(R.id.tv_error_message);
        mLoading = findViewById(R.id.pb_loading);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        if (isInternetAvailable() != false){
            mErrorMessage.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            loadSunsignList();
        }

    }

    private void loadSunsignList(){
        new FetchSunsignsTask().execute();
        showSunsignListView();
    }

    private void showSunsignListView(){
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }



    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onSunsignClick(String sunsign) {
        Intent intent = new Intent(this, DetailActivity.class);
        Log.e("Selected Sunsign", sunsign);
        intent.putExtra(DetailActivity.INTENT_EXTRA, sunsign);
        this.startActivity(intent);
    }

    private class FetchSunsignsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Service service = RetrofitClient.getClient().create(Service.class);
            Call<List> call = service.getSunsigns();
            call.enqueue(new Callback<List>() {
                @Override
                public void onResponse(Call<List> call, Response<List> response) {
                    if (response.isSuccessful()) {
                        mSunsignList = response.body();
                        adapter.setData(mSunsignList);
                        adapter.notifyDataSetChanged();
                    } else {
                        mErrorMessage.setVisibility(View.INVISIBLE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });

            return null;
        }
    }
}
