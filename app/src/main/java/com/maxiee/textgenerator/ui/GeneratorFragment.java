package com.maxiee.textgenerator.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.maxiee.textgenerator.R;
import com.maxiee.textgenerator.database.DatabaseHelper;
import com.maxiee.textgenerator.database.ModelTable;
import com.maxiee.textgenerator.markov.Markov;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxiee on 15-5-14.
 */
public class GeneratorFragment extends Fragment{

    private TextView mGenerateredText;
    private Button mGenerateButton;
    private DatabaseHelper mDatabaseHelper;
    private JSONObject model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_generator, container, false);
        mDatabaseHelper = DatabaseHelper.instance(getActivity());

        mGenerateredText = (TextView) rootView.findViewById(R.id.text);
        mGenerateButton = (Button) rootView.findViewById(R.id.generate);
        mGenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGenerateredText.setText(generateText());
            }
        });

        return rootView;
    }

    public String generateText() {
        String ret = "";
        Cursor cursor = mDatabaseHelper.getReadableDatabase().query(
            ModelTable.NAME,
                    new String[] {
                            ModelTable.CONTENT
                    },
                    ModelTable.ID + "=?",
                    new String[] {"1"},
                    null, null, null
        );

        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            Log.d("maxiee", "加载模型");
            try {
                model = new JSONObject(cursor.getString(
                        cursor.getColumnIndex(ModelTable.CONTENT)
                ));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (model != null) {
            Log.d("maxiee", "模型不为空");
            try {
                ret = Markov.generateText(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.d("maxiee", ret);
        return ret;
    }
}
