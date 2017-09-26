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
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 01 Aug 2017 at 7:39 PM
 */

public class Privacy extends AppCompatActivity{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.privacy);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = Privacy.this.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(ContextCompat.getColor(Privacy.this, R.color.colorAccent));
		}

		TextView privacytext = (TextView)findViewById(R.id.privacytext);
		TextView name = (TextView)findViewById(R.id.name);
		privacytext.setTypeface(Utilz.font(Privacy.this,"medium"));
		name.setTypeface(Utilz.font(Privacy.this,"bold"));
		name.setText("Privacy");
		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
