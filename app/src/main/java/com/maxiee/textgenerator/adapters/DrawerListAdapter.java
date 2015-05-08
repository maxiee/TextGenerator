package com.maxiee.textgenerator.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import com.maxiee.textgenerator.R;

public class DrawerListAdapter extends ArrayAdapter<String> {

    public static class ViewHolder {
        public final View root;
        public final TextView textView;

        public ViewHolder(View view) {
            root = view;
            textView = (TextView) root.findViewById(R.id.text);
        }
    }

    public DrawerListAdapter(Context context, String[] items) {
        super(context, -1, items);
    }

    @Override
    public final View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(parent.getContext(), R.layout.list_item_drawer, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(getItem(i));
        return view;
    }
}
