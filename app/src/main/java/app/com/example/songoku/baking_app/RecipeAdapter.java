package app.com.example.songoku.baking_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songoku on 17/11/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private Context mContext;
    private List<Recipe_Contract> mRecipeList;

    public RecipeAdapter(Context context, List<Recipe_Contract> recipeList)
    {
        this.mContext = context;
        this.mRecipeList = recipeList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

        Integer serve_number = mRecipeList.get(position).getServings();

        String servings = String.valueOf(serve_number);

        holder.mTitle.setText(mRecipeList.get(position).getName());

        String poster = mRecipeList.get(position).getImage();


        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.khana)
                .error(R.drawable.khana)
                .into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

       // @BindView(R.id.recipe_title)
        TextView mTitle  ;

        //@BindView(R.id.recipe_image_design)
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
           // ButterKnife.bind(this, itemView);
            mTitle = (TextView) itemView.findViewById(R.id.recipe_title);
            mImageView = (ImageView) itemView.findViewById(R.id.recipe_image_design);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            int position = getAdapterPosition();
            Recipe_Contract mRecipe = mRecipeList.get(position);

            Intent intent = new Intent(context, RecipeStepList.class);
            intent.putExtra("Title",mRecipe.getName());

            Log.d("mrecipe =",mRecipe.getName());
//            Log.d("mrecipe2 =",mRecipe.getSteps().toString());

            ArrayList<Ingredient> ingredients = new ArrayList<>(mRecipe.getIngredients());
            intent.putParcelableArrayListExtra("Ingredient",ingredients);

            ArrayList<Step_Contract> steps = new ArrayList<>(mRecipe.getSteps());
            intent.putParcelableArrayListExtra("Step",steps);

            context.startActivity(intent);

        }
    }
}
