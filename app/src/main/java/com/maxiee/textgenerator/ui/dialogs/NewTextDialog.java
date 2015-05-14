package com.maxiee.textgenerator.ui.dialogs;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.maxiee.textgenerator.R;

/**
 * Created by maxiee on 15-5-14.
 */
public class NewTextDialog extends AppCompatDialog {

    private Toolbar mToolbar;
    private String mTitle;

    public NewTextDialog(Context context) {
        super(context, R.style.AppTheme_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_text);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

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

                    return true;
                }

                return false;
            }
        });

        mTitle = getContext().getString(R.string.add);
        mToolbar.setTitle(mTitle);
    }
}
