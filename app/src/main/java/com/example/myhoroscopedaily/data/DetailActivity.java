package com.example.myhoroscopedaily.data;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.util.Log;
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
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA = "sunsign";
    public static final String WIDGET_PREF = "widget_prefs";
    public static final String ID_PREF = "id";
    public static final String NAME_PREF = "name";
    public static final String PREDICTION = "prediction";

    public String DAY = "today";
    public String sunsign;
    public String passPrediction;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        final Button mYesterday = findViewById(R.id.btn_previous);
        final Button mTomorrow = findViewById(R.id.btn_next);
        final TextView mDay = findViewById(R.id.tv_day);

        ImageView mPoster = findViewById(R.id.iv_sunsign_detail_image);

        TextView mPrediction = findViewById(R.id.tv_sunsign_prediction);


        mYesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DAY.compareTo("today") == 0) {
                    mDay.setText(R.string.yesterdayPrediction);
                    mYesterday.setVisibility(View.INVISIBLE);
                    mTomorrow.setText(R.string.buttonToday);
                    DAY = "yesterday";
                    displayPrediction(mPrediction);
                } else {
                    mDay.setText(R.string.todayPrediction);
                    mTomorrow.setVisibility(View.VISIBLE);
                    mYesterday.setText(R.string.buttonYesterday);
                    DAY = "today";
                    displayPrediction(mPrediction);

                }
            }
        });

        mTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DAY.compareTo("today") == 0) {
                    mDay.setText(R.string.tomorrowPrediction);
                    mTomorrow.setVisibility(View.INVISIBLE);
                    mYesterday.setText(R.string.buttonToday);
                    DAY = "tomorrow";
                    displayPrediction(mPrediction);
                } else {
                    mDay.setText(R.string.todayPrediction);
                    mYesterday.setVisibility(View.VISIBLE);
                    mTomorrow.setText(R.string.buttonTomorrow);
                    DAY = "today";
                    displayPrediction(mPrediction);
                }
            }
        });

        sunsign = getIntent().getStringExtra(INTENT_EXTRA);

        setTitle(sunsign);
        String image_url = "";
        switch (sunsign) {
            case "taurus":
                image_url = "Taurus";
                break;
            default: image_url = sunsign;
        }

        Picasso.get()
                .load("https://i2.wp.com/www.pictureboxblue.com/pbb-cont/pbb-up/2019/11/"+image_url+"-s.jpg?w=800&ssl=1")
                .fit()
                .into(mPoster);
        displayPrediction(mPrediction);
    }
    public void displayPrediction(TextView mPrediction) {
        final AstrologyModel[] astrology = new AstrologyModel[1];
        final String[] prediction = {""};
        Service service = RetrofitClient.getClient().create(Service.class);
        Call<AstrologyModel> call = service.getAstrology("http://sandipbgt.com/theastrologer/api/horoscope/"+ sunsign +"/"+ DAY +"/");
        Log.e("Prediction", call.toString());
        call.enqueue(new Callback<AstrologyModel>() {
            @Override
            public void onResponse(Call<AstrologyModel> call, Response<AstrologyModel> response) {
                if (response.isSuccessful()) {
                    astrology[0] = response.body();
                    prediction[0] = astrology[0].getHoroscope();
                    Log.e("Prediction", prediction[0]);
                    passPrediction = prediction[0];
                    mPrediction.setText(prediction[0]);
                }
            }

            @Override
            public void onFailure(Call<AstrologyModel> call, Throwable t) {
                Log.e("TAG", t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_widget_menu, menu);
        return true;
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
        editor.putString(PREDICTION, passPrediction);
        editor.apply();


        WidgetProvider.updateWidget(this);
    }

}
