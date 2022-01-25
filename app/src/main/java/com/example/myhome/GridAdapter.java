package com.example.myhome;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Rad14nt
 * Class used to Display a GridAdapter on Overview Activity and get/use the necessary data
 * */

public class GridAdapter extends BaseAdapter {

    private Context context;
    private int[] image;
    private String[] accountName;
    private LayoutInflater inflater;


    /**
     * GridAdapter to model for an member picture thats to be shown in members activity
     * @param context context of the site the GridAdapter was called on
     * @param accountName name of the member to be displayed
     * @param image image id, currently hardcoded
     */
    public GridAdapter(Context context, String[] accountName, int[] image) {
        this.context = context;
        this.accountName = accountName;
        this.image = image;
    }

    @Override
    public int getCount() {
        return accountName.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null){
            convertView = inflater.inflate(R.layout.member_grid_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.grid_image);
        TextView textView = convertView.findViewById(R.id.item_name);

        imageView.setImageResource(image[position]);
        textView.setText(accountName[position]);

        return convertView;
    }
}
