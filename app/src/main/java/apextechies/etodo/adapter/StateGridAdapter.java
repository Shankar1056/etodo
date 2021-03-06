package apextechies.etodo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apextechies.etodo.R;
import apextechies.etodo.activity.SearchAddressGooglePlacesActivity;
import apextechies.etodo.model.HomeCategory;
import apextechies.etodo.network.Utilz;

/**
 * Created by Shankar on 7/11/2017.
 */
public class StateGridAdapter extends BaseAdapter {
    HomeCategory model;
    ArrayList<HomeCategory> modellist;
    private Activity ativity;
    private int newcount;
    private static LayoutInflater inflater = null;
    private SearchAddressGooglePlacesActivity  allstate;

    public StateGridAdapter(Activity c, ArrayList<HomeCategory> haml, SearchAddressGooglePlacesActivity allState) {
        try {
            this.ativity = c;
            this.modellist = haml;
            this.allstate = allState;
            inflater = (LayoutInflater) ativity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        catch (Exception e)
        {

        }

    }
    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView cat_name;
        ImageView cat_image;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        final ViewHolder holder;

        if (convertView == null) {
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.assstate_item, null);


            /****** View Holder Object to contain tabitem.xml file elements ******/
            holder = new ViewHolder();
            holder.cat_image = (ImageView) vi.findViewById(R.id.cat_image);
            holder.cat_name = (TextView) vi.findViewById(R.id.cat_name);

            holder.cat_name.setTypeface(Utilz.font(ativity,"bold"));

            vi.setTag(holder);
            vi.setTag(R.id.cat_name, holder.cat_name);
            vi.setTag(R.id.cat_image, holder.cat_image);

        } else {
            holder = (ViewHolder) vi.getTag();

        }
        if (modellist.size() <= 0) {
        } else {

            model = modellist.get(position);
            holder.cat_image.setTag(position);
            holder.cat_name.setTag(position);
            holder.cat_name.setText(model.getCat_name());
    
            Picasso.with(ativity).load(model.getCat_image()).into(holder.cat_image);
    
           

        }
        return vi;
    }



}
