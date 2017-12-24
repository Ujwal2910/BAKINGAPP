package app.com.example.songoku.baking_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by songoku on 18/11/17.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder>{
    private Context mContext;
    private List<Step_Contract> mStepLists;
    private boolean mTwoPane;

    public StepClickListener listener;

    public interface StepClickListener {
        void onStepClick(int position);
    }

    public StepAdapter(Context context, List<Step_Contract> steps, Boolean mTwoPane, StepClickListener mClickListener){
        this.mContext = context;
        this.mStepLists = steps;
        this.mTwoPane = mTwoPane;
        this.listener = mClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_list,parent,false);
        return new StepAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Step_Contract step = mStepLists.get(position);
        holder.Stepname.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mStepLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView Stepname;
        View mView;
        public ViewHolder(View itemView) {
            super(itemView);
            Stepname = (TextView) itemView.findViewById(R.id.step_name);
            mView = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onStepClick(getAdapterPosition());
        }
    }
}
