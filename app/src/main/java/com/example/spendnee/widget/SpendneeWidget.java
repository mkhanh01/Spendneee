package com.example.spendnee.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.spendnee.R;
import com.example.spendnee.activity.HomeActivity;
import com.example.spendnee.data.DataLocalManager;

/**
 * Implementation of App Widget functionality.
 */
public class SpendneeWidget extends AppWidgetProvider {
    private Intent intent;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        String income = DataLocalManager.getSumIncome();
        String expense = DataLocalManager.getSumExpense();
        String total = DataLocalManager.getSumTotal();

        for (int appWidgetId : appWidgetIds) {
            RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.spendnee_widget);

            intent =  new Intent(context, HomeActivity.class);
            view.setOnClickPendingIntent(R.id.my_widget,  PendingIntent.getActivity(context, 0, intent, 0));

            view.setTextViewText(R.id.appwidget_tv_income1, income);
            view.setTextViewText(R.id.appwidget_tv_expense1, expense);
            view.setTextViewText(R.id.appwidget_tv_total1, total);

            appWidgetManager.updateAppWidget(appWidgetId, view);

        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

//    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
//                                int appWidgetId) {
//
//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//        String income = DataLocalManager.getSumIncome();
//        String expense = DataLocalManager.getSumExpense();
//        String total = DataLocalManager.getSumTotal();
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
//        views.setTextViewText(R.id.appwidget_tv_income1, income);
//        views.setTextViewText(R.id.appwidget_tv_expense1, expense);
//        views.setTextViewText(R.id.appwidget_tv_total1, total);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//    }
}