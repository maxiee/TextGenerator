package com.maxiee.textgenerator.ui.dialogs;

import android.app.ProgressDialog;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;

import com.maxiee.textgenerator.R;
import com.maxiee.textgenerator.database.CorpusTable;
import com.maxiee.textgenerator.database.DatabaseHelper;
import com.maxiee.textgenerator.markov.Markov;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.LogRecord;

/**
 * Created by maxiee on 15-5-14.
 */
public class NewTextDialog extends AppCompatDialog {

    private Toolbar mToolbar;
    private String mTitle;
    private EditText mEditText;
    private ClipboardManager mClipboardManager;
    private DatabaseHelper mDatabaseHelper;
    private boolean isAdded = false;
    private String textAdded;
    private OnAddFinishedListener mCallback;

    public interface OnAddFinishedListener {
        public void update(String text);
    }

    public NewTextDialog(Context context) {
        super(context, R.style.AppTheme_Dialog);
    }

    public void setOnAddFinishedListener(OnAddFinishedListener callback) {
        mCallback = callback;
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
                    textAdded = mEditText.getText().toString();
                    if (textAdded.isEmpty()) {
                        Toast.makeText(getContext(), R.string.warnning_empty, Toast.LENGTH_LONG).show();
                        return true;
                    }
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

        mDatabaseHelper = DatabaseHelper.instance(getContext());

    }

    private String getPaste() {
        return mClipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
    }

    public boolean isAdded() {
        return isAdded;
    }

    public String getTextAdded() {
        return textAdded;
    }

    private class Task extends AsyncTask<Void, Void, Void> {

        private ProgressDialog mProgress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(getContext());
            mProgress.setCancelable(false);
            String message = getContext().getString(R.string.updating);
            mProgress.setMessage(message);
            mProgress.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            textAdded = Markov.pureText(textAdded);
//            ArrayList<String> list = new ArrayList<>();
//            list.add(content);
//            if (content.isEmpty()) {return null;}
//            JSONObject model = Markov.generateModel(list, 1);
//            Log.d("maxiee", model.toString());
            ContentValues values = new ContentValues();
            values.put(CorpusTable.CONTENT, textAdded);
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            db.beginTransaction();
            db.insert(CorpusTable.NAME, null, values);
            db.setTransactionSuccessful();
            db.endTransaction();
            isAdded = true;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mCallback != null) {
                mCallback.update(textAdded);
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgress.dismiss();
                    dismiss();
                }
            }, 1000);
            dismiss();
        }
    }
}
