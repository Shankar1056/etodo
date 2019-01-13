package apextechies.etodo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 30 Sep 2017 at 12:21 PM
 */

public class OffersModel implements Parcelable{

	private String id,shopId,shopOpenTime,shopCloseTime,day,categoryName,shopName,shopBanner,shopLocation,shopAddress,shopLatitute,
			shopLongitute,shopWebsiteUrl,mondayOpen,mondayClose,tuesdayOpen,tuesdayClose,wednesOpen,wednesClose,thursOpen,thursClose,
			fridayOpen,fridayClose,saturdayOpen,saturdayClose,validTill;
	private Double distance;

	public OffersModel(String id, String shopId, String shopOpenTime, String shopCloseTime, String day, String categoryName,
					   String shopName, String shopBanner, String shopLocation, String shopAddress, String shopLatitute, String shopLongitute,
					   String shopWebsiteUrl,Double distance, String mondayOpen, String mondayClose, String tuesdayOpen, String tuesdayClose,
					   String wednesOpen,String wednesClose, String thursOpen, String thursClose, String fridayOpen, String fridayClose,
					   String saturdayOpen, String saturdayClose, String validTill)
	{
		this.id = id;
		this.shopId = shopId;
		this.shopOpenTime = shopOpenTime;
		this.shopCloseTime = shopCloseTime;
		this.day = day;
		this.categoryName = categoryName;
		this.shopName = shopName;
		this.shopBanner = shopBanner;
		this.shopLocation = shopLocation;
		this.shopAddress = shopAddress;
		this.shopLatitute = shopLatitute;
		this.shopLongitute = shopLongitute;
		this.shopWebsiteUrl = shopWebsiteUrl;
		this.distance = distance;
		this.mondayOpen = mondayOpen;
		this.mondayClose = mondayClose;
		this.tuesdayOpen = tuesdayOpen;
		this.tuesdayClose = tuesdayClose;
		this.wednesOpen = wednesOpen;
		this.wednesClose = wednesClose;
		this.thursOpen = thursOpen;
		this.thursClose = thursClose;
		this.fridayOpen = fridayOpen;
		this.fridayClose = fridayClose;
		this.saturdayOpen = saturdayOpen;
		this.saturdayClose = saturdayClose;
		this.validTill = validTill;
	}

	protected OffersModel(Parcel in) {
		id = in.readString();
		shopId = in.readString();
		shopOpenTime = in.readString();
		shopCloseTime = in.readString();
		day = in.readString();
		categoryName = in.readString();
		shopName = in.readString();
		shopBanner = in.readString();
		shopLocation = in.readString();
		shopAddress = in.readString();
		shopLatitute = in.readString();
		shopLongitute = in.readString();
		shopWebsiteUrl = in.readString();
		mondayOpen = in.readString();
		mondayClose = in.readString();
		tuesdayOpen = in.readString();
		tuesdayClose = in.readString();
		wednesOpen = in.readString();
		wednesClose = in.readString();
		thursOpen = in.readString();
		thursClose = in.readString();
		fridayOpen = in.readString();
		fridayClose = in.readString();
		saturdayOpen = in.readString();
		saturdayClose = in.readString();
		validTill = in.readString();
	}

	public static final Creator<OffersModel> CREATOR = new Creator<OffersModel>() {
		@Override
		public OffersModel createFromParcel(Parcel in) {
			return new OffersModel(in);
		}

		@Override
		public OffersModel[] newArray(int size) {
			return new OffersModel[size];
		}
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopOpenTime() {
		return shopOpenTime;
	}

	public void setShopOpenTime(String shopOpenTime) {
		this.shopOpenTime = shopOpenTime;
	}

	public String getShopCloseTime() {
		return shopCloseTime;
	}

	public void setShopCloseTime(String shopCloseTime) {
		this.shopCloseTime = shopCloseTime;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopBanner() {
		return shopBanner;
	}

	public void setShopBanner(String shopBanner) {
		this.shopBanner = shopBanner;
	}

	public String getShopLocation() {
		return shopLocation;
	}

	public void setShopLocation(String shopLocation) {
		this.shopLocation = shopLocation;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopLatitute() {
		return shopLatitute;
	}

	public void setShopLatitute(String shopLatitute) {
		this.shopLatitute = shopLatitute;
	}

	public String getShopLongitute() {
		return shopLongitute;
	}

	public void setShopLongitute(String shopLongitute) {
		this.shopLongitute = shopLongitute;
	}

	public String getShopWebsiteUrl() {
		return shopWebsiteUrl;
	}

	public void setShopWebsiteUrl(String shopWebsiteUrl) {
		this.shopWebsiteUrl = shopWebsiteUrl;
	}

	public String getMondayOpen() {
		return mondayOpen;
	}

	public void setMondayOpen(String mondayOpen) {
		this.mondayOpen = mondayOpen;
	}

	public String getMondayClose() {
		return mondayClose;
	}

	public void setMondayClose(String mondayClose) {
		this.mondayClose = mondayClose;
	}

	public String getTuesdayOpen() {
		return tuesdayOpen;
	}

	public void setTuesdayOpen(String tuesdayOpen) {
		this.tuesdayOpen = tuesdayOpen;
	}

	public String getTuesdayClose() {
		return tuesdayClose;
	}

	public void setTuesdayClose(String tuesdayClose) {
		this.tuesdayClose = tuesdayClose;
	}

	public String getWednesOpen() {
		return wednesOpen;
	}

	public void setWednesOpen(String wednesOpen) {
		this.wednesOpen = wednesOpen;
	}

	public String getWednesClose() {
		return wednesClose;
	}

	public void setWednesClose(String wednesClose) {
		this.wednesClose = wednesClose;
	}

	public String getThursOpen() {
		return thursOpen;
	}

	public void setThursOpen(String thursOpen) {
		this.thursOpen = thursOpen;
	}

	public String getThursClose() {
		return thursClose;
	}

	public void setThursClose(String thursClose) {
		this.thursClose = thursClose;
	}

	public String getFridayOpen() {
		return fridayOpen;
	}

	public void setFridayOpen(String fridayOpen) {
		this.fridayOpen = fridayOpen;
	}

	public String getFridayClose() {
		return fridayClose;
	}

	public void setFridayClose(String fridayClose) {
		this.fridayClose = fridayClose;
	}

	public String getSaturdayOpen() {
		return saturdayOpen;
	}

	public void setSaturdayOpen(String saturdayOpen) {
		this.saturdayOpen = saturdayOpen;
	}

	public String getSaturdayClose() {
		return saturdayClose;
	}

	public void setSaturdayClose(String saturdayClose) {
		this.saturdayClose = saturdayClose;
	}

	public String getValidTill() {
		return validTill;
	}

	public void setValidTill(String validTill) {
		this.validTill = validTill;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(shopId);
		dest.writeString(shopOpenTime);
		dest.writeString(shopCloseTime);
		dest.writeString(day);
		dest.writeString(categoryName);
		dest.writeString(shopName);
		dest.writeString(shopBanner);
		dest.writeString(shopLocation);
		dest.writeString(shopAddress);
		dest.writeString(shopLatitute);
		dest.writeString(shopLongitute);
		dest.writeString(shopWebsiteUrl);
		dest.writeString(mondayOpen);
		dest.writeString(mondayClose);
		dest.writeString(tuesdayOpen);
		dest.writeString(tuesdayClose);
		dest.writeString(wednesOpen);
		dest.writeString(wednesClose);
		dest.writeString(thursOpen);
		dest.writeString(thursClose);
		dest.writeString(fridayOpen);
		dest.writeString(fridayClose);
		dest.writeString(saturdayOpen);
		dest.writeString(saturdayClose);
		dest.writeString(validTill);
	}
}