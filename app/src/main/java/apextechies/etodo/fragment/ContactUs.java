package apextechies.etodo.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import apextechies.etodo.R;
import apextechies.etodo.network.Utilz;

/**
 * Created by Admin on 08-05-2017.
 */

public class ContactUs extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ContactUs.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(ContactUs.this, R.color.colorAccent));
        }
        domappin();
    }

    private void domappin() {
        TextView name = (TextView)findViewById(R.id.name);
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        TextView phone = (TextView)findViewById(R.id.phone);
        TextView alternatenumber = (TextView)findViewById(R.id.alternatenumber);
        TextView emailtext = (TextView)findViewById(R.id.emailtext);
        TextView email = (TextView)findViewById(R.id.email);
        TextView locationtext = (TextView)findViewById(R.id.locationtext);
        TextView location = (TextView)findViewById(R.id.location);
        TextView websitetext = (TextView)findViewById(R.id.websitetext);


        phone.setOnClickListener(this);
        alternatenumber.setOnClickListener(this);
        email.setOnClickListener(this);
        location.setOnClickListener(this);
        websitetext.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);

        name.setTypeface(Utilz.font(ContactUs.this,"bold"));
        websitetext.setTypeface(Utilz.font(ContactUs.this,"medium"));
        textView3.setTypeface(Utilz.font(ContactUs.this,"medium"));
        phone.setTypeface(Utilz.font(ContactUs.this,"medium"));
        alternatenumber.setTypeface(Utilz.font(ContactUs.this,"medium"));
        emailtext.setTypeface(Utilz.font(ContactUs.this,"medium"));
        email.setTypeface(Utilz.font(ContactUs.this,"medium"));
        locationtext.setTypeface(Utilz.font(ContactUs.this,"medium"));
        location.setTypeface(Utilz.font(ContactUs.this,"medium"));
        websitetext.setTypeface(Utilz.font(ContactUs.this,"medium"));

        name.setText("Cotact Us");

        mapinitialize();
    }


    private void mapinitialize() {
        try

        {
           MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(23.7867885,86.4185349);
       /* mMap.addMarker(new
                MarkerOptions().position(latLng).title("eWebbazar Prv. Ltd"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));*/

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(23.7867885,86.4185349));
        markerOptions.title("eWebbazar Prv. Ltd");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.alternatenumber:
                try {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+"7549098888"));
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(ContactUs.this, "Phone facility not available on your device", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.phone:
                try {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+"7549097777"));
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(ContactUs.this, "Phone facility not available on your device", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.email:
                try {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","support@websspider.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
                catch (Exception e){
                    Toast.makeText(ContactUs.this, "Phone facility not available on your device", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.websitelink:
                try {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.websspider.com/"));
                startActivity(i);
                }
                catch (Exception e){
                    Toast.makeText(ContactUs.this, "", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}