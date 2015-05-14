package com.maxiee.textgenerator.ui.dialogs;

import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;

import com.maxiee.textgenerator.R;
import com.maxiee.textgenerator.markov.Markov;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by maxiee on 15-5-14.
 */
public class NewTextDialog extends AppCompatDialog {

    private Toolbar mToolbar;
    private String mTitle;
    private EditText mEditText;
    private ClipboardManager mClipboardManager;

    public NewTextDialog(Context context) {
        super(context, R.style.AppTheme_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_text);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mEditText = (EditText) findViewById(R.id.text);

        if (Build.VERSION.SDK_INT >= 21) {
            mToolbar.setElevation(15.6f);
        }

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mToolbar.inflateMenu(R.menu.dialog_add);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.add) {
                    new Task().execute();
                    return true;
                }

                return false;
            }
        });

        mTitle = getContext().getString(R.string.add);
        mToolbar.setTitle(mTitle);

        mClipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        MenuItem pasteItem = mToolbar.getMenu().findItem(R.id.paste);
        if (!(mClipboardManager.hasPrimaryClip()) ||
                !(mClipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))) {
            pasteItem.setEnabled(false);
        } else {
            pasteItem.setEnabled(true);
            mEditText.setText(getPaste());
        }

    }

    private String getPaste() {
        return mClipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
    }

    private class Task extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String content = mEditText.getText().toString();
            ArrayList<String> list = new ArrayList<>();
            list.add(content);
            if (content.isEmpty()) {return null;}
            JSONObject model = Markov.generateModel(list, 1);
            Log.d("maxiee", model.toString());
            return null;
        }
    }
}
