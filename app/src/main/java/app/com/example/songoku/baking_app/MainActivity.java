package app.com.example.songoku.baking_app;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
//import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.recycler_view_recipe)
    private RecyclerView mRecyclerView;
//    @BindView(R.id.error_text)
    private TextView mError;

    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ButterKnife.bind(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_recipe);
        mError = (TextView) findViewById(R.id.error_text);
        new LoadRecipeData().execute();
    }

    private class LoadRecipeData extends AsyncTask<Void,Void,Recipe_Contract> {

        public LoadRecipeData()
        {}

        @Override
        protected Recipe_Contract doInBackground(Void... voids) {

            try {

                RetrofitBuilder builder = new RetrofitBuilder();
                ApiService service = builder.createService(ApiService.class);
                Call<List<Recipe_Contract>> call = service.getResultInfo();
                call.enqueue(new Callback<List<Recipe_Contract>>() {
                    @Override
                    public void onResponse(Call<List<Recipe_Contract>> call, Response<List<Recipe_Contract>> response) {
                        List<Recipe_Contract> Recipe_list = response.body();
                        NoError();
                        mRecipeAdapter = new RecipeAdapter(getApplicationContext(), Recipe_list);
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            mRecyclerView.setLayoutManager(layoutManager);
                        }
                        else {
                            LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                            mRecyclerView.setLayoutManager(layoutManager);
                        }
                        mRecyclerView.setAdapter(mRecipeAdapter);
                        mRecipeAdapter.notifyDataSetChanged();
                        Log.d("try: ", "tried");
                    }

                    @Override
                    public void onFailure(Call<List<Recipe_Contract>> call, Throwable t) {
                        YesError();
                        Log.d("fail: ", "failed");
                    }
                });
            }
            catch (Exception e)
            {
                Log.d("catch: ", "catched");
            }
            return null;
        }
    }

    public void YesError(){
        mError.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    public void NoError(){
        mError.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
