package com.quickpick.views.ui.dashboard;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.quickpick.R;
import com.quickpick.presenter.utils.GPSTracker;
import com.quickpick.views.ui.customviews.CustomDialog;
import com.quickpick.views.ui.dashboard.tabs.EatsFragment;

import com.rey.material.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardTabs extends AppCompatActivity  {

	private Fragment currentFragment;
	private TabViewPagerAdapter adapter;
	private AHBottomNavigationAdapter navigationAdapter;
	private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
	private boolean useMenuResource = true;
	private int[] tabColors;
	private Handler handler = new Handler();

	// UI
	private AHBottomNavigationViewPager viewPager;
	private AHBottomNavigation bottomNavigation;
	private FloatingActionButton floatingActionButton;
	public final int REQUEST_CODE_ASK_LOCATION_PERMISSIONS = 124;
	GPSTracker gpsTracker;
	String lat="",lng="";
	protected Location mLastLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy);
		boolean enabledTranslucentNavigation = getSharedPreferences("shared", Context.MODE_PRIVATE)
				.getBoolean("translucentNavigation", false);
		setTheme(enabledTranslucentNavigation ? R.style.AppTheme_TranslucentNavigation : R.style.AppTheme);
		setContentView(R.layout.activity_home);
		initUI();

//		startIntentService();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
	}
	
	/**
	 * Init UI
	 */
	private void initUI() {
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
		}
		
		bottomNavigation = findViewById(R.id.bottom_navigation);
		viewPager = findViewById(R.id.view_pager);
		floatingActionButton = findViewById(R.id.floating_action_button);

		/*if (useMenuResource) {
			tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
			navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_nav_items);
			navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
		} else*/ {
			AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.drinks, R.drawable.drinks, R.color.color_tab_1);
			AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.eats, R.drawable.menu, R.color.color_tab_2);
			AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.pick, R.drawable.delivery, R.color.color_tab_3);
			AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.profile, R.drawable.profile, R.color.color_tab_3);

			bottomNavigationItems.add(item1);
			bottomNavigationItems.add(item2);
			bottomNavigationItems.add(item3);
			bottomNavigationItems.add(item4);

			bottomNavigation.addItems(bottomNavigationItems);
		}

		//bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);
		bottomNavigation.setTranslucentNavigationEnabled(true);

		bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
			@Override
			public boolean onTabSelected(int position, boolean wasSelected) {

				if (currentFragment == null) {
					currentFragment = adapter.getCurrentFragment();
				}

				/*if (wasSelected) {
					currentFragment.refresh();
					return true;
				}

				if (currentFragment != null) {
					currentFragment.willBeHidden();
				}*/

				viewPager.setCurrentItem(position, false);
				
				if (currentFragment == null) {
					return true;
				}
				
				currentFragment = adapter.getCurrentFragment();
//				currentFragment.willBeDisplayed();

				if (position == 1) {
					bottomNavigation.setNotification("", 1);

					floatingActionButton.setVisibility(View.VISIBLE);
					floatingActionButton.setAlpha(0f);
					floatingActionButton.setScaleX(0f);
					floatingActionButton.setScaleY(0f);
					floatingActionButton.animate()
							.alpha(1)
							.scaleX(1)
							.scaleY(1)
							.setDuration(300)
							.setInterpolator(new OvershootInterpolator())
							.setListener(new Animator.AnimatorListener() {
								@Override
								public void onAnimationStart(Animator animation) {

								}

								@Override
								public void onAnimationEnd(Animator animation) {
									floatingActionButton.animate()
											.setInterpolator(new LinearOutSlowInInterpolator())
											.start();
								}

								@Override
								public void onAnimationCancel(Animator animation) {

								}

								@Override
								public void onAnimationRepeat(Animator animation) {

								}
							})
							.start();

				} else {
					if (floatingActionButton.getVisibility() == View.VISIBLE) {
						floatingActionButton.animate()
								.alpha(0)
								.scaleX(0)
								.scaleY(0)
								.setDuration(300)
								.setInterpolator(new LinearOutSlowInInterpolator())
								.setListener(new Animator.AnimatorListener() {
									@Override
									public void onAnimationStart(Animator animation) {

									}

									@Override
									public void onAnimationEnd(Animator animation) {
										floatingActionButton.setVisibility(View.GONE);
									}

									@Override
									public void onAnimationCancel(Animator animation) {
										floatingActionButton.setVisibility(View.GONE);
									}

									@Override
									public void onAnimationRepeat(Animator animation) {

									}
								})
								.start();
					}
				}

				return true;
			}
		});
		
		/*
		bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
			@Override public void onPositionChange(int y) {
				Log.d("DashboardTabs", "BottomNavigation Position: " + y);
			}
		});
		*/

		viewPager.setOffscreenPageLimit(4);
		adapter = new TabViewPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);

		currentFragment = adapter.getCurrentFragment();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.callingFragment(floatingActionButton);
                if(getVisibleFragment() instanceof EatsFragment){
                	Log.e("visible eats fragment","<><>");
				}else{
                	Log.e("visible restaurant","<><<");
				}

            }
        });
		
		//bottomNavigation.setDefaultBackgroundResource(R.drawable.bottom_navigation_background);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if(permission_Location()) {
			gpsTracker = new GPSTracker(this);
			if (!gpsTracker.isLocationAvailable()) {
				CustomDialog.getInstance().commonDialog(this,
						"Please Enable Location Services",
						"Go to Settings -> Location Service ->" +
								"Check on Use GPS, Wi-Fi or mobile networks to determine location ","location");
			}else{
				lat = String.valueOf(gpsTracker.getLatitude());
				lng = String.valueOf(gpsTracker.getLongitude());
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case REQUEST_CODE_ASK_LOCATION_PERMISSIONS:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// if (gpsTracker == null)
					gpsTracker = new GPSTracker(this);
					if (gpsTracker.isLocationAvailable()) {
						Log.e("location", gpsTracker.getLatitude() + ":::" + gpsTracker.getLongitude());
						lat = String.valueOf(gpsTracker.getLatitude());
						lng = String.valueOf(gpsTracker.getLongitude());
						if(String.valueOf(gpsTracker.getLatitude()).equals("0.0")){
							gpsTracker.getLocation();
						}

					} else {
						CustomDialog.getInstance().commonDialog(this,
								"Please Enable Location Services",
								"Go to Settings -> Location Service ->" +
										"Check on Use GPS, Wi-Fi or mobile networks to determine location ","location");
					}

				}else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
					CustomDialog.getInstance().commonDialog(this,
							"Your Location permission Denied",
							"Go to Settings and Grant the permission to use better features. ","location permssion");
//                Toast.makeText(DashBoardActivityNew.this, "Go to Settings and Grant the permission to use this feature.", Toast.LENGTH_SHORT).show();
					// User selected the Never Ask Again Option
				} else {
					Toast.makeText(DashboardTabs.this, "Permission Denied", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}

	public boolean permission_Location() {
		int hasStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
		if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
			if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
				requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
						REQUEST_CODE_ASK_LOCATION_PERMISSIONS);
				return false;
			}

			requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
					REQUEST_CODE_ASK_LOCATION_PERMISSIONS);

			return false;
		}
		return true;
	}




	public HashMap<String ,String > getLatLongs(){
		HashMap<String ,String > params = new HashMap<>();
		params.put("lat",lat);
		params.put("lng",lng);
		return params;
	}
	private Fragment getVisibleFragment() {
		FragmentManager fragmentManager = DashboardTabs.this.getSupportFragmentManager();
		List<Fragment> fragments = fragmentManager.getFragments();
		for (Fragment fragment : fragments) {
			if (fragment != null && fragment.isVisible())
				return fragment;
		}
		return null;
	}



}
