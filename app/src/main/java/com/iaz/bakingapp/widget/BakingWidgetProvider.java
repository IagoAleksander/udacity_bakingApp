package com.iaz.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.iaz.bakingapp.R;
import com.iaz.bakingapp.models.Recipe;
import com.iaz.bakingapp.presentation.ui.activities.RecipeActivity;
import com.iaz.bakingapp.presentation.ui.activities.RecipeIngredientsActivity;
import com.iaz.bakingapp.presentation.ui.activities.SelectRecipeActivity;
import com.iaz.bakingapp.util.Constants;

import java.util.ArrayList;


public class BakingWidgetProvider extends AppWidgetProvider {

    static Recipe recipe;

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);

        Intent appIntent;

        if (context.getResources().getBoolean(R.bool.is_tablet)) {
            appIntent = new Intent(context, RecipeActivity.class);
            appIntent.putExtra(Constants.RECIPE_BUNDLE, recipe);
        } else {
            appIntent = new Intent(context, RecipeIngredientsActivity.class);
            appIntent.putExtra(Constants.RECIPE_BUNDLE, recipe);
        }

        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);

        views.setTextViewText(R.id.recipe_name, recipe.getName());

        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent2 = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent2);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Recipe recipeForWidget) {
        recipe = recipeForWidget;
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}