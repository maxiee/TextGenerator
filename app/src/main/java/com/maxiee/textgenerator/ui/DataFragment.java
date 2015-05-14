package com.maxiee.textgenerator.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.maxiee.textgenerator.R;
import com.melnykov.fab.FloatingActionButton;

/**
 * Created by maxiee on 5/8/15.
 */
public class DataFragment extends Fragment {

    private ListView dataList;
    private FloatingActionButton mFAB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);

        dataList = (ListView) rootView.findViewById(R.id.data_list);
        mFAB = (FloatingActionButton) rootView.findViewById(R.id.fab);
        mFAB.attachToListView(dataList);

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        return rootView;
    }
}
