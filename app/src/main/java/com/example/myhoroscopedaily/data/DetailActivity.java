package com.example.myhoroscopedaily.data;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myhoroscopedaily.R;
import com.example.myhoroscopedaily.models.AstrologyModel;
import com.example.myhoroscopedaily.ui.Detail;
import com.example.myhoroscopedaily.ui.DetailIntentService;
//import com.example.myhoroscopedaily.widget.WidgetProvider;
import com.example.myhoroscopedaily.widget.WidgetProvider;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;

public class DetailActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String INTENT_EXTRA = "sunsign";
    public static final String WIDGET_PREF = "widget_prefs";
    public static final String ID_PREF = "id";
    public static final String NAME_PREF = "name";

    public String DAY = "today";
    public String sunsign;

    private ImageView mPoster = findViewById(R.id.iv_sunsign_detail_image);
    private TextView mPrediction = findViewById(R.id.tv_sunsign_prediction);



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        final Button mYesterday = findViewById(R.id.btn_previous);
        final Button mTomorrow = findViewById(R.id.btn_next);
        final TextView mDay = findViewById(R.id.tv_day);




        updatePrediction(mPrediction);

        mYesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DAY.compareTo("today") == 0) {
                    mDay.setText(R.string.yesterdayPrediction);
                    mYesterday.setVisibility(View.INVISIBLE);
                    DAY = "yesterday";
                    updatePrediction(mPrediction);
                } else {
                    mDay.setText(R.string.todayPrediction);
                    mTomorrow.setVisibility(View.VISIBLE);
                    DAY = "today";
                    updatePrediction(mPrediction);
                }
            }
        });

        mTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DAY.compareTo("today") == 0) {
                    mDay.setText(R.string.tomorrowPrediction);
                    mTomorrow.setVisibility(View.INVISIBLE);
                    DAY = "tomorrow";
                } else {
                    mDay.setText(R.string.todayPrediction);
                    mYesterday.setVisibility(View.VISIBLE);
                    DAY = "today";
                }
            }
        });

        sunsign = getIntent().getStringExtra(INTENT_EXTRA);

        setTitle(sunsign);
        Picasso.get()
                .load(R.drawable.aries)
                .into(mPoster);

        mPrediction.setText("This is prediction");


    }

    public void updatePrediction (View v){
        Intent intent = new Intent(this, DetailIntentService.class);
        intent.setAction(Detail.ACTION_MODIFY_DETAIL);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_widget_menu, menu);
        return true;
    }

//    private void updatePrediction() {
//        String prediction = AstrologyModel.getHoroscope();
//        mPrediction.setText(prediction);
//    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePrediction(mPrediction);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_widget:
                addToPrefsForWidget();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToPrefsForWidget() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(NAME_PREF, sunsign);
        editor.apply();


        WidgetProvider.updateWidget(this);
    }

}
