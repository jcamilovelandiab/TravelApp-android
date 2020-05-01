package com.app.travelapp.ui.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.travelapp.R;
import com.app.travelapp.data.model.Place;

import java.util.ArrayList;

public class PlaceArrayAdapter extends BaseAdapter {

    ArrayList<Place> places;
    Context context;

    public PlaceArrayAdapter(ArrayList<Place> places, Context context) {
        this.places = places;
        this.context = context;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Object getItem(int position) {
        return places.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =inflater.inflate(R.layout.layout_place, null);
            Place place = places.get(position);
            TextView tv_author, tv_address, tv_description;
            ImageView iv_picture;
            tv_author = view.findViewById(R.id.layout_place_tv_author);
            tv_address = view.findViewById(R.id.layout_place_tv_address);
            tv_description = view.findViewById(R.id.layout_place_tv_description);
        }
        return view;
    }
}
