package app.com.example.songoku.baking_app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Created by songoku on 20/12/17.
 */

public class WidgetProvider extends AppWidgetProvider
{

    static ArrayList<String> mList = new ArrayList<>();
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        //mutilple widget update
        for (int appWidgetId : appWidgetIds)
        {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget_screen_2);
        Intent appIntent = new Intent(context, MainActivity.class);
        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.ingredients_grid_view,appPendingIntent);

        Intent intent = new Intent(context, GridService.class);
        views.setRemoteAdapter(R.id.ingredients_grid_view,intent);

        appWidgetManager.updateAppWidget(appWidgetId,views);
    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
        final String action_update = intent.getAction();

        if (action_update.equals("android.appwidget.action.APPWIDGET_UPDATE2")) {
            mList = intent.getExtras().getStringArrayList("Ingredient_Widget");
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredients_grid_view);

            //update mutilple widgets
            WidgetProvider.updateBakingWidgets(context, appWidgetManager, appWidgetIds);
            super.onReceive(context, intent);
        }

    }
}
