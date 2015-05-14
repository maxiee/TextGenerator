package com.maxiee.textgenerator.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.maxiee.textgenerator.R;
import com.maxiee.textgenerator.database.CorpusTable;
import com.maxiee.textgenerator.database.DatabaseHelper;
import com.maxiee.textgenerator.ui.dialogs.NewTextDialog;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by maxiee on 5/8/15.
 */
public class DataFragment extends Fragment {

    private ListView dataList;
    private ArrayList<String> mCorpus;
    private ArrayAdapter<String> mArrayAdapter;
    private FloatingActionButton mFAB;
    private DatabaseHelper mDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);
        mDatabaseHelper = DatabaseHelper.instance(getActivity());

        dataList = (ListView) rootView.findViewById(R.id.data_list);
        mCorpus = loadCorpus();
        mArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_corpus, mCorpus);
        dataList.setAdapter(mArrayAdapter);

        mFAB = (FloatingActionButton) rootView.findViewById(R.id.fab);
        mFAB.attachToListView(dataList);

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewTextDialog newTextDialog = new NewTextDialog(getActivity());
                newTextDialog.setOnAddFinishedListener(new NewTextDialog.OnAddFinishedListener() {
                    @Override
                    public void update(String textAdded) {
                        Log.d("maxieed", "isAdded!");
                        mCorpus.add(textAdded);
                        mArrayAdapter.notifyDataSetChanged();
                    }
                });
                newTextDialog.show();
            }
        });

        return rootView;
    }

    private ArrayList<String> loadCorpus() {

        ArrayList<String> ret = new ArrayList<>();

        Cursor cursor = mDatabaseHelper.getReadableDatabase().rawQuery(
                "select * from " + CorpusTable.NAME, null
        );

        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();

            do {
                ret.add(cursor.getString(cursor.getColumnIndex(CorpusTable.CONTENT)));
            } while (cursor.moveToNext());
        }

        Log.d("maxiee", ret.toString());

        return ret;
    }
}
