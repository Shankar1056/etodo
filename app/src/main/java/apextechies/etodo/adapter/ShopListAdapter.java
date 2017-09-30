package apextechies.etodo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import apextechies.etodo.R;
import apextechies.etodo.activity.OffersList;
import apextechies.etodo.model.OffersModel;
import apextechies.etodo.network.Utilz;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 10 Jul 2017 at 12:08 PM
 */

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.MyViewHolder> {
	
	private List<OffersModel> myCartModels;
	private Context context;
	private OffersList cart;
	private int mycart_item;
	
	
	
	public class MyViewHolder extends RecyclerView.ViewHolder {
		public TextView cat_name, cat_location,distanceinkm;
		
		public MyViewHolder(View view) {
			super(view);
			cat_name = (TextView) view.findViewById(R.id.cat_name);
			cat_location = (TextView) view.findViewById(R.id.cat_location);
			distanceinkm = (TextView) view.findViewById(R.id.distanceinkm);
		}
	}
	
	
	public ShopListAdapter(Context context, List<OffersModel> myCartModels, OffersList cart) {
		this.context = context;
		this.myCartModels = myCartModels;
		this.cart = cart;
	}
	
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
		    .inflate(R.layout.offerlist_item, parent, false);
		
		return new MyViewHolder(itemView);
	}
	
	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position) {
		final OffersModel sa = myCartModels.get(position);
		holder.cat_name.setTypeface(Utilz.font(context, "bold"));
		holder.cat_location.setTypeface(Utilz.font(context, "regular"));
		holder.distanceinkm.setTypeface(Utilz.font(context, "regular"));
		
		holder.cat_name.setText(sa.getShop_name());
		holder.distanceinkm.setText(""+sa.getDistance());
		
		String text = "";
		
		if (text.equalsIgnoreCase("")) {
			text = myCartModels.get(position).getLocation();
		} else {
			text = text + "," + myCartModels.get(position).getLocation();
		}
		
		
		
		holder.cat_location.append(text);
		
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cart.sendpos(position);
			}
		});
		
	}
	
	@Override
	public int getItemCount() {
		return myCartModels.size();
	}
	
}
