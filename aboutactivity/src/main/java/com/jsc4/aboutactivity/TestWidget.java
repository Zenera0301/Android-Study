package com.jsc4.aboutactivity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

public class TestWidget extends AppWidgetProvider {

    public static final String WIDGET_BUTTON_ACTION = "WIDGET_BUTTON_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent != null && TextUtils.equals(intent.getAction(), WIDGET_BUTTON_ACTION)){
            Log.i("djtest", "onReceive: be clicked");
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
            remoteViews.setTextViewText(R.id.widget_textview, "be clicked");
            remoteViews.setTextColor(R.id.widget_textview, Color.RED);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, TestWidget.class);

            appWidgetManager.updateAppWidget(componentName, remoteViews);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);

        Intent intent = new Intent();
        intent.setClass(context, TestWidget.class);
        intent.setAction(WIDGET_BUTTON_ACTION);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,0);
        remoteViews.setOnClickPendingIntent(R.id.widget_button, pendingIntent);

        // 告诉控件已经操作响应过了
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

    }
}
