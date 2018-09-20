package com.example.furyou.omisechallenge;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Adapter for the Charity ListView
 *
 **/

public class CharityAdapter extends ArrayAdapter<Charity>{

    public CharityAdapter(Context context, List<Charity> charities) {
        super(context, 0, charities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.charity,parent, false);
        }

        CharityViewHolder viewHolder = (CharityViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new CharityViewHolder();
            viewHolder.charity_logo = (ImageView) convertView.findViewById(R.id.charity_logo);
            viewHolder.charity_name = (TextView) convertView.findViewById(R.id.charity_name);
            convertView.setTag(viewHolder);
        }

        // getting the position of the Charity in the List<Charity>
        Charity charity = getItem(position);

        // creating the view for one Charity
        try {
            Drawable drawable = Drawable.createFromStream((InputStream) new URL(charity.get_logo_url()).getContent(), "src");
            viewHolder.charity_logo.setImageDrawable(drawable);
        }catch (Exception e){}
        new DownloadImageTask(viewHolder.charity_logo).execute(charity.get_logo_url());
        viewHolder.charity_name.setText(charity.get_name());
        return convertView;
    }

    private class CharityViewHolder{
        public ImageView charity_logo;
        public TextView charity_name;
    }
}
