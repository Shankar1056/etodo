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
import apextechies.etodo.network.Utilz;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 01 Aug 2017 at 7:31 PM
 */

public class TermsConditions extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.termsconditions);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = TermsConditions.this.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(ContextCompat.getColor(TermsConditions.this, R.color.colorAccent));
		}

		TextView name = (TextView)findViewById(R.id.name);
		TextView termsconitiontext = (TextView)findViewById(R.id.termsconitiontext);

		name.setTypeface(Utilz.font(TermsConditions.this,"bold"));
		termsconitiontext.setTypeface(Utilz.font(TermsConditions.this,"medium"));

		name.setText("Terms and conditions");
		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
