package apextechies.etodo.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import apextechies.etodo.R;
import apextechies.etodo.adapter.ShopListAdapter;
import apextechies.etodo.common.ClsGeneral;
import apextechies.etodo.common.CustomItemAnimator;
import apextechies.etodo.model.OffersModel;
import apextechies.etodo.network.Download_web;
import apextechies.etodo.network.OnTaskCompleted;
import apextechies.etodo.network.Utilz;
import apextechies.etodo.webservices.WebServices;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 30 Sep 2017 at 12:12 PM
 */

public class OffersList extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView listrecycler;
    private ShopListAdapter adapter;
    private ArrayList<OffersModel> offerList = new ArrayList<>();
    private ArrayList<OffersModel> sendList = new ArrayList<>();
    double latti = 0.0, longi = 0.0;
    private SwipeRefreshLayout expandable_list_swipe;
    private  TextView totalcount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.productlist);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = OffersList.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(OffersList.this, R.color.colorPrimaryDark));

        }
        initwidget();
        getofferslist();
    }

    private void getofferslist() {

        try {
            latti = Double.parseDouble(ClsGeneral.getPreferences(OffersList.this, ClsGeneral.LATITUTE));
            longi = Double.parseDouble(ClsGeneral.getPreferences(OffersList.this, ClsGeneral.LONGITUTE));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Utilz.isInternetConnected(OffersList.this)) {
            callapi();
        }

    }

    private void callapi() {
        Download_web web = new Download_web(this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                if (!response.equals("")) {
                    try {
                        if (expandable_list_swipe != null)
                            expandable_list_swipe.setRefreshing(false);

                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("success");
                        if (status.equalsIgnoreCase("true")) {
                            offerList.clear();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                String id = jo.optString("id");
                                String shopId = jo.optString("shopId");
                                String shopOpenTime = jo.optString("shopOpenTime");
                                String shopCloseTime = jo.optString("shopCloseTime");
                                String day = jo.optString("day");
                                String categoryName = jo.optString("categoryName");
                                String shopName = jo.optString("shopName");
                                String shopBanner = jo.optString("shopBanner");
                                String shopLocation = jo.optString("shopLocation");
                                String shopAddress = jo.optString("shopAddress");
                                String shopLatitute = jo.optString("shopLatitute");
                                String shopLongitute = jo.optString("shopLongitute");
                                String shopWebsiteUrl = jo.optString("shopWebsiteUrl");
                                String mondayOpen = jo.optString("mondayOpen");
                                String mondayClose = jo.optString("mondayClose");
                                String tuesdayOpen = jo.optString("tuesdayOpen");
                                String tuesdayClose = jo.optString("tuesdayClose");
                                String wednesOpen = jo.optString("wednesOpen");
                                String wednesClose = jo.optString("wednesClose");
                                String thursOpen = jo.optString("thursOpen");
                                String thursClose = jo.optString("thursClose");
                                String fridayOpen = jo.optString("fridayOpen");
                                String fridayClose = jo.optString("fridayClose");
                                String saturdayOpen = jo.optString("saturdayOpen");
                                String saturdayClose = jo.optString("saturdayClose");
                                String validTill = jo.optString("validTill");
                                String dis = "" + Utilz.calculatedistanceinkm(latti, longi, shopLatitute, shopLongitute);
                                OffersModel offersModel = new OffersModel(id, shopId, shopOpenTime, shopCloseTime, day, categoryName, shopName,
                                        shopBanner, shopLocation, shopAddress, shopLatitute, shopLongitute, shopWebsiteUrl, Double.parseDouble(dis), mondayOpen
                                        , mondayClose, tuesdayOpen, tuesdayClose, wednesOpen, wednesClose, thursOpen, thursClose, fridayOpen, fridayClose, saturdayOpen, saturdayClose, validTill);
                                offerList.add(offersModel);
                                adapter.notifyDataSetChanged();
                            }
                            if (offerList.size() > 0) {

                                totalcount.setText("Total result found( " + offerList.size() + " )");

                            } else {
                                totalcount.setVisibility(View.GONE);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        web.setReqType(true);
        web.execute(WebServices.OFFERS);
    }

    private void initwidget() {
        expandable_list_swipe = (SwipeRefreshLayout) findViewById(R.id.expandable_list_swipe);
        totalcount = (TextView) findViewById(R.id.totalcount);
        listrecycler = (RecyclerView) findViewById(R.id.listrecycler);
        listrecycler.setLayoutManager(new LinearLayoutManager(this));
        listrecycler.setItemAnimator(new CustomItemAnimator());
        adapter = new ShopListAdapter(OffersList.this, offerList, OffersList.this);
        listrecycler.setAdapter(adapter);
        findViewById(R.id.back).setOnClickListener(this);
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText("Offers Near You");

        expandable_list_swipe.setOnRefreshListener(this);
        if (Utilz.isInternetConnected(OffersList.this))
            expandable_list_swipe.setRefreshing(true);
    }

    public void sendpos(int position) {
        OffersModel s = offerList.get(position);
        sendList.add(new OffersModel(s.getId(), s.getShopId(), s.getShopOpenTime(), s.getShopCloseTime(), s.getDay(), s.getCategoryName(),
                s.getShopName(), s.getShopBanner(), s.getShopLocation(), s.getShopAddress(), s.getShopLatitute(), s.getShopLongitute(),
                s.getShopWebsiteUrl(), s.getDistance(), s.getMondayOpen(), s.getMondayClose(), s.getTuesdayOpen(), s.getTuesdayClose(),
                s.getWednesOpen(), s.getWednesClose(), s.getThursOpen(), s.getThursClose(), s.getFridayOpen(), s.getFridayClose(),
                s.getSaturdayOpen(), s.getSaturdayClose(), s.getValidTill()));
        Intent intent = new Intent(OffersList.this, OfferShopDescription.class);
        intent.putParcelableArrayListExtra("list", sendList);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        if (Utilz.isInternetConnected(OffersList.this))
            expandable_list_swipe.setRefreshing(true);
        callapi();
    }
}
