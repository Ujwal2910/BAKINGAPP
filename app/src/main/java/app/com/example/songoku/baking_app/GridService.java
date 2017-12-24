package app.com.example.songoku.baking_app;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import static app.com.example.songoku.baking_app.WidgetProvider.mList;

/**
 * Created by songoku on 21/12/17.
 */

public class GridService extends RemoteViewsService {
    List<String> mIngredientList;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteFactory(this.getApplicationContext(),intent);
    }

    private class GridRemoteFactory implements RemoteViewsFactory {

        Context mContext = null;

        public GridRemoteFactory(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            mIngredientList = mList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mIngredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(mContext.getPackageName()
                    , R.layout.widget_list);
            views.setTextViewText(R.id.ingredient_item,
            mIngredientList.get(i));

            Intent populate_intent = new Intent();
            views.setOnClickFillInIntent(R.id.ingredient_item,populate_intent);
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
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
