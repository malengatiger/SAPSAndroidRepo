package com.boha.sapslibrary.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.boha.sapslibrary.R;
import com.boha.sapslibrary.util.DataUtil;
import com.boha.sapslibrary.util.OKUtil;
import com.boha.sapslibrary.util.Util;
import com.boha.vodacom.dto.PoliceStationDTO;
import com.boha.vodacom.dto.ResponseDTO;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class PoliceStationMapActivity extends AppCompatActivity
        implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleMap googleMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location location;
    TextView txtCount;
    boolean mResolvingError;
    Context ctx;

    FloatingActionButton fab;
    boolean mRequestingLocationUpdates;

    List<Marker> markers = new ArrayList<Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_station_map);
        ctx = getApplicationContext();

        txtCount = (TextView) findViewById(R.id.count);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        Util.setCustomActionBar(ctx,getSupportActionBar(),
                "Police Station Map","Stations found 30 km radius",ContextCompat.getDrawable(ctx,android.R.drawable.ic_dialog_map));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap m) {
                googleMap = m;
                setMap();

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationUpdates();
            }
        });
    }

    private void setMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);

            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    private void getStreetView(double latitude, double longitude) {
        StringBuilder sb = new StringBuilder();
        sb.append("google.streetview:cbll=");
        sb.append(latitude).append(",").append(longitude);
        Uri gmmIntentUri = Uri.parse(sb.toString());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.psmap_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            return true;
        }
        if (id == R.id.action_help) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        Log.e(LOG, "####### onLocationChanged");
        if (location.getAccuracy() < ACCURACY) {
            stopLocationUpdates();
            getStations();
        }
    }


    private void getStations() {
        DataUtil.findPoliceStationsByRadius(getApplicationContext(),
                location.getLatitude(),
                location.getLongitude(), 30, new OKUtil.OKListener() {
                    @Override
                    public void onResponse(ResponseDTO response) {
                        snackbar.dismiss();
                        policeStations = response.getPoliceStations();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setMarkers();
                            }
                        });

                    }

                    @Override
                    public void onError(final String message) {
                        //Snackbar.make(fab, message, Snackbar.LENGTH_LONG).show();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                snackbar.dismiss();
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });
    }

    List<PoliceStationDTO> policeStations;
    int index;

    private void setMarkers() {
        googleMap.clear();
        markers.clear();
        index = 0;
        BitmapDescriptor desc = null;
        Drawable icon = ContextCompat.getDrawable(ctx,R.drawable.police);
        desc = BitmapDescriptorFactory.fromBitmap(Util.drawableToBitmap(icon));

        for (PoliceStationDTO station : policeStations) {
            if (station.getLatitude() == null) continue;
            LatLng pnt = new LatLng(station.getLatitude(), station.getLongitude());
             Marker m = googleMap.addMarker(new MarkerOptions()
                    .title(station.getPoliceStationID().toString())
                    .icon(desc)
                    .snippet(station.getName())
                    .position(pnt));
            markers.add(m);
            index++;
        }
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //ensure that all markers in bounds
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }

                LatLngBounds bounds = builder.build();
                int padding = 60; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                txtCount.setText("" + markers.size());
                googleMap.animateCamera(cu);

            }
        });

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
                    startLocationUpdates();
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

    Snackbar  snackbar;
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.e(LOG, "+++++++ startLocationUpdates, requestLocationUpdates fired");
        snackbar = Snackbar.make(fab,"Refreshing the data on your map ...",Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
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

    static final int REQUEST_FINE_LOCATION = 66, ACCURACY = 100, REQUEST_RESOLVE_ERROR = 565;
    static final String DIALOG_ERROR = "Dialig Error";
    static final String LOG = PoliceStationMapActivity.class.getSimpleName();

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GooglePlayServicesUtil.getErrorDialog()
            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
    }

    /* Creates a dialog for an error message */
    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getFragmentManager(), "errordialog");
    }

    /* Called from ErrorDialogFragment when the dialog is dismissed. */
    public void onDialogDismissed() {
        mResolvingError = false;
    }


    List<BitmapDescriptor> bmdList = new ArrayList<BitmapDescriptor>();

    private Drawable getIcon(int index) {


        try {
            switch (index) {
                case 0:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_1);

                case 1:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_2);

                case 2:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_3);

                case 3:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_4);

                case 4:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_5);

                case 5:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_6);

                case 6:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_7);

                case 7:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_8);

                case 8:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_9);

                case 9:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_10);

                case 10:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_11);

                case 11:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_12);

                case 12:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_13);

                case 13:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_14);

                case 14:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_15);

                case 15:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_16);

                case 16:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_17);

                case 17:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_18);

                case 18:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_19);

                case 19:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_20);

                case 20:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_21);

                case 21:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_22);

                case 22:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_23);

                case 23:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_24);

                case 24:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_25);

                case 25:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_26);

                case 26:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_27);

                case 27:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_28);

                case 28:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_29);

                case 29:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_30);

                case 30:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_31);

                case 31:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_32);

                case 32:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_33);

                case 33:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_34);

                case 34:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_35);

                case 35:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_36);

                case 36:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_37);

                case 37:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_38);

                case 38:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_39);

                case 39:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_40);

                case 40:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_41);

                case 41:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_42);

                case 42:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_43);

                case 43:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_44);

                case 44:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_45);

                case 45:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_46);

                case 46:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_47);

                case 47:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_48);

                case 48:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_49);

                case 49:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_50);

                case 50:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_51);

                case 51:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_52);

                case 52:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_53);

                case 53:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_54);

                case 54:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_55);

                case 55:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_56);

                case 56:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_57);

                case 57:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_58);

                case 58:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_59);

                case 59:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_60);

                case 60:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_61);

                case 61:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_62);

                case 62:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_63);

                case 63:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_64);

                case 64:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_65);

                case 65:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_66);

                case 66:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_67);

                case 67:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_68);

                case 68:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_69);

                case 69:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_70);

                case 70:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_71);

                case 71:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_72);

                case 72:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_73);

                case 73:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_74);

                case 74:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_75);

                case 75:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_76);

                case 76:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_77);

                case 77:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_78);

                case 78:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_79);

                case 79:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_80);

                case 80:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_81);

                case 81:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_82);

                case 82:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_83);

                case 83:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_84);

                case 84:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_85);

                case 85:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_86);

                case 86:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_87);

                case 87:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_88);

                case 88:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_89);

                case 89:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_90);

                case 90:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_91);

                case 91:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_92);

                case 92:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_93);

                case 93:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_94);

                case 94:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_95);

                case 95:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_96);

                case 96:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_97);

                case 97:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_98);

                case 98:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_99);

                case 99:
                    return ContextCompat.getDrawable(ctx, R.drawable.number_100);

            }
        } catch (Exception e) {
            Log.e(LOG, "Load icons failed", e);
        }

        return ContextCompat.getDrawable(ctx, R.drawable.number_1);
    }

}
