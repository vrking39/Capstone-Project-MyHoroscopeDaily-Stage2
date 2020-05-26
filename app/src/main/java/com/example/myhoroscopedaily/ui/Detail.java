package com.example.myhoroscopedaily.ui;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.myhoroscopedaily.data.RetrofitClient;
import com.example.myhoroscopedaily.data.Service;
import com.example.myhoroscopedaily.models.AstrologyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail {

    public static final String ACTION_MODIFY_DETAIL = "modify_detail";
    public static void executeTask(Context context, String action) {
// COMPLETED (8) If the action equals ACTION_INCREMENT_WATER_COUNT, call this class's incrementWaterCount
        if (ACTION_MODIFY_DETAIL.equals(action)) {
            modifyDetailContext(context);
        }
    }

    private static void modifyDetailContext(Context context) {
//      COMPLETED (5) From incrementWaterCount, call the PreferenceUtility method that will ultimately update the water count
        Service service = RetrofitClient.getClient().create(Service.class);
        Call<AstrologyModel> call = service.getAstrology("https://sandipbgt.com/theastrologer/api/horoscope/aquarius/today/");
        call.enqueue(new Callback<AstrologyModel>() {
            @Override
            public void onResponse(Call<AstrologyModel> call, Response<AstrologyModel> response) {
                if (response.isSuccessful()) {
                    AstrologyModel as = response.body();
                    AstrologyModel.setHoroscope(context, as.getHoroscope());
                }
            }

            @Override
            public void onFailure(Call<AstrologyModel> call, Throwable t) {
                Log.e("YO", t.getMessage());
            }
        });
    }
}
