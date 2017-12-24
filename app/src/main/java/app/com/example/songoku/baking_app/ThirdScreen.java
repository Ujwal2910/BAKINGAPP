package app.com.example.songoku.baking_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by songoku on 19/12/17.
 */

public class ThirdScreen extends AppCompatActivity {

    public int pos = 0;
    public FragmentManager fragmentManager;
    public List<Step_Contract> steps;
    public FragmentVideo fragmentVideo1;
    public FragmentDesc fragmentDesc1;
    public FragmentVideo fragmentVideo2;
    public FragmentDesc fragmentDesc2;
    public FloatingActionButton prevFAB, nextFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_screen);

        Intent i = getIntent();
        pos = i.getIntExtra("pos", -1);
        steps = i.getParcelableArrayListExtra("item_arg");

        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        bundle1.putString("bVideo", i.getStringExtra("Vid_Url"));
        bundle1.putString("bThumb", i.getStringExtra("thumbnail"));
        bundle2.putInt("bID", i.getIntExtra("ID", -1));
        bundle2.putString("bShortDesc", i.getStringExtra("Short_Desc"));
        bundle2.putString("bDesc", i.getStringExtra("Desc"));

        fragmentManager = getSupportFragmentManager();

        fragmentVideo1 = new FragmentVideo();
        fragmentVideo1.setArguments(bundle1);
        fragmentManager.beginTransaction()
                .add(R.id.video_container, fragmentVideo1)
                .commit();

        fragmentDesc1 = new FragmentDesc();
        fragmentDesc1.setArguments(bundle2);
        fragmentManager.beginTransaction()
                .add(R.id.description_container, fragmentDesc1)
                .commit();

        nextFAB = (FloatingActionButton) findViewById(R.id.nect_fab);
        prevFAB = (FloatingActionButton) findViewById(R.id.previous_fab);

        checkFABs(pos);

        nextFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos<steps.size()-1) {
                    pos++;
                    updateFragments(pos);
                }
            }
        });

        prevFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos>0) {
                    pos--;
                    updateFragments(pos);
                }
            }
        });
    }

    public void updateFragments(int pos) {

        checkFABs(pos);

        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();

        bundle1.putString("bVideo", steps.get(pos).getVideoURL());
        bundle1.putString("bThumb", steps.get(pos).getThumbnailURL());

        bundle2.putInt("bID", steps.get(pos).getId());
        bundle2.putString("bShortDesc", steps.get(pos).getShortDescription());
        bundle2.putString("bDesc", steps.get(pos).getDescription());

        fragmentVideo2 = new FragmentVideo();
        fragmentDesc2 = new FragmentDesc();

        fragmentVideo2.setArguments(bundle1);
        fragmentDesc2.setArguments(bundle2);

        fragmentManager.beginTransaction()
                .replace(R.id.video_container, fragmentVideo2)
                .commit();
        fragmentManager.beginTransaction()
                .replace(R.id.description_container, fragmentDesc2)
                .commit();
    }

    public void checkFABs(int pos) {
        if(pos == 0) {
            prevFAB.setEnabled(false);
            prevFAB.setVisibility(View.GONE);
        }
        else {
            prevFAB.setEnabled(true);
            prevFAB.setVisibility(View.VISIBLE);
        }

        if(pos == steps.size()-1) {
            nextFAB.setEnabled(false);
            nextFAB.setVisibility(View.GONE);
        }
        else {
            nextFAB.setEnabled(true);
            nextFAB.setVisibility(View.VISIBLE);
        }
    }
}
