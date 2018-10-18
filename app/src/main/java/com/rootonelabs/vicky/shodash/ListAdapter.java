package com.rootonelabs.vicky.shodash;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vicky on 14-Oct-18.
 */

public class ListAdapter extends ArrayAdapter<listDetails>{

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<listDetails> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_detail, parent, false);
        }

        TextView speakerName = (TextView)convertView.findViewById(R.id.nameTextView);
        TextView topic = (TextView)convertView.findViewById(R.id.topicTextView);
        TextView yt_url = (TextView)convertView.findViewById(R.id.urlTextView);

        listDetails details = getItem(position);

        speakerName.setText(details.getSpeakerName());
        topic.setText(details.getTopic());
        topic.setText(details.getYt_url());

        return convertView;
    }
}
