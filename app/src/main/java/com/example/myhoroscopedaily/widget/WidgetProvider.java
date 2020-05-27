package com.example.myhoroscopedaily.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.myhoroscopedaily.R;
import com.example.myhoroscopedaily.data.DetailActivity;
import com.example.myhoroscopedaily.ui.Detail;
import com.example.myhoroscopedaily.ui.MainActivity;


/**
 * Implementation of App Widget functionality.
 */
public class WidgetProvider extends AppWidgetProvider {

    static String prediction;
    private static String text;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DetailActivity.WIDGET_PREF, Context.MODE_PRIVATE);
//        int id = sharedPreferences.getInt(StepsListActivity.ID_PREF, 0);

        text = sharedPreferences.getString(DetailActivity.NAME_PREF, "no sunsign selected");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sunsign_widget);
        views.setTextViewText(R.id.appwidget_text, text);
        prediction = sharedPreferences.getString(DetailActivity.PREDICTION, "no sunsign selected");
        views.setTextViewText(R.id.widget_prediction_view, prediction);

//        //Set adapter
//        Intent intent = new Intent(context, WidgetService.class);
//        views.setRemoteAdapter(R.id.widget_prediction_view, intent);

        //open mainActivity when title is clicked
        Intent clickIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_prediction_view);
    }

    public static void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
        //Now update all widgets
        for (int appWidgetId : appWidgetIds) {
            WidgetProvider.updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        SharedPreferences sharedPreferences = context.getSharedPreferences(DetailActivity.WIDGET_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(DetailActivity.NAME_PREF);
        editor.remove(DetailActivity.ID_PREF);
        editor.apply();
    }
}

