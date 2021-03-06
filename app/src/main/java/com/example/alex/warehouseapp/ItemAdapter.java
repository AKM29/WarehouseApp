package com.example.alex.warehouseapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.ecommerce.Product;

import java.util.ArrayList;

/**
 * Created by Alex on 17/09/2017.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    private Activity activity;
    private ArrayList<Item> items;
    private static LayoutInflater inflater = null;

    public ItemAdapter(Activity activity, int resource, ArrayList<Item> items) {
        super(activity, resource, items);

        try {
            this.activity = activity;
            this.items = items;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {

        }
    }

    public int getCount() {
        return items.size();
    }

    public Product getItem(Product position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public ImageView display_image;
        public TextView display_price;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder viewHolder;

        try {
            if(convertView == null) {
                view = inflater.inflate(R.layout.item_layout, null);
                viewHolder = new ViewHolder();

                viewHolder.display_name = (TextView) view.findViewById(R.id.display_name);
                viewHolder.display_image = (ImageView) view.findViewById(R.id.display_image);
                viewHolder.display_price = (TextView) view.findViewById(R.id.display_price);

                view.setTag(viewHolder);

                Item i = items.get(position);
                viewHolder.display_name.setText(items.get(position).getName());
                double price = items.get(position).getPrice();
                viewHolder.display_price.setText(Double.toString(price));
            } else {
                viewHolder = (ViewHolder) view.getTag();

                Item i = items.get(position);
                viewHolder.display_name.setText(items.get(position).getName());
                double price = items.get(position).getPrice();
                viewHolder.display_price.setText(Double.toString(price));

                System.out.println(i.getName() + ":" + i.getPrice());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return view;
    }
}
