package com.maxiee.textgenerator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.maxiee.textgenerator.R;

/**
 * Created by maxiee on 15-5-14.
 */
public class GeneratorFragment extends Fragment{

    private EditText mGenerateredText;
    private Button mGenerateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_generator, container, false);

        mGenerateredText = (EditText) rootView.findViewById(R.id.text);
        mGenerateButton = (Button) rootView.findViewById(R.id.generate);

        return rootView;
    }
}
