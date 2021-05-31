package com.example.covidtracker.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.example.covidtracker.R;
import com.example.covidtracker.api.RetrofitClient;
import com.example.covidtracker.model.IndonesiaResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of App Widget functionality.
 */
public class InfoCovidWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.info_covid_widget);

        RetrofitClient.INSTANCE.getInstance().getIndonesia().enqueue(new Callback<ArrayList<IndonesiaResponse>>(){
            @Override
            public void onResponse(@NotNull Call<ArrayList<IndonesiaResponse>> call, @NotNull Response<ArrayList<IndonesiaResponse>> response) {
                try {
                    if (response.isSuccessful()){
                        IndonesiaResponse indonesiaResponse = response.body().get(0);
                        views.setTextViewText(R.id.tv_positif, indonesiaResponse.getPositif());
                        views.setTextViewText(R.id.tv_meninggal, indonesiaResponse.getMeninggal());
                        views.setTextViewText(R.id.tv_sembuh, indonesiaResponse.getSembuh());
                        views.setTextViewText(R.id.tv_dirawat, indonesiaResponse.getDirawat());

                        // Instruct the widget manager to update the widget
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    }
                } catch (Exception e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ArrayList<IndonesiaResponse>> call, @NotNull Throwable t) {
                t.getMessage();
            }
        });
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}