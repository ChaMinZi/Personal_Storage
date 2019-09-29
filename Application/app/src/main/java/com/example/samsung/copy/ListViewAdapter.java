package com.example.samsung.copy;

/**
 * Created by SAMSUNG on 2017-11-08.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter{
    static public ArrayList<ListViewItem> listViewItemList=new ArrayList<ListViewItem>();

    public ListViewAdapter(){

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        ImageView imgImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView1) ;

        ListViewItem listViewItem = listViewItemList.get(position);

        Glide.with(context).load(listViewItem.getImg()).into(imgImageView);
        descTextView.setText(listViewItem.getDesc());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public int getCount() {
        return this.listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(String img, String desc) {
        ListViewItem item = new ListViewItem();

        item.setImg(img);
        item.setDesc(desc);

        listViewItemList.add(item);
    }
}
