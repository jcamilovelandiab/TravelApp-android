package com.app.travelapp.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.app.travelapp.R;
import com.app.travelapp.data.model.Place;

import java.io.File;
import java.util.ArrayList;

public class PlaceArrayAdapter extends BaseAdapter {

    @Nullable
    Integer backgroundColor;
    ArrayList<Place> places;
    Context context;

    public PlaceArrayAdapter(Context context, ArrayList<Place> places) {
        this.places = places;
        this.context = context;
        this.backgroundColor = null;
    }

    public PlaceArrayAdapter(Context context, ArrayList<Place> places, Integer backgroundColor) {
        this.places = places;
        this.context = context;
        this.backgroundColor = backgroundColor;
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
            if(this.backgroundColor!=null) view.setBackgroundColor(this.backgroundColor);
            Place place = places.get(position);
            TextView tv_author, tv_name, tv_address, tv_description;
            ImageView iv_picture;

            iv_picture = view.findViewById(R.id.layout_place_iv_picture);
            tv_author = view.findViewById(R.id.layout_place_tv_author);
            tv_name = view.findViewById(R.id.layout_place_tv_name);
            tv_address = view.findViewById(R.id.layout_place_tv_address);
            tv_description = view.findViewById(R.id.layout_place_tv_description);

            tv_author.setText(place.getAuthor().getUsername());
            tv_name.setText(place.getName());
            tv_address.setText(place.getAddress());
            tv_description.setText(place.getDescription());
            if(place.getImages().size()==0){
                iv_picture.setImageDrawable(view.getResources().getDrawable(R.drawable.places1));
            }else{
                Uri photoUri = loadImage(place.getImages().get(0));
                if(photoUri!=null){
                    iv_picture.setImageURI(photoUri);
                }else{
                    iv_picture.setImageDrawable(view.getResources().getDrawable(R.drawable.places1));
                }
            }
        }
        return view;
    }

    //cargar imagen
    public Uri loadImage(String pathImage){
        try{
            File filePhoto=new File(pathImage);
            String authority = context.getString(R.string.authority_package);
            return FileProvider.getUriForFile(this.context,authority,filePhoto);
        }catch (Exception ex){
            //Toast.makeText(this.context, "An error occurred while attempting to load the image", Toast.LENGTH_SHORT).show();
            Log.d("Loading image","Error occurred while attempting to load the image "+pathImage+"\nMessage: "+ex.getMessage()+"\nCause: "+ex.getCause());
            return null;
        }
    }

}