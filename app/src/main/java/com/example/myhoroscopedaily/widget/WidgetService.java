package com.example.myhoroscopedaily.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.myhoroscopedaily.R;


public class WidgetService extends RemoteViewsService {

    private String sunsign;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteListFactory(getApplicationContext());
    }

    private class RemoteListFactory implements RemoteViewsFactory {

        Context mContext;

        RemoteListFactory(Context applicationContext) {
            this.mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            sunsign = WidgetProvider.prediction;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.sunsign_widget);
            views.setTextViewText(R.id.appwidget_text, "Taurus will have a good day");

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
