package app.com.example.songoku.baking_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by songoku on 19/12/17.
 */

public class FragmentDesc extends Fragment {

    private String shortDesc;
    private String desc;

    public FragmentDesc() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_desc, container, false);
        shortDesc = getArguments().getString("bShortDesc");
        desc = getArguments().getString("bDesc");

        TextView tvShortDesc = (TextView) rootView.findViewById(R.id.tv_short_desc);
        tvShortDesc.setText(shortDesc);

        TextView tvDesc = (TextView) rootView.findViewById(R.id.tv_desc);
        tvDesc.setText(desc);

        return rootView;
    }
}
