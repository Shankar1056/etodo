package apextechies.etodo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import apextechies.etodo.R;
import apextechies.etodo.adapter.ShopListAdapter;
import apextechies.etodo.common.CustomItemAnimator;
import apextechies.etodo.model.OffersModel;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 30 Sep 2017 at 12:12 PM
 */

public class OffersList extends AppCompatActivity {
	
	private RecyclerView listrecycler;
	private  ShopListAdapter adapter;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productlist);
		initwidget();
	}
	
	private void initwidget() {
		
		listrecycler = (RecyclerView) findViewById(R.id.listrecycler);
		listrecycler.setLayoutManager(new LinearLayoutManager(this));
		listrecycler.setItemAnimator(new CustomItemAnimator());
		adapter = new ShopListAdapter(OffersList.this, new ArrayList<OffersModel>(),OffersList.this);
		listrecycler.setAdapter(adapter);
		
	}
	
	public void sendpos(int position) {
		
	}
}
