
package com.daiv.android.twitter.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import com.daiv.android.twitter.R;
import com.daiv.android.twitter.manipulations.widgets.NetworkedCacheableImageView;
import twitter4j.Status;
import uk.co.senab.bitmapcache.CacheableBitmapDrawable;

import java.util.ArrayList;

public class MultiplePicsGridAdapter extends PicturesGridAdapter {

    String pics = "";

    public MultiplePicsGridAdapter(Context context, ArrayList<String> text, ArrayList<Status> statuses, int gridWidth) {
        super(context, text, statuses, gridWidth);

        for (String s : text) {
            pics += s + " ";
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.picture, null);

            AbsListView.LayoutParams params = new AbsListView.LayoutParams(gridWidth, gridWidth);
            convertView.setLayoutParams(params);

            ViewHolder holder = new ViewHolder();
            holder.iv = (NetworkedCacheableImageView) convertView.findViewById(R.id.picture);
            convertView.setTag(holder);
        }

        final ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.iv.loadImage(text.get(position), false, new NetworkedCacheableImageView.OnImageLoadedListener() {
            @Override
            public void onImageLoaded(CacheableBitmapDrawable result) {
                holder.iv.setBackgroundDrawable(null);
            }
        });

        return convertView;
    }
}
