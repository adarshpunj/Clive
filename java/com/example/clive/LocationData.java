package com.example.clive;

public class LocationData {

    private String mLatitude;
    private String mLongitude;

    public LocationData(String mLatitude, String mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    public void setmLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }
    public void setmLongitude(String mLongitude) {
        this.mLongitude = mLongitude;
    }
    public String getmLatitude() {
        return mLatitude;
    }
    public String getmLongitude() {
        return mLongitude;
    }
}
