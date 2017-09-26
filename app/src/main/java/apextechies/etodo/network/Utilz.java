package apextechies.etodo.network;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import apextechies.etodo.R;


public class Utilz {

	public static String[] months = { "Jan.", "Feb.", "Mar.", "Apr.", "May.",
			"Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec." };

	static String[] fullMonthName = { "January", "February", "March", "April",
			"May", "June", "July", "August", "September", "October",
			"November", "December" };

	public static String dateFromAdapter = null;
	public static final int HTTP_TIMEOUT = 60 * 1000; // milliseconds

	private static HttpClient mHttpClient;

	private static HttpClient getHttpClient() {
		if (mHttpClient == null) {
			mHttpClient = new DefaultHttpClient();
			final HttpParams params = mHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
		}
		return mHttpClient;

	}

	public static String executeHttpPost(String url, ArrayList<NameValuePair> postParameters) throws Exception {
		BufferedReader in = null;
		try {
			HttpClient client = getHttpClient();
			HttpPost request = new HttpPost(url);
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();

			String result = sb.toString();
			return result;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	public static String executeHttpGet(String url) throws Exception {
		BufferedReader in = null;
		try {
			HttpClient client = getHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();

			String result = sb.toString();
			return result;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static boolean isInternetConnected(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}





	public static boolean isValidEmail1(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}

	public static void hideKeyboard(Activity activity) {
		try {
			InputMethodManager inputManager = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			// check if no view has focus:
			View view = activity.getCurrentFocus();
			if (view != null) {
				inputManager.hideSoftInputFromWindow(view.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {
			// Ignore exceptions if any
			Log.e("KeyBoardUtil", e.toString(), e);
		}
	}

	public static String addZero(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	public static int getDateFromString(String dateStr) {
		int date = 0;
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date parsedDate = DATE_FORMAT.parse(dateStr);
			date = parsedDate.getDate();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}



	public static String ConvertDate(String dateStr) {
		String date = null;
		try {
			String[] input = dateStr.split("-");
			int getYear = Integer.valueOf(input[0]);
			int getMonth = Integer.valueOf(input[1]);
			int getDay = Integer.valueOf(input[2]);
			date = addZero(getDay) + "/" + addZero(getMonth) + "/" + getYear;
		} catch (Exception e) {

		}
		return date;
	}

	public static String getOnlyMonthDate(String dateStr) {
		String date = null;
		try {
			String[] input = dateStr.split("-");
			int getYear = Integer.valueOf(input[0]);
			int getMonth = Integer.valueOf(input[1]);
			// int getDay = Integer.valueOf(input[2]);
			date = fullMonthName[getMonth - 1] + ", " + getYear;
		} catch (Exception e) {

		}
		return date;
	}

	public static String getOnlyMonth(String dateStr) {
		String date = null;
		try {
			String[] input = dateStr.split("-");
			int getYear = Integer.valueOf(input[0]);
			int getMonth = Integer.valueOf(input[1]);
			// int getDay = Integer.valueOf(input[2]);
			if (getMonth == 0) {
				getMonth = 1;
			}
			date = fullMonthName[getMonth - 1];
		} catch (Exception e) {

		}

		return date;
	}

	public static String AddSubScriptToDate(String dateStr) {

		String date = null;
		try {
			String[] input = dateStr.split("-");
			int getYear = Integer.valueOf(input[0]);
			int getMonth = Integer.valueOf(input[1]);
			int getDay = Integer.valueOf(input[2]);
			date = addZero(getDay) + "," + fullMonthName[getMonth - 1] + " "
					+ getYear;
		} catch (Exception e) {

		}
		return date;

	}

	public static boolean isTodaySession(String sessionDate) {
		Date d = new Date();
		String dat = d.toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(Date.parse(dat));

		// String dateByUser = dayOfMonth + "/" + (monthOfYear + 1) + "/" +
		// year;
		Date CURRENTDATE = null, SESSIONDATE = null;

		try {
			CURRENTDATE = sdf.parse(currentDate);
			SESSIONDATE = sdf.parse(sessionDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (CURRENTDATE.compareTo(SESSIONDATE) == 0) {
			return true;
		} else {
			return false;
		}
	}


	public static Typeface font(Context context, String fonttype) {
		Typeface typeface = null;
		if (fonttype.equalsIgnoreCase("black"))
		{
			typeface =Typeface.createFromAsset(context.getAssets(),
					context.getResources().getString(R.string.black_font));
		}
		else if (fonttype.equalsIgnoreCase("regular"))
		{
			typeface =Typeface.createFromAsset(context.getAssets(),
					context.getResources().getString(R.string.regular_font));
		}
		else if (fonttype.equalsIgnoreCase("medium"))
		{
			typeface =Typeface.createFromAsset(context.getAssets(),
					context.getResources().getString(R.string.medium_font));
		}
		else if (fonttype.equalsIgnoreCase("bold"))
		{
			typeface =Typeface.createFromAsset(context.getAssets(),
					context.getResources().getString(R.string.bold_font));
		}
		else if (fonttype.equalsIgnoreCase("blackitalic"))
		{
			typeface =Typeface.createFromAsset(context.getAssets(),
					context.getResources().getString(R.string.black_italic_font));
		}

		else if (fonttype.equalsIgnoreCase("light"))
		{
			typeface =Typeface.createFromAsset(context.getAssets(),
					context.getResources().getString(R.string.light_font));
		}
		else if (fonttype.equalsIgnoreCase("italic"))
		{
			typeface =Typeface.createFromAsset(context.getAssets(),
					context.getResources().getString(R.string.italic_font));
		}
		else if (fonttype.equalsIgnoreCase("thin"))
		{
			typeface =Typeface.createFromAsset(context.getAssets(),
					context.getResources().getString(R.string.thin_font));
		}
		return typeface;
	}
}
