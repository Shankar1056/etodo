package apextechies.etodo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import apextechies.etodo.R;
import apextechies.etodo.common.ClsGeneral;
import apextechies.etodo.network.Download_web;
import apextechies.etodo.network.OnTaskCompleted;
import apextechies.etodo.network.Utilz;
import apextechies.etodo.webservices.WebServices;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 26 Sep 2017 at 5:32 PM
 */

public class FeedBackForm extends AppCompatActivity implements View.OnClickListener{
	private EditText edittextfeedback;
	private Button submit;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		domapping();
		
	}
	
	private void domapping() {
		
		findViewById(R.id.back).setOnClickListener(this);
		TextView toolbartext = (TextView)findViewById(R.id.toolbartext);
		edittextfeedback = (EditText)findViewById(R.id.edittextfeedback);
		submit = (Button)findViewById(R.id.submit);
		toolbartext.setText("Feedback");
		submit.setTypeface(Utilz.font(FeedBackForm.this,"bold"));
		submit.setOnClickListener(this);
		toolbartext.setTypeface(Utilz.font(FeedBackForm.this,"medium"));
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId())
		{
			case R.id.back:
				finish();
				break;
			case R.id.submit:
				if (edittextfeedback.getText().toString().trim().equalsIgnoreCase(""))
				{
					Toast.makeText(this, "your feedback please", Toast.LENGTH_SHORT).show();
					return;
				}
				else
				{
					savefeedback();
				}
				break;
		}
	}
	
	private void savefeedback() {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
		Download_web web = new Download_web(FeedBackForm.this, new OnTaskCompleted() {
			@Override
			public void onTaskCompleted(String response) {
				
				Log.e("Feedback Response :",response);
				Toast.makeText(FeedBackForm.this, "Thank You", Toast.LENGTH_SHORT).show();
				edittextfeedback.setText("");
				finish();
				
			}
		});
		
		nameValuePairs.add(new BasicNameValuePair("user_id", ClsGeneral.getPreferences(FeedBackForm.this, ClsGeneral
		    .USER_ID)));
		nameValuePairs.add(new BasicNameValuePair("comments", edittextfeedback.getText().toString()));
		web.setReqType(false);
		web.setData(nameValuePairs);
		web.execute(WebServices.FEEDBACK);
		
	}
}
