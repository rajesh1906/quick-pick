package com.quickpick.presenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.List;

public class GPSTracker implements LocationListener {

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	private final Activity mContext;
	// Declaring a Location Manager
	protected LocationManager locationManager;
	// flag for GPS status
	boolean isGPSEnabled = false;
	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = false;
	Location location; // location
	double latitude; // latitude
	double longitude; // longitude
	boolean isLocationAvailable;
	public GPSTracker(Activity context) {
		mContext = context;
		getLocation();
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) mContext
					.getSystemService(Context.LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				this.isLocationAvailable=true;
				if (isNetworkEnabled) {
						locationManager.requestLocationUpdates(
								LocationManager.NETWORK_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("Network", "Network");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
								latlng(latitude,longitude,mContext);
							}
						}


				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
								latlng(latitude,longitude,mContext);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(location==null){
			return getLastKnownLocation();
		}

		return location;
	}

	public boolean isLocationAvailable() {
		return this.isLocationAvailable;
	}
	private Location getLastKnownLocation() {
		LocationManager mLocationManager = (LocationManager)mContext.getApplicationContext().getSystemService(mContext.LOCATION_SERVICE);
		List<String> providers = mLocationManager.getProviders(true);
		Location bestLocation = null;
		for (String provider : providers) {
			Location l = mLocationManager.getLastKnownLocation(provider);
			if (l == null) {
				continue;
			}
			if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
				// Found best last known location: %s", l);
				bestLocation = l;
				location=bestLocation;
				latitude = bestLocation.getLatitude();
				longitude = bestLocation.getLongitude();
				latlng(latitude,longitude,mContext);
				break;
			}
		}
		return bestLocation;
	}
	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(GPSTracker.this);
		}
	}
	public static void latlng(Double lat, Double lng, Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("lat", ""+lat);
		editor.putString("lng", ""+lng);
		editor.commit();
	}
	public String getlatLNg(String val, Context context){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return  preferences.getString(val,"0.0");
	}
	/**
	 * Function to get latitude
	 */
	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}
		if(latitude==0.0){
			return Double.parseDouble(getlatLNg("lat",mContext));
		}
		// return latitude
		return latitude;
	}

	/**
	 * Function to get longitude
	 */
	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}
		if(longitude==0.0){
			return Double.parseDouble(getlatLNg("lng",mContext));
		}
		// return longitude
		return longitude;
	}


	@Override
	public void onLocationChanged(Location location) {
		this.location = location;
		getLatitude();
		getLongitude();
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}


}
