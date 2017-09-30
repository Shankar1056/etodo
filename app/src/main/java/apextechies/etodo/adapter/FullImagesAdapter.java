package apextechies.etodo.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import apextechies.etodo.fragment.FullImageFragment;
import apextechies.etodo.model.CatBanner;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 14 Jul 2017 at 7:49 PM
 */

public class FullImagesAdapter extends FragmentStatePagerAdapter {
	
	private ArrayList<CatBanner> propertyImagellist = new ArrayList<>();
	private final int total_length;
	private String pos;
	
	public FullImagesAdapter(FragmentManager fm, ArrayList<CatBanner> arrayList, int total_length, String pos) {
		super(fm);
		this.propertyImagellist = arrayList;
		this.total_length = total_length;
		this.pos = pos;
		
	}
	
	
	
	
	@Override
	
	public Fragment getItem(int position) {
		
		Fragment fragment = new FullImageFragment();
		Bundle args = new Bundle();
		
		
		args.putString("imageurl", propertyImagellist.get(position).getBanner_image());
//		  args.putString("name",propertyImagellist.get(position).getCategory());
		args.putString("pos", ""+position);
		args.putString("total_length", "" + propertyImagellist.size());
		args.putString("valid_till", propertyImagellist.get(position).getValid_till());
		fragment.setArguments(args);
		
		
		return fragment;
	}
	
	@Override
	public int getCount() {
		return propertyImagellist.size();
	}
	
	
}
