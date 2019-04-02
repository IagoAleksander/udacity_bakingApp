package com.iaz.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.iaz.bakingapp.R;
import com.iaz.bakingapp.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

import static com.iaz.bakingapp.widget.BakingWidgetProvider.recipe;

public class GridWidgetService extends RemoteViewsService {
    private List<String> remoteViewingredientsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(), intent);
    }


    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        final Context mContext;

        private GridRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            ArrayList<String> recipeIngredientsForWidgets = new ArrayList<>();

            for (Ingredient ingredient : recipe.getIngredients()) {
                recipeIngredientsForWidgets.add(String.format("- %s %s of %s", ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
            }

            remoteViewingredientsList = recipeIngredientsForWidgets;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {

            return remoteViewingredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_grid_view_item);

            views.setTextViewText(R.id.widget_grid_view_item, remoteViewingredientsList.get(position));

            Intent fillInIntent = new Intent();
            views.setOnClickFillInIntent(R.id.widget_grid_view_item, fillInIntent);

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
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


    }


}