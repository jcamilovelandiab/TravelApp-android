package com.app.travelapp.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.app.travelapp.R;
import com.app.travelapp.data.model.Place;

import java.io.File;
import java.util.List;

public class PlaceListRecyclerViewAdapter extends RecyclerView.Adapter<PlaceListRecyclerViewAdapter.PlaceViewHolder> {

    private Context context;
    private List<Place> placeList;
    RecyclerViewClickListener listener;

    public PlaceListRecyclerViewAdapter(Context context, List<Place> placeList,RecyclerViewClickListener listener) {
        this.context = context;
        this.placeList = placeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.layout_place, parent, false);
        return new PlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.bind(place, listener);
    }

    private Uri loadImage(String pathImage){
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

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder{

        TextView tv_author, tv_name, tv_address, tv_description;
        ImageView iv_picture;
        ImageButton ib_more;

        public PlaceViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setBackgroundColor(itemView.getResources().getColor(R.color.colorAccent));
            iv_picture = itemView.findViewById(R.id.layout_place_iv_picture);
            tv_author = itemView.findViewById(R.id.layout_place_tv_author);
            tv_name = itemView.findViewById(R.id.layout_place_tv_name);
            tv_address = itemView.findViewById(R.id.layout_place_tv_address);
            tv_description = itemView.findViewById(R.id.layout_place_tv_description);
            ib_more = itemView.findViewById(R.id.layout_place_ib_more);
        }

        public void bind(final Place place, final RecyclerViewClickListener listener){
            tv_author.setText(place.getAuthor().getUsername());
            tv_name.setText(place.getName());
            tv_address.setText(place.getAddress());
            tv_description.setText(place.getDescription());
            if(place.getImages().size()==0){
                iv_picture.setImageDrawable(itemView.getResources().getDrawable(R.drawable.places1));
            }else{
                Uri photoUri = loadImage(place.getImages().get(0));
                if(photoUri!=null){
                    iv_picture.setImageURI(photoUri);
                }else{
                    iv_picture.setImageDrawable(itemView.getResources().getDrawable(R.drawable.places1));
                }
            }
            ib_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMorePlaceClicked(place);
                }
            });
        }

    }

}
