package app.com.example.songoku.baking_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by songoku on 18/11/17.
 */

public class RecipeStepList  extends AppCompatActivity implements StepAdapter.StepClickListener{

    private TextView ingredient_List;
    private RecyclerView steps_List;
    private StepAdapter stepAdapter;
    private ArrayList<Step_Contract> steps;
    private boolean mTwoPane;
    public FragmentManager fragmentManager;
//    public Fragment fragmentVideo, fragmentDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);

        steps = getIntent().getParcelableArrayListExtra("Step");

        if(findViewById(R.id.two_pane_linear_layout) != null) {
            mTwoPane = true;
        }
        else {
            mTwoPane = false;
        }

//        fragmentVideo = new FragmentVideo();
//        fragmentDesc = new FragmentDesc();
        fragmentManager = getSupportFragmentManager();

        ingredient_List = (TextView)findViewById(R.id.tv_ingredients_list);
        steps_List = (RecyclerView)findViewById(R.id.rv_recipe_steps);

        String title = getIntent().getStringExtra("Title");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        //toolbar.setTitle(title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }

        //androidhive for the ingredient concatenation part
        ArrayList<String> recipeIngredientsForWidgets= new ArrayList<>();

        ArrayList<Ingredient> ingredients = getIntent().getParcelableArrayListExtra("Ingredient");
        int num = ingredients.size();
        for(int i = 0; i < num; i++) {
            ingredient_List.append(ingredients.get(i).getIngredient()+" "
                            +ingredients.get(i).getQuantity().toString()+" "
                            +ingredients.get(i).getMeasure()+"\n");

            recipeIngredientsForWidgets.add(ingredients.get(i).getIngredient()+" "
                    +ingredients.get(i).getQuantity().toString()+" "
                    +ingredients.get(i).getMeasure()+"\n");
        }

        UpdateWidgetService.startBakingService(getApplicationContext(),
                recipeIngredientsForWidgets);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        if(mTwoPane) {
            Fragment fragmentVideo = new FragmentVideo();
            Fragment fragmentDesc = new FragmentDesc();
            Bundle bundle1 = new Bundle();
            Bundle bundle2 = new Bundle();
            bundle1.putString("bVideo", steps.get(0).getVideoURL());
            bundle1.putString("bThumb", steps.get(0).getThumbnailURL());
            bundle2.putInt("bID", steps.get(0).getId());
            bundle2.putString("bShortDesc", steps.get(0).getShortDescription());
            bundle2.putString("bDesc", steps.get(0).getDescription());
            fragmentVideo.setArguments(bundle1);
            fragmentDesc.setArguments(bundle2);
            fragmentManager.beginTransaction()
                    .add(R.id.video_container, fragmentVideo)
                    .commit();
            fragmentManager.beginTransaction()
                    .add(R.id.description_container, fragmentDesc)
                    .commit();
        }

        stepAdapter = new StepAdapter(getApplicationContext(), steps, mTwoPane, this);
        steps_List.setLayoutManager(layoutManager);
        steps_List.setAdapter(stepAdapter);
        stepAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStepClick(int position) {
        if(!mTwoPane) {

            Intent intent = new Intent(this, ThirdScreen.class);
            intent.putParcelableArrayListExtra("item_arg", steps);
            intent.putExtra("pos", position);
            intent.putExtra("ID", steps.get(position).getId());
            intent.putExtra("Desc", steps.get(position).getDescription());
            intent.putExtra("Short_Desc", steps.get(position).getShortDescription());
            intent.putExtra("Vid_Url", steps.get(position).getVideoURL());
            intent.putExtra("thumbnail", steps.get(position).getThumbnailURL());
            startActivity(intent);
        }
        else {
            Fragment fragmentVideo = new FragmentVideo();
            Fragment fragmentDesc = new FragmentDesc();
            Bundle bundle1 = new Bundle();
            Bundle bundle2 = new Bundle();
            bundle1.putString("bVideo", steps.get(position).getVideoURL());
            bundle1.putString("bThumb", steps.get(position).getThumbnailURL());
            bundle2.putInt("bID", steps.get(position).getId());
            bundle2.putString("bShortDesc", steps.get(position).getShortDescription());
            bundle2.putString("bDesc", steps.get(position).getDescription());
            fragmentVideo.setArguments(bundle1);
            fragmentDesc.setArguments(bundle2);
            fragmentManager.beginTransaction()
                    .replace(R.id.video_container, fragmentVideo)
                    .commit();
            fragmentManager.beginTransaction()
                    .replace(R.id.description_container, fragmentDesc)
                    .commit();
        }
    }
}
