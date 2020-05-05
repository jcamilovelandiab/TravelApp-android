package com.app.travelapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.travelapp.R;
import com.app.travelapp.data.model.User;

import java.util.ArrayList;

public class UserArrayAdapter extends BaseAdapter {

    ArrayList<User> users;
    Context context;

    public UserArrayAdapter(Context context, ArrayList<User> users) {
        this.users = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
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
            view =inflater.inflate(R.layout.layout_user, null);
            User u = users.get(position);
            TextView tv_name, tv_email, tv_username, tv_role;
            ImageView iv_picture;

            iv_picture = view.findViewById(R.id.layout_user_iv_picture);
            tv_name = view.findViewById(R.id.layout_user_tv_name);
            tv_email = view.findViewById(R.id.layout_user_tv_email);
            tv_username = view.findViewById(R.id.layout_user_tv_username);
            tv_role = view.findViewById(R.id.layout_user_tv_role);
            tv_name.setText(u.getFull_name());
            tv_email.setText(u.getEmail());
            tv_username.setText(u.getUsername());
            tv_role.setText(u.getRole());
        }
        return view;
    }

}
