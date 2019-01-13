package apextechies.etodo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import apextechies.etodo.R;
import apextechies.etodo.adapter.TimingAdapter;
import apextechies.etodo.common.ClsGeneral;
import apextechies.etodo.model.OfferBannerDesc;
import apextechies.etodo.model.OffersModel;
import apextechies.etodo.model.TimingModel;
import apextechies.etodo.network.Utilz;
import io.fabric.sdk.android.Fabric;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 05 Jul 2017 at 6:51 PM
 */

public class OfferShopDescription extends AppCompatActivity implements View.OnClickListener {
    private static ImageView mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private double latti, longi;
    private TextView validitytext, validontext;
    private TextView photocount, websitelink;
    private RecyclerView openclose_rec;
    int daycount = 0;
    private RelativeLayout toprel;
    private TextView toolbartext;
    private ArrayList<OffersModel> sendList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.description);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = OfferShopDescription.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(OfferShopDescription.this, R.color.colorPrimaryDark));

        }

        sendList = getIntent().getParcelableArrayListExtra("list");

        toprel = (RelativeLayout) findViewById(R.id.toprel);
        toprel.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                daycount = 6;
                break;

            case Calendar.MONDAY:
                daycount = 0;
                break;

            case Calendar.TUESDAY:
                daycount = 1;
                break;
            case Calendar.WEDNESDAY:
                daycount = 2;
                break;
            case Calendar.THURSDAY:
                daycount = 3;
                break;
            case Calendar.FRIDAY:
                daycount = 4;
                break;
            case Calendar.SATURDAY:
                daycount = 5;
                break;
        }

        initToolBar();
        domapping();
    }

    public void initToolBar() {
        validontext = (TextView) findViewById(R.id.validontext);
        toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setTypeface(Utilz.font(OfferShopDescription.this, "medium"));
        toolbartext.setText(sendList.get(0).getShopName());
        findViewById(R.id.backimage).setOnClickListener(this);
        validitytext = (TextView) findViewById(R.id.validitytext);
        validitytext.setText("Valid till : " + sendList.get(0).getValidTill());
    }

    private void domapping() {
        photocount = (TextView) findViewById(R.id.photocount);
        TextView shopname = (TextView) findViewById(R.id.shopname);
        TextView uploadedtime = (TextView) findViewById(R.id.uploadedtime);
        TextView location = (TextView) findViewById(R.id.location);
        TextView distanceinkm = (TextView) findViewById(R.id.distanceinkm);
        TextView daytext = (TextView) findViewById(R.id.daytext);
        findViewById(R.id.gotomap).setOnClickListener(this);
        websitelink = (TextView) findViewById(R.id.websitelink);
        mPager = (ImageView) findViewById(R.id.pager);
        findViewById(R.id.toprel).setOnClickListener(this);

        openclose_rec = (RecyclerView) findViewById(R.id.openclose_rec);
        openclose_rec.setHasFixedSize(true);
        openclose_rec.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OfferShopDescription.this);
        openclose_rec.setLayoutManager(mLayoutManager);
        openclose_rec.setItemAnimator(new DefaultItemAnimator());

        photocount.setTypeface(Utilz.font(OfferShopDescription.this, "medium"));
        shopname.setTypeface(Utilz.font(OfferShopDescription.this, "bold"));
        validontext.setTypeface(Utilz.font(OfferShopDescription.this, "bold"));
        location.setTypeface(Utilz.font(OfferShopDescription.this, "regular"));
        distanceinkm.setTypeface(Utilz.font(OfferShopDescription.this, "regular"));
        uploadedtime.setTypeface(Utilz.font(OfferShopDescription.this, "medium"));
        validitytext.setTypeface(Utilz.font(OfferShopDescription.this, "medium"));
        websitelink.setTypeface(Utilz.font(OfferShopDescription.this, "medium"));
        daytext.setTypeface(Utilz.font(OfferShopDescription.this, "bold"));

        location.setOnClickListener(this);
        websitelink.setOnClickListener(this);

        Picasso.with(OfferShopDescription.this).load(sendList.get(0).getShopBanner()).into(mPager);

        try {
            latti = Double.parseDouble(ClsGeneral.getPreferences(OfferShopDescription.this, ClsGeneral.LATITUTE));
            longi = Double.parseDouble(ClsGeneral.getPreferences(OfferShopDescription.this, ClsGeneral.LONGITUTE));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        shopname.setText(sendList.get(0).getShopName());
        toolbartext.setText(sendList.get(0).getShopName());

        location.setText(sendList.get(0).getShopLocation());
        distanceinkm.setText("" + Utilz.calculatedistanceinkm(latti, longi, sendList.get(0).getShopLatitute(),
                sendList.get(0).getShopLongitute()) + " km");

        websitelink.setText(sendList.get(0).getShopWebsiteUrl());


        setdaysapi();
    }

    private void setdaysapi() {
        ArrayList<TimingModel> timingModels = new ArrayList<>();
        timingModels.add(new TimingModel("Monday", sendList.get(0).getMondayOpen(), (sendList.get(0).getMondayClose())));
        timingModels.add(new TimingModel("Tuesday", sendList.get(0).getTuesdayOpen(), (sendList.get(0).getTuesdayClose())));
        timingModels.add(new TimingModel("Wednesday", sendList.get(0).getWednesOpen(), (sendList.get(0).getWednesClose())));
        timingModels.add(new TimingModel("Thursday", sendList.get(0).getThursOpen(), (sendList.get(0).getThursClose())));
        timingModels.add(new TimingModel("Friday", sendList.get(0).getFridayOpen(), (sendList.get(0).getFridayClose())));
        timingModels.add(new TimingModel("Saturday", sendList.get(0).getSaturdayOpen(), (sendList.get(0).getSaturdayClose())));
        timingModels.add(new TimingModel("Sunday", sendList.get(0).getMondayOpen(), (sendList.get(0).getMondayClose())));
        if (timingModels != null && timingModels.size() > 0) {
            for (int i = 0; i < timingModels.size(); i++) {
                TimingModel model = timingModels.get(i);
                if (i == daycount) {
                    model.setTypefacetype("bold");
                } else {
                    model.setTypefacetype("regular");
                }
            }
            if (timingModels != null && timingModels.size() > 0) {
                TimingAdapter timingAdapter = new TimingAdapter(OfferShopDescription.this, timingModels, daycount);
                openclose_rec.setAdapter(timingAdapter);
            }
            if (timingModels == null) {
                LinearLayout timinlayout = (LinearLayout) findViewById(R.id.timinlayout);
                timinlayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location:

                break;
            case R.id.gotomap:
                LatLng start_latlong = new LatLng(latti, longi);
                LatLng end_latlng = new LatLng(Double.parseDouble(sendList.get(0).getShopLatitute()), Double.parseDouble(sendList.get(0).getShopLongitute()));
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + String.valueOf(start_latlong.latitude) + "," + String.valueOf(start_latlong.longitude) + "&daddr=" + String.valueOf(end_latlng.latitude) + "," + String.valueOf(end_latlng.longitude)));
                startActivity(intent);


                break;
            case R.id.backimage:
                finish();
                break;
            case R.id.websitelink:
                try {
                    String url = sendList.get(0).getShopWebsiteUrl();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    public void sendpos() {
        try {
            ArrayList<OfferBannerDesc> catBanners = new ArrayList<>();
            catBanners.add(new OfferBannerDesc(sendList.get(0).getShopBanner(), "", "", ""));
            Intent intent = new Intent(OfferShopDescription.this, FullImageActivity.class);
            intent.putParcelableArrayListExtra("list", catBanners);
            intent.putExtra("pos", "" + currentPage);
            startActivity(intent);
        } catch (Exception e) {

        }
    }


}
