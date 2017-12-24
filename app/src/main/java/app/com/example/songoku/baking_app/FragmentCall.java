package app.com.example.songoku.baking_app;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class FragmentCall extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment fragmentVideo = new FragmentVideo();
    Fragment fragmentDesc = new FragmentDesc();
    public void func(Context context, int position, Step_Contract step) {
//        Toast.makeText(context, "Baap Baap hota hai BC", Toast.LENGTH_SHORT).show();
        Bundle b1 = new Bundle();
        b1.putString("bVideo", step.getVideoURL());
        b1.putString("bThumb", step.getThumbnailURL());

        Bundle b2 = new Bundle();
        b2.putInt("bID", step.getId());
        b2.putString("bShortDesc", step.getShortDescription());
        b2.putString("bDesc", step.getDescription());

        fragmentVideo.setArguments(b1);
        fragmentManager.beginTransaction()
                .replace(R.id.video_container, fragmentVideo)
                .commit();
        fragmentDesc.setArguments(b2);
        fragmentManager.beginTransaction()
                .replace(R.id.description_container, fragmentDesc)
                .commit();
    }
}
