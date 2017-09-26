package apextechies.etodo.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import apextechies.etodo.R;
import apextechies.etodo.activity.MainActivity;
import apextechies.etodo.network.Utilz;

/**
 * Created by Shankar on 5/10/2017.
 */

public class AboutUs extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = AboutUs.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(AboutUs.this, R.color.colorAccent));
        }

        domappin();
    }

    private void domappin() {
        TextView name = (TextView)findViewById(R.id.name);
        TextView abouttext = (TextView)findViewById(R.id.abouttext);

        name.setTypeface(Utilz.font(AboutUs.this,"bold"));
        abouttext.setTypeface(Utilz.font(AboutUs.this,"medium"));
        name.setText("About Us");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
