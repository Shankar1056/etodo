package apextechies.etodo.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import apextechies.etodo.LocationUtil.PermissionUtils;
import apextechies.etodo.R;
import apextechies.etodo.adapter.CustomPagerAdapter;
import apextechies.etodo.adapter.DotsAdapter;
import apextechies.etodo.adapter.HomeGridAdapter;
import apextechies.etodo.common.AddressResolverService;
import apextechies.etodo.common.ClsGeneral;
import apextechies.etodo.common.ExpandableHeightGridView;
import apextechies.etodo.fragment.AboutUs;
import apextechies.etodo.fragment.ContactUs;
import apextechies.etodo.fragment.Privacy;
import apextechies.etodo.fragment.TermsConditions;
import apextechies.etodo.model.BannerImageModel;
import apextechies.etodo.model.HomeCategory;
import apextechies.etodo.network.Download_web;
import apextechies.etodo.network.OnTaskCompleted;
import apextechies.etodo.network.Utilz;
import apextechies.etodo.scroll.BaseActivity;
import apextechies.etodo.scroll.ObservableScrollView;
import apextechies.etodo.scroll.ObservableScrollViewCallbacks;
import apextechies.etodo.scroll.ScrollState;
import apextechies.etodo.scroll.ScrollUtils;
import apextechies.etodo.tooltip.Tooltip;
import apextechies.etodo.webservices.WebServices;
import io.fabric.sdk.android.Fabric;

import static apextechies.etodo.activity.SearchAddressGooglePlacesActivity.REQUEST_LOCATION;

public class MainActivity extends BaseActivity implements OnConnectionFailedListener, View.OnClickListener,
        ConnectionCallbacks, OnRequestPermissionsResultCallback, PermissionUtils.PermissionResultCallback,
        ObservableScrollViewCallbacks, SwipeRefreshLayout.OnRefreshListener,LocationListener {

    private TextView toolbartext;
    private RelativeLayout leftdrawer;
    private DrawerLayout drawer;
    private ArrayList<HomeCategory> homeCategories = new ArrayList<>();
    private ExpandableHeightGridView categoryGrid;
    private HomeGridAdapter homeGridAdapter;
    private TextView share, notification, contactus, about, privacy, termsconition;
    private LinearLayout sharelayout, notificationlayout, contactlayout, aboutlayout, privacylayout, termsconitionlayout;
    ImageView shareimage, notificationimage, contactimage, aboutimage, privacyimage, termsconitionimage;
    private SwipeRefreshLayout expandable_list_swipe;

    //Linked
    private static final String host = "api.linkedin.com";
    private static final String topCardUrl = "https://" + host + "/v1/people/~:" +
            "(email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))";
    private ProgressDialog progress;
    private EditText serach;
    private GoogleApiClient mGoogleApiClient;

    private View mHeaderView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mBaseTranslationY;
    LinearLayout drawerlist;

    //view pager
    private ViewPager mViewPager;
    private CustomPagerAdapter mCustomPagerAdapter;
    private RecyclerView dots;
    private DotsAdapter dotsAdapter;
    private int previousPage;
    private int previousState;
    private boolean geasturesEnable;

    //get current location address
    private AddressResultReceiver mResultReceiver;

    // LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();

    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    private Location mLastLocation;

    // Google client to interact with Google API

    double latitude;
    double longitude;
    protected LocationManager locationManager;
    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;

    // list of permissions

    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;

    boolean isPermissionGranted;
    private Tooltip.ClosePolicy mClosePolicy = Tooltip.ClosePolicy.TOUCH_ANYWHERE_CONSUME;
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        chkpermission();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = MainActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
        }
        mResultReceiver = new AddressResultReceiver(new Handler());
        domapping();
    }

    private void chkpermission() {

        permissionUtils = new PermissionUtils(MainActivity.this);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionUtils.check_permission(permissions, "Need GPS permission for getting your location", 1);

    }

    private void domapping() {
        expandable_list_swipe = (SwipeRefreshLayout) findViewById(R.id.expandable_list_swipe);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mHeaderView = findViewById(R.id.header);
        ViewCompat.setElevation(mHeaderView, getResources().getDimension(R.dimen.toolbar_elevation));
        mToolbarView = findViewById(R.id.toolbar);

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        toolbartext = (TextView) findViewById(R.id.toolbartext);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftdrawer = (RelativeLayout) findViewById(R.id.leftdrawer);
        drawerlist = (LinearLayout) findViewById(R.id.drawerlist);

        dots = (RecyclerView) findViewById(R.id.dots);
        mViewPager = (ViewPager) findViewById(R.id.pager_zoom);


        TextView name = (TextView) findViewById(R.id.name);
        TextView number = (TextView) findViewById(R.id.number);
        share = (TextView) findViewById(R.id.share);
        notification = (TextView) findViewById(R.id.notification);
        contactus = (TextView) findViewById(R.id.contactus);
        about = (TextView) findViewById(R.id.about);
        privacy = (TextView) findViewById(R.id.privacy);
        termsconition = (TextView) findViewById(R.id.termsconition);
        TextView cancellation = (TextView) findViewById(R.id.cancellation);
        TextView travellayout = (TextView) findViewById(R.id.travellayout);
        TextView educationlayout = (TextView) findViewById(R.id.educationlayout);
        TextView shoppinglayout = (TextView) findViewById(R.id.shoppinglayout);
        TextView hotdeallayout = (TextView) findViewById(R.id.hotdeallayout);
        ImageView feedback = (ImageView)findViewById(R.id.feedback);

        findViewById(R.id.profile).setOnClickListener(this);
        serach = (EditText) findViewById(R.id.serach);

        name.setOnClickListener(this);
        share.setOnClickListener(this);
        notification.setOnClickListener(this);
        contactus.setOnClickListener(this);
        about.setOnClickListener(this);
        privacy.setOnClickListener(this);
        termsconition.setOnClickListener(this);
        cancellation.setOnClickListener(this);
        travellayout.setOnClickListener(this);
        educationlayout.setOnClickListener(this);
        hotdeallayout.setOnClickListener(this);
        drawerlist.setOnClickListener(this);
        shoppinglayout.setOnClickListener(this);
        feedback.setOnClickListener(this);

        toolbartext.setTypeface(Utilz.font(MainActivity.this, "medium"));
        serach.setTypeface(Utilz.font(MainActivity.this, "medium"));
        name.setTypeface(Utilz.font(MainActivity.this, "bold"));
        share.setTypeface(Utilz.font(MainActivity.this, "medium"));
        number.setTypeface(Utilz.font(MainActivity.this, "medium"));
        notification.setTypeface(Utilz.font(MainActivity.this, "medium"));
        contactus.setTypeface(Utilz.font(MainActivity.this, "medium"));
        about.setTypeface(Utilz.font(MainActivity.this, "medium"));
        privacy.setTypeface(Utilz.font(MainActivity.this, "medium"));
        termsconition.setTypeface(Utilz.font(MainActivity.this, "medium"));
        cancellation.setTypeface(Utilz.font(MainActivity.this, "medium"));
        travellayout.setTypeface(Utilz.font(MainActivity.this, "medium"));
        shoppinglayout.setTypeface(Utilz.font(MainActivity.this, "medium"));
        educationlayout.setTypeface(Utilz.font(MainActivity.this, "medium"));
        hotdeallayout.setTypeface(Utilz.font(MainActivity.this, "medium"));

        name.setText(ClsGeneral.getPreferences(MainActivity.this, ClsGeneral.USER_NAME));
        number.setText(ClsGeneral.getPreferences(MainActivity.this, ClsGeneral.USER_PHONENUMBER));


        categoryGrid = (ExpandableHeightGridView) findViewById(R.id.categoryGrid);


        categoryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, HomeSubCategory.class);
                intent.putExtra("id", homeCategories.get(position).getCat_id());
                intent.putExtra("name", homeCategories.get(position).getCat_name());
                startActivity(intent);
            }
        });


        leftdrawer.setOnClickListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        if (ClsGeneral.getPreferences(MainActivity.this, getString(R.string.loginType)).equalsIgnoreCase(getString(R.string.linked))) {

            linkededinApiHelper();
        }

        toolbartext.setOnClickListener(this);

        expandable_list_swipe.setOnRefreshListener(this);
    
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Tooltip.make(MainActivity.this,
            new Tooltip.Builder()
                .anchor(feedback, Tooltip.Gravity.BOTTOM)
                .closePolicy(mClosePolicy, 40000)
                .text("Offers Near You")
                .withArrow(true)
                .withOverlay(false)
                .maxWidth((int) (metrics.widthPixels / 2.5))
                //.withCallback(getActivity().getApplicationContext())
                .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                .build()
        ).show();
        
        
        if (Utilz.isInternetConnected(MainActivity.this))
            expandable_list_swipe.setRefreshing(true);

        searchimplement();
        checkgoogleplayservice();
        getlocation();
        callapi();

        callmethodeveryfivesecond();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.e("onPageScrool","position "+position+" , positionoffset "+positionOffset+" , positionoffsetPixels"+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("onPageSelected", "position " + position);
                try {
                    if (previousPage != position) {
                        dotsAdapter.setSelected(position);
                        dotsAdapter.notifyDataSetChanged();
                    }
                    previousPage = position;
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("onScrollStateChanged", "state " + state);

               // previousState = state;

            }
        });

        changebackground(share, sharelayout, shareimage, R.mipmap.share, false);

    }

    private void callmethodeveryfivesecond() {
        final Handler ha = new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (toolbartext.getText().toString().equalsIgnoreCase(getResources().getString(R.string.fetchinglocation))) {
                    getLocation();
                }

                ha.postDelayed(this, 2000);
            }
        }, 2000);
    }

    private void checkgoogleplayservice() {

        // check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();

        }
    }

    private void getlocation() {

        //getLocation();


    }


    private void searchimplement() {
        serach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (homeGridAdapter != null) {
                    String text = serach.getText().toString().toLowerCase(Locale.getDefault());
                    homeGridAdapter.filter(text);
                }
            }
        });
    }

    private void callapi() {

        try {
            Download_web web = new Download_web(MainActivity.this, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(String response) {
                    new GetCategory().execute(WebServices.CATEGORY);

                    try {
                        String image = null, slider_id = null, slider_status = null, slider_coupanid = null;
                        ArrayList<BannerImageModel> models = new ArrayList<>();
                        JSONArray jsonArray = new JSONObject(response).getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1.has("slider_id")) {
                                slider_id = jsonObject1.getString("slider_id");
                            }
                            if (jsonObject1.has("slider_image")) {
                                image = jsonObject1.getString("slider_image");
                            }
                            if (jsonObject1.has("slider_status")) {
                                slider_status = jsonObject1.getString("slider_status");
                            }
                            if (jsonObject1.has("slider_coupanid")) {
                                slider_coupanid = jsonObject1.getString("slider_coupanid");
                            }

                            if (slider_status.equalsIgnoreCase("1")) {
                                models.add(new BannerImageModel(slider_id, image, slider_status, slider_coupanid));
                            }
                        }
                        mCustomPagerAdapter = new CustomPagerAdapter();
                        mCustomPagerAdapter.setData(models, MainActivity.this, MainActivity.this);
                        mCustomPagerAdapter.isDynamic(true);
                        mCustomPagerAdapter.setVP(mViewPager);
                        mViewPager.setAdapter(mCustomPagerAdapter);
                        geasturesEnable = true;
                        if (models.size() > 1) {
                            //Dots

                            dots.setHasFixedSize(true);
                            dots.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            dotsAdapter = new DotsAdapter(models.size());
                            dots.setAdapter(dotsAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            web.setReqType(true);
            web.execute(WebServices.BANNERSLIDER);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void linkededinApiHelper() {
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(MainActivity.this, topCardUrl, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse result) {
                try {

                    setprofile(result.getResponseDataAsJson());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onApiError(LIApiError error) {
            }
        });
    }

    public void setprofile(JSONObject response) {

        try {
            JSONObject res = response;

            /*user_email.setText(response.get("emailAddress").toString());
            user_name.setText(response.get("formattedName").toString());

            Picasso.with(this).load(response.getString("pictureUrl"))
                    .into(profile_picture);
*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leftdrawer:
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
                break;
            
            case R.id.travellayout:
                openwebsite("https://play.google.com/store/apps/details?id=com.musafirbazar.app.musafirbazar");
                break;
            case R.id.educationlayout:
                openwebsite("https://play.google.com/store/apps/details?id=com.apextechies.eretort");
                break;
            case R.id.shoppinglayout:
                openwebsite("https://ecommerce.musafirbazar.com/");
                break;
            case R.id.hotdeallayout:
                openwebsite("https://www.musafirbazar.com/");
              //  startActivity(new Intent(MainActivity.this,OffersList.class));
                break;
            case R.id.drawerlist:

                break;

            case R.id.share:
                changebackground(share, sharelayout, shareimage, R.mipmap.share, true);
                try {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=apextechies.etodo&hl=en");
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "Share Webspider"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.notification:
                changebackground(notification, notificationlayout, notificationimage, R.mipmap.notification, true);
                drawer.closeDrawer(GravityCompat.END);
                Toast.makeText(this, "Comin Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about:
                changebackground(about, aboutlayout, aboutimage, R.mipmap.about, true);
                drawer.closeDrawer(GravityCompat.END);
                startActivity(new Intent(MainActivity.this, AboutUs.class));
                break;
            case R.id.contactus:
                changebackground(contactus, contactlayout, contactimage, R.mipmap.contact, true);
                try {
                    drawer.closeDrawer(GravityCompat.END);
                    startActivity(new Intent(MainActivity.this, ContactUs.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.privacy:
                changebackground(privacy, privacylayout, privacyimage, R.mipmap.privacy, true);
                drawer.closeDrawer(GravityCompat.END);
                startActivity(new Intent(MainActivity.this, Privacy.class));
                break;
            case R.id.termsconition:
                changebackground(termsconition, termsconitionlayout, termsconitionimage, R.mipmap.condition, true);
                drawer.closeDrawer(GravityCompat.END);
                startActivity(new Intent(MainActivity.this, TermsConditions.class));
                break;
            case R.id.cancellation:
                drawer.closeDrawer(GravityCompat.END);
                Toast.makeText(this, "cancellation", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbartext:
                startActivity(new Intent(MainActivity.this, SearchAddressGooglePlacesActivity.class));
                break;
            case R.id.profile:
                drawer.closeDrawer(GravityCompat.END);
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
            case R.id.feedback:
              //  startActivity(new Intent(MainActivity.this, FeedBackForm.class));
                startActivity(new Intent(MainActivity.this,OffersList.class));
                break;
        }

    }

    private void changebackground(TextView tv, LinearLayout ll, ImageView iv, int color, boolean b) {


        sharelayout = (LinearLayout) findViewById(R.id.sharelayout);
        notificationlayout = (LinearLayout) findViewById(R.id.notificationlayout);
        contactlayout = (LinearLayout) findViewById(R.id.contactlayout);
        aboutlayout = (LinearLayout) findViewById(R.id.aboutlayout);
        privacylayout = (LinearLayout) findViewById(R.id.privacylayout);
        termsconitionlayout = (LinearLayout) findViewById(R.id.termsconitionlayout);

        shareimage = (ImageView) findViewById(R.id.shareimage);
        notificationimage = (ImageView) findViewById(R.id.notificationimage);
        contactimage = (ImageView) findViewById(R.id.contactimage);
        aboutimage = (ImageView) findViewById(R.id.aboutimage);
        privacyimage = (ImageView) findViewById(R.id.privacyimage);
        termsconitionimage = (ImageView) findViewById(R.id.termsconitionimage);

        sharelayout.setBackgroundColor(getResources().getColor(R.color.white));
        notificationlayout.setBackgroundColor(getResources().getColor(R.color.white));
        contactlayout.setBackgroundColor(getResources().getColor(R.color.white));
        aboutlayout.setBackgroundColor(getResources().getColor(R.color.white));
        privacylayout.setBackgroundColor(getResources().getColor(R.color.white));
        termsconitionlayout.setBackgroundColor(getResources().getColor(R.color.white));

        shareimage.setBackgroundResource(R.mipmap.share_white);
        notificationimage.setBackgroundResource(R.mipmap.notification_white);
        contactimage.setBackgroundResource(R.mipmap.contact_white);
        aboutimage.setBackgroundResource(R.mipmap.about_white);
        privacyimage.setBackgroundResource(R.mipmap.privacy_white);
        termsconitionimage.setBackgroundResource(R.mipmap.condition_white);

        share.setTextColor(getResources().getColor(R.color.leftmenutext_color));
        notification.setTextColor(getResources().getColor(R.color.leftmenutext_color));
        contactus.setTextColor(getResources().getColor(R.color.leftmenutext_color));
        about.setTextColor(getResources().getColor(R.color.leftmenutext_color));
        privacy.setTextColor(getResources().getColor(R.color.leftmenutext_color));
        termsconition.setTextColor(getResources().getColor(R.color.leftmenutext_color));

        if (b) {
            ll.setBackgroundColor(getResources().getColor(R.color.app_back));
            iv.setBackgroundResource(color);
            tv.setTextColor(getResources().getColor(R.color.red));
        }


    }

    private void openwebsite(String url) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        if (Utilz.isInternetConnected(MainActivity.this))
            expandable_list_swipe.setRefreshing(true);
        callapi();
    }

    public void sendos() {
        startActivity(new Intent(MainActivity.this,OffersList.class));

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private class GetCategory extends AsyncTask<String, Void, String> {
        String result1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            try {
                result1 = Utilz.executeHttpGet(params[0]);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result1;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (expandable_list_swipe != null)
                expandable_list_swipe.setRefreshing(false);
            if (s != null) {
                String cat_id = null, cat_name = null, cat_image = null, cat_status = null;

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String success = jsonObject.getString("success");
                    String success_msg = jsonObject.getString("success_message");
                    if (success.equalsIgnoreCase("true")) {
                        homeCategories.clear();


                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1.has("cat_id")) {
                                cat_id = jsonObject1.getString("cat_id");

                            }
                            if (jsonObject1.has("cat_name")) {
                                cat_name = jsonObject1.getString("cat_name");

                            }
                            if (jsonObject1.has("cat_image")) {
                                cat_image = jsonObject1.getString("cat_image");

                            }

                            if (jsonObject1.has("cat_status")) {
                                cat_status = jsonObject1.getString("cat_status");
                            }
                            if (cat_status.equalsIgnoreCase("1")) {
                                homeCategories.add(new HomeCategory(cat_id, cat_name, cat_image, cat_status, false, R
                                        .mipmap.red_back, 0, R.mipmap.white_back));
                            }

                        }

                        homeGridAdapter = new HomeGridAdapter(MainActivity.this, homeCategories);
                        categoryGrid.setAdapter(homeGridAdapter);
                        categoryGrid.setExpanded(true);


                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }


    /**
     * Method to display the location on UI
     */

    private void getLocation() {

        if (isPermissionGranted) {

            try {
              /*  mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    ClsGeneral.setPreferences(MainActivity.this, ClsGeneral.LATITUTE, ""+mLastLocation.getLatitude());
                    ClsGeneral.setPreferences(MainActivity.this, ClsGeneral.LONGITUTE, ""+mLastLocation.getLatitude());

                    startIntentService(mLastLocation);

                } else {
                }*/
                double currentLatitude = 0, currentLongitude = 0;
               // Location gpsLocation = getCurrentLocation(LocationManager.GPS_PROVIDER);
                mLastLocation = getCurrentLocation(LocationManager.GPS_PROVIDER);
                if (mLastLocation != null) {
                    currentLatitude = mLastLocation.getLatitude();
                    currentLongitude = mLastLocation.getLongitude();
                } else {
                  //  Location nwLocation = getCurrentLocation(LocationManager.NETWORK_PROVIDER);
                    mLastLocation = getCurrentLocation(LocationManager.NETWORK_PROVIDER);
                    if (mLastLocation != null) {
                        currentLatitude = mLastLocation.getLatitude();
                        currentLongitude = mLastLocation.getLongitude();
                    }
                }

                if (currentLatitude == 0.0 || currentLongitude == 0.0) {
                    showSettingsAlert();
                } else {
                    startIntentService(mLastLocation);
                    ClsGeneral.setPreferences(MainActivity.this,
                            ClsGeneral.LATITUTE, ""+currentLatitude);
                    ClsGeneral.setPreferences(MainActivity.this,
                            ClsGeneral.LONGITUTE, ""+currentLongitude);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }

    }
    private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS Settings");
        alertDialog.setCancelable(false);

        // Setting Dialog Message
        alertDialog.setMessage("Please help us determine your location to show businesess near you. Click on Settings and turn on. Thanks");

        // On pressing Settings button
        alertDialog.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.show();
    }
    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    public void getAddress() {

        Address locationAddress = getAddress(latitude, longitude);

        if (locationAddress != null) {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();

            String currentLocation;

            if (!TextUtils.isEmpty(address)) {
                currentLocation = address;

                if (!TextUtils.isEmpty(address1))
                    currentLocation += "\n" + address1;

                if (!TextUtils.isEmpty(city)) {
                    currentLocation += "\n" + city;


                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += " - " + postalCode;
                } else {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += "\n" + postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation += "\n" + state;

                if (!TextUtils.isEmpty(country))
                    currentLocation += "\n" + country;

                toolbartext.setText("" + address1 + "," + city);
            }

        }


    }

    /**
     * Creating google api client object
     */

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        //getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });


    }


    /**
     * Method to verify google play services on the device
     */

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        //getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        break;
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //checkPlayServices();

        String lat = ClsGeneral.getPreferences(MainActivity.this, ClsGeneral.LATITUTE);
        String lon = ClsGeneral.getPreferences(MainActivity.this, ClsGeneral.LONGITUTE);
        Location mLocation = new Location("");
        if ((lat != null && lat.length() > 0) && (lon != null && lon.length() > 0)) {
            latitude = Double.parseDouble(lat);
            longitude = Double.parseDouble(lon);
            //getAddress();
            mLocation.setLatitude(latitude);
            mLocation.setLongitude(longitude);
            startIntentService(mLocation);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (locationManager != null)
            locationManager.removeUpdates(this);
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


    // Permission check functions


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION", "GRANTED");
        isPermissionGranted = true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY", "GRANTED");
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION", "DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION", "NEVER ASK AGAIN");
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void startIntentService(Location mLocation) {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(this, AddressResolverService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(AddressResolverService.RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(AddressResolverService.LOCATION_DATA_EXTRA, mLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MapActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String addres = resultData.getString(AddressResolverService.LOCATION_DATA_AREA);
            if (addres.equalsIgnoreCase("")) {
                addres = resultData.getString(AddressResolverService.LOCATION_DATA_AREA);
            }
            String finladdress = resultData.getString(AddressResolverService.LOCATION_DATA_AREA);
            try {
                toolbartext.setText(finladdress);
            } catch (NullPointerException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }


    private Location getCurrentLocation(String provider) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        }
        Location location;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(provider)) {
            locationManager.requestLocationUpdates(provider, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, MainActivity.this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(provider);
                return location;
            }
        }
        return null;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (dragging) {
            int toolbarHeight = mToolbarView.getHeight();
            if (firstScroll) {
                float currentHeaderTranslationY = ViewHelper.getTranslationY(mHeaderView);
                if (-toolbarHeight < currentHeaderTranslationY) {
                    mBaseTranslationY = scrollY;
                }
            }
            float headerTranslationY = ScrollUtils.getFloat(-(scrollY - mBaseTranslationY), -toolbarHeight, 0);
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewHelper.setTranslationY(mHeaderView, headerTranslationY);
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        mBaseTranslationY = 0;

        if (scrollState == ScrollState.DOWN) {
            showToolbar();
        } else if (scrollState == ScrollState.UP) {
            int toolbarHeight = mToolbarView.getHeight();
            int scrollY = mScrollView.getCurrentScrollY();
            if (toolbarHeight <= scrollY) {
                hideToolbar();
            } else {
                showToolbar();
            }
        } else {
            // Even if onScrollChanged occurs without scrollY changing, toolbar should be adjusted
            if (!toolbarIsShown() && !toolbarIsHidden()) {
                // Toolbar is moving but doesn't know which to move:
                // you can change this to hideToolbar()
                showToolbar();
            }
        }
    }

    private boolean toolbarIsShown() {
        return ViewHelper.getTranslationY(mHeaderView) == 0;
    }

    private boolean toolbarIsHidden() {
        return ViewHelper.getTranslationY(mHeaderView) == -mToolbarView.getHeight();
    }

    private void showToolbar() {
        float headerTranslationY = ViewHelper.getTranslationY(mHeaderView);
        if (headerTranslationY != 0) {
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewPropertyAnimator.animate(mHeaderView).translationY(0).setDuration(200).start();
        }
    }

    private void hideToolbar() {
        float headerTranslationY = ViewHelper.getTranslationY(mHeaderView);
        int toolbarHeight = mToolbarView.getHeight();
        if (headerTranslationY != -toolbarHeight) {
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewPropertyAnimator.animate(mHeaderView).translationY(-toolbarHeight).setDuration(200).start();
        }
    }
}
