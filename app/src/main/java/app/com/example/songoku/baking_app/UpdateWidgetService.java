package app.com.example.songoku.baking_app;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by songoku on 21/12/17.
 */

public class UpdateWidgetService extends IntentService{
    public UpdateWidgetService() {
        super("UpdateWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            ArrayList<String> fromActivityIngredientsList = intent.getExtras()
                    .getStringArrayList("Ingredient_Widget");
            handleUpdate(fromActivityIngredientsList);
        }

    }

    private void handleUpdate(ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra("Ingredient_Widget",fromActivityIngredientsList);
        sendBroadcast(intent);
    }
    public static void startBakingService(Context context, ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.putExtra("Ingredient_Widget",fromActivityIngredientsList);
        context.startService(intent);
    }
}
