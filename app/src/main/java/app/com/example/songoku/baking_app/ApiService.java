package app.com.example.songoku.baking_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by songoku on 17/11/17.
 */

public interface ApiService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe_Contract>> getResultInfo();

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<Ingredient> getIngredientInfo();

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<Step_Contract> getStepInfo();

}
