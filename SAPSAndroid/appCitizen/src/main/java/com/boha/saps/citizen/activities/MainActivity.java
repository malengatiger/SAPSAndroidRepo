package com.boha.saps.citizen.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.boha.saps.citizen.R;
import com.boha.sapslibrary.activities.IncidentActivity;
import com.boha.sapslibrary.activities.IncidentMapActivity;
import com.boha.sapslibrary.activities.PoliceStationMapActivity;
import com.boha.sapslibrary.adapters.IncidentAdapter;
import com.boha.sapslibrary.adapters.PoliceStationAdapter;
import com.boha.sapslibrary.fragments.IncidentListFragment;
import com.boha.sapslibrary.fragments.PageFragment;
import com.boha.sapslibrary.fragments.PoliceStationListFragment;
import com.boha.sapslibrary.util.DataUtil;
import com.boha.sapslibrary.util.DepthPageTransformer;
import com.boha.sapslibrary.util.OKUtil;
import com.boha.sapslibrary.util.Util;
import com.boha.vodacom.dto.PanicIncidentDTO;
import com.boha.vodacom.dto.PoliceStationDTO;
import com.boha.vodacom.dto.ResponseDTO;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        PoliceStationAdapter.PoliceStationListener,
        IncidentAdapter.IncidentListener{



    static List<PageFragment> pageFragmentList = new ArrayList<>();
    static List<String> mTitles = new ArrayList<>();
    ViewPager viewPager;
    MainPagerAdapter mainPagerAdapter;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location location;
    ImageView hero, iconAddIncident, iconIncidentMap;
    boolean mResolvingError;
    FloatingActionButton fab;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layout);

        ctx = getApplicationContext();

        setupViewPager();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        Util.setCustomActionBar(ctx,
                getSupportActionBar(),"CityZAn", "An app for the people!",
                ContextCompat.getDrawable(ctx,R.drawable.ic_person_add_white_24dp));
    }

    @Override
    public void onResume() {
        super.onResume();
        hero.setImageDrawable(Util.getRandomBackgroundImage(getApplicationContext()));
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainPagerAdapter);
        iconAddIncident = (ImageView)findViewById(R.id.iconAdd);
        iconIncidentMap = (ImageView)findViewById(R.id.iconMap);
        hero = (ImageView)findViewById(R.id.hero);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStations();
            }
        });
        iconIncidentMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentPageIndex) {
                    case 0:
                        Intent m = new Intent(ctx, PoliceStationMapActivity.class);
                        startActivity(m);
                        break;
                    case 1:
                        Intent m2 = new Intent(ctx, IncidentMapActivity.class);
                        startActivity(m2);
                        break;
                }
            }
        });
        iconAddIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getApplicationContext(), IncidentActivity.class);
                startActivityForResult(m,INCIDENT_REQUESTED);
            }
        });

    }
    static final int INCIDENT_REQUESTED = 1145;
    PoliceStationListFragment policeStationListFragment;
    IncidentListFragment incidentListFragment;
    int currentPageIndex;

    private void buildPages() {
        pageFragmentList = new ArrayList<>();

        policeStationListFragment = PoliceStationListFragment.newInstance(policeStations);
        pageFragmentList.add(policeStationListFragment);
        mTitles.add("Police Stations");
        mainPagerAdapter.notifyDataSetChanged();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setPageTransformer(true, new DepthPageTransformer());
    }

    List<PoliceStationDTO> policeStations;
    private void getStations() {
        setRefreshActionButtonState(true);
        snackbar = Snackbar.make(viewPager,"Finding Police Stations around you",Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        DataUtil.findPoliceStationsByRadius(getApplicationContext(), location.getLatitude(), location.getLongitude(), 22, new OKUtil.OKListener() {
            @Override
            public void onResponse(final ResponseDTO response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshActionButtonState(false);
                        snackbar.dismiss();
                        policeStations = response.getPoliceStations();
                        Log.w(LOG,"##### policeStations found: " + policeStations.size());
                        buildPages();
                        getIncidents();
                    }
                });
            }

            @Override
            public void onError(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshActionButtonState(false);
                        snackbar.dismiss();
                        Snackbar.make(viewPager,message,Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    List<PanicIncidentDTO> incidents;
    private void getIncidents() {
        setRefreshActionButtonState(true);
        snackbar = Snackbar.make(viewPager,"Finding Police Stations around you",Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        DataUtil.findIncidentsByRadius(getApplicationContext(), location.getLatitude(), location.getLongitude(), 22, new OKUtil.OKListener() {
            @Override
            public void onResponse(final ResponseDTO response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshActionButtonState(false);
                        snackbar.dismiss();
                        incidents = response.getIncidents();
                        Log.w(LOG,"##### incidents found: " + incidents.size());
                        pageFragmentList.add(IncidentListFragment.newInstance(incidents));
                        mTitles.add("Incidents");
                        mainPagerAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshActionButtonState(false);
                        snackbar.dismiss();
                        Snackbar.make(viewPager,message,Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    @Override
    public void onConnected(Bundle bundle) {
        Log.w(LOG, "########### mGoogleApiClient onConnected ....");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);

            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (location != null)
            Log.w(LOG, "## googleApiClient onConnected, requesting location updates ....getLastLocation acc: " + location.getAccuracy());
        setLocationRequest();
    }

    private void setLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setFastestInterval(1000);
        mRequestingLocationUpdates = true;

        startLocationUpdates();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.w(LOG, "********** permission granted, OK!");
                    setLocationRequest();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.e(LOG, "+++++++ startLocationUpdates, requestLocationUpdates fired");
        setRefreshActionButtonState(true);

    }

    private void stopLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        Log.d(LOG, "---------- stopLocationUpdates removeLocationUpdates fired");
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        Log.e(LOG, "####### onLocationChanged, accuracy: " + location.getAccuracy());
        setRefreshActionButtonState(false);
        if (location.getAccuracy() < ACCURACY) {
            stopLocationUpdates();
            if (snackbar != null) {
                snackbar.dismiss();
            }
            if (isDirectionsNeeded) {
                isDirectionsNeeded = false;
                getDirections();
                return;
            }
            if (isIncidentDirections) {
                isIncidentDirections = false;
                getIncidentDirections();
                return;
            }

            getStations();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    boolean isDirectionsNeeded, isIncidentDirections;
    PoliceStationDTO station;
    PanicIncidentDTO incident;
    Snackbar snackbar;
    @Override
    public void onDirections(PoliceStationDTO station) {
        this.station = station;
        isDirectionsNeeded = true;
        snackbar = Snackbar.make(viewPager,"Confirming location coordinates before getting directions",Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        startLocationUpdates();
    }

    private void getDirections() {
        Log.i(LOG, "startDirectionsMap ..........");

        String url = "http://maps.google.com/maps?saddr="
                + location.getLatitude() + "," + location.getLongitude()
                + "&daddr=" + station.getLatitude() + "," + station.getLongitude() + "&mode=driving";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }
    @Override
    public void onLove(PoliceStationDTO station) {

    }

    @Override
    public void onChat(PoliceStationDTO station) {

    }

    @Override
    public void onEdit(PoliceStationDTO station) {

    }

    @Override
    public void onDirections(PanicIncidentDTO incident) {
        this.incident = incident;
        isIncidentDirections = true;
        snackbar = Snackbar.make(viewPager,"Confirming location coordinates before getting directions",Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        startLocationUpdates();
    }
    private void getIncidentDirections() {
        Log.i(LOG, "startDirectionsMap ..........");

        String url = "http://maps.google.com/maps?saddr="
                + location.getLatitude() + "," + location.getLongitude()
                + "&daddr=" + incident.getLatitude() + "," + incident.getLongitude() + "&mode=driving";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }
    @Override
    public void onCamera(PanicIncidentDTO incident) {

    }

    @Override
    public void onChat(PanicIncidentDTO incident) {

    }

    private static class MainPagerAdapter extends FragmentStatePagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            return (Fragment) pageFragmentList.get(i);
        }

        @Override
        public int getCount() {
            return pageFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.boha.sapslibrary.R.menu.citizen_menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == com.boha.sapslibrary.R.id.action_refresh) {
            getStations();
            return true;
        }
        if (id == com.boha.sapslibrary.R.id.action_help) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setRefreshActionButtonState(final boolean refreshing) {
        if (mMenu != null) {
            final MenuItem refreshItem = mMenu.findItem(R.id.action_refresh);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.action_bar_progess);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(LOG, "################ onStart .... connect API and location clients ");
        if (!mResolvingError) {  // more about this later
            mGoogleApiClient.connect();
        }

    }

    @Override
    protected void onStop() {
        Log.w(LOG, "############## onStop stopping google service clients");
        try {
            if (mGoogleApiClient != null)
                mGoogleApiClient.disconnect();
        } catch (Exception e) {
            Log.e(LOG, "Failed to Stop something", e);
        }
        super.onStop();
    }
    Menu mMenu;
    boolean mRequestingLocationUpdates;
    static final String LOG = MainActivity.class.getSimpleName();
    static final int REQUEST_FINE_LOCATION = 66, ACCURACY = 150, REQUEST_RESOLVE_ERROR = 565;
}
