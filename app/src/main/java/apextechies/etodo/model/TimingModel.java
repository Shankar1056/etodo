package apextechies.etodo.model;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 09 Aug 2017 at 5:12 PM
 */

public class TimingModel {

	String day;
	String open_time;
	String close_time;
	String typefacetype;
	public TimingModel(String day, String open_time, String close_time)
	{
		this.day = day;
		this.open_time = open_time;
		this.close_time = close_time;
	}
	public String getDay() {return day;	}

	public String getOpen_time() {
		return open_time;
	}

	public String getClose_time() {
		return close_time;
	}

	public String getTypefacetype() {
		return typefacetype;
	}

	public void setTypefacetype(String typefacetype) {
		this.typefacetype = typefacetype;
	}


}
