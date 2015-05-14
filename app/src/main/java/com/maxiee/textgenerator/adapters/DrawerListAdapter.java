package com.maxiee.textgenerator.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;
import com.maxiee.textgenerator.R;

import java.util.ArrayList;

public class DrawerListAdapter extends BaseAdapter {

    private ArrayList<Item> data;
    private LayoutInflater mInflater;
    private int normalColor, accentColor;

    private int selectedPosition = 0;

    public DrawerListAdapter(
            Context context,
            ArrayList<Item> data,
            @ColorRes int normalColor, @ColorRes int accentColor) {
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
        this.normalColor = context.getResources().getColor(normalColor);
        this.accentColor = context.getResources().getColor(accentColor);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Item getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public int getSelectedPosition() {
        return  selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_drawer, parent, false);

            holder = new ViewHolder();
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.tv_icon = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_icon.setText(getItem(position).title);
        try {
            holder.iv_icon.setImageResource(getItem(position).iconId);
        } catch (Exception e) {
            holder.iv_icon.setImageResource(R.mipmap.ic_launcher);
        }

        if (position != selectedPosition) {
            try {
                holder.tv_icon.setTextColor(normalColor);
                holder.iv_icon.setColorFilter(normalColor, PorterDuff.Mode.MULTIPLY);
            } catch (Exception e) {}
        } else {
            holder.tv_icon.setTextColor(accentColor);
            try {
                holder.iv_icon.setColorFilter(accentColor, PorterDuff.Mode.MULTIPLY);
            } catch (Exception e) {}
        }
        return convertView;
    }

    public class ViewHolder {
        public ImageView iv_icon;
        public TextView tv_icon;
    }


    public static class Item {
        public int iconId;
        public String title;

        public Item(String title, @DrawableRes int iconId) {
            this.title = title;
            this.iconId = iconId;
        }
    }
}
