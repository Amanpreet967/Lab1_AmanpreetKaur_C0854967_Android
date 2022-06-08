package com.example.lab1_amanpreetkaur_c0854967_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private int polyLineClick = 0;
    int locationCount = 0;



    private AppCompatTextView distanceInKm;
    private int polygoneColorClicks, strokeColorCLick = 0;
    private List<Polygon> listOfPolyGone = new ArrayList<>();
    private Polyline polyline;

    List<Polyline> polylines = new ArrayList<>();
    List<LatLng> pointLatlng = new ArrayList<>();

    private static final int POLYGON_STROKE_WIDTH_PX = 8;


    private List<Marker> listOfMarker = new ArrayList<>();
    private MarkerOptions markerOptions;
    PolylineOptions polylineOptions = new PolylineOptions();
    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int COLOR_WHITE_ARGB = 0xfffff23f;
    private static final int COLOR_LIGHT_GREEN_ARGB = 0xff81C444;
    private static final int COLOR_DARK_ORANGE_ARGB = 0xffF57F17;
    private Polygon polygonMain;
    private SupportMapFragment mapFragment;
    private ProgressBar progressBar;
    private GoogleMap googleMap;

//
//    private static final int COLOR_BLACK_ARGB = 0xff000000;
//    private static final int COLOR_WHITE_ARGB = 0xfffff23f;
//    private static final int COLOR_LIGHT_GREEN_ARGB = 0xff81C444;
//    private static final int COLOR_DARK_ORANGE_ARGB = 0xffF57F17;
//    private Polygon polygonMain;
//    private SupportMapFragment mapFragment;
//    private ProgressBar progressBar;
//    private GoogleMap googleMap;


    private static final int COLOR_LIGHT_ORANGE_ARGB = 0xffF9A825;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private Marker locationMarker;
    private static final int COLOR_DARK_GREEN_ARGB = 0xff388E3C;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        distanceInKm = findViewById(R.id.total_km);
//

        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else { // Google Play Services are available

            FragmentManager fm = getSupportFragmentManager();
            mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_v2);
            if (mapFragment == null) {
                mapFragment = new SupportMapFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.map_v2, mapFragment);
                ft.commit();
                fm.executePendingTransactions();
            }
            progressBar.setVisibility(View.GONE);
            if (mapFragment != null) {
                mapFragment.getMapAsync(MainActivity.this);
            }

        }
//        findViewById(R.id.solid_color).setOnClickListener(v -> {
//            polygoneColorClicks++;
//            if (polygoneColorClicks == 1) {
//                stylePolygon(COLOR_DARK_ORANGE_ARGB);
//            } else if (polygoneColorClicks == 2) {
//                stylePolygon(COLOR_LIGHT_ORANGE_ARGB);
//            } else if (polygoneColorClicks == 3) {
//                stylePolygon(COLOR_LIGHT_GREEN_ARGB);
//            } else if (polygoneColorClicks == 4) {
//                polygoneColorClicks = 0;
//                stylePolygon(COLOR_BLACK_ARGB);
//            }

        findViewById(R.id.solid_color).setOnClickListener(v -> {
            polygoneColorClicks++;
            if (polygoneColorClicks == 1) {
                stylePolygon(COLOR_DARK_ORANGE_ARGB);
            } else if (polygoneColorClicks == 2) {
                stylePolygon(COLOR_LIGHT_ORANGE_ARGB);
            } else if (polygoneColorClicks == 3) {
                stylePolygon(COLOR_LIGHT_GREEN_ARGB);
            } else if (polygoneColorClicks == 4) {
                polygoneColorClicks = 0;
                stylePolygon(COLOR_BLACK_ARGB);
            }


        });
        findViewById(R.id.stroke_color).setOnClickListener(v -> {
            strokeColorCLick++;
            if (strokeColorCLick == 1) {
                polyline.setColor(Color.GREEN);
                polygonMain.setStrokeColor(Color.GREEN);
            } else if (strokeColorCLick == 2) {
                polyline.setColor(Color.BLACK);
                polygonMain.setStrokeColor(Color.BLACK);
            } else if (strokeColorCLick == 3) {
                polyline.setColor(Color.BLUE);
                polygonMain.setStrokeColor(Color.BLUE);
            } else if (strokeColorCLick == 4) {
                strokeColorCLick = 0;
                polyline.setColor(Color.WHITE);
                polygonMain.setStrokeColor(Color.WHITE);
            }


        });

//        locationMarker = googleMap.addMarker(markerOptions);
//        locationMarker.setTitle("A");
//        locationMarker.showInfoWindow();
//        listOfMarker.add(locationMarker);

    }


    private void drawMarker(LatLng point) {
        if (polylineOptions == null) {
            polylineOptions = new PolylineOptions();
        }
        polylineOptions.add(point);

        markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Adding marker on the Google Map
        locationMarker = googleMap.addMarker(markerOptions);
        locationMarker.setTitle("A");
        locationMarker.showInfoWindow();
        listOfMarker.add(locationMarker);

        polyline = this.googleMap.addPolyline(polylineOptions);
        polyline.setClickable(true);
        polyline.setColor(Color.RED);
        polylines.add(polyline);
        googleMap.setOnPolylineClickListener(line -> {
            line.remove();
//            if (polyLineClick == 1){
//                polyLineClick = 0;
//                polylines.remove(line);
//                googleMap.clear();
//            } else {
//                polyLineClick++;
//            }

        });
        googleMap.setOnPolygonClickListener(polygon -> {
            polygon.remove();
            googleMap.clear();
            googleMap.resetMinMaxZoomPreference();
            locationCount = 0;
            polylineOptions = null;
        });

    }



    private void stylePolygon(int color) {
        if (polylines.size() <= 4) {
            polygonMain = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            polylines.get(0).getPoints().get(0),
                            polylines.get(1).getPoints().get(1),
                            polylines.get(2).getPoints().get(2),
                            polylines.get(3).getPoints().get(3)));

            polygonMain.setTag("alpha");
            String type = "";
            // Get the data object stored with the polygon.
            if (polygonMain.getTag() != null) {
                type = polygonMain.getTag().toString();
            }
            List<PatternItem> pattern = null;
            int strokeColor = COLOR_DARK_ORANGE_ARGB;
            int fillColor = color;

            polygonMain.setStrokePattern(pattern);
            polygonMain.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
            polygonMain.setStrokeColor(strokeColor);
            polygonMain.setFillColor(fillColor);
            listOfPolyGone.add(polygonMain);
        }


    }





    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        CameraUpdate point = CameraUpdateFactory.newLatLng(new LatLng(43.6532, 79.3832));

// moves camera to coordinates
        googleMap.moveCamera(point);
// animates camera to coordinates
        googleMap.animateCamera(point);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                if (locationCount < 4) {
                    locationCount++;
                    pointLatlng.add(point);
                    drawMarker(point);
                } else {
                    Toast.makeText(getBaseContext(), "You can add up to 4 locations only", Toast.LENGTH_SHORT).show();
                }

                if (locationCount == 1) {
                    distanceInKm.setText("0 KM");
                } else if (locationCount == 2) {
                    double totalDistance = CalculationByDistance(pointLatlng.get(0), pointLatlng.get(1));
                    distanceInKm.setText(String.format("%.0f", totalDistance) + " KM");
                } else if (locationCount == 3) {
                    double totalDistance = CalculationByDistance(pointLatlng.get(0), pointLatlng.get(1)) +
                            CalculationByDistance(pointLatlng.get(1), pointLatlng.get(2));
                    distanceInKm.setText(String.format("%.0f", totalDistance) + " KM");
                } else if (locationCount == 4 || locationCount == 3) {
                    double totalDistance = CalculationByDistance(pointLatlng.get(0), pointLatlng.get(1)) +
                            CalculationByDistance(pointLatlng.get(1), pointLatlng.get(2)) + CalculationByDistance(pointLatlng.get(2), pointLatlng.get(3)) +
                            CalculationByDistance(pointLatlng.get(3), pointLatlng.get(0));
                    Log.d("LOGGER", totalDistance + "");
                    distanceInKm.setText(String.format("%.0f", totalDistance) + " KM");
                    stylePolygon(COLOR_LIGHT_GREEN_ARGB);
                    polylineOptions.color(Color.GREEN);
//                    if (listOfMarker.size() < 3){
//                        listOfMarker.get(0).setTitle("A");
//                        listOfMarker.get(1).setTitle("B");
//                        listOfMarker.get(2).setTitle("C");
//                        listOfMarker.get(3).setTitle("D");
//                    }
                }


            }

        });

        googleMap.setOnMarkerClickListener(marker -> {
            marker.remove();
            locationCount--;
            return false;
        });
    }

//    double lon1 = StartP.longitude;
//    double lon2 = EndP.longitude;
//    double dLat = Math.toRadians(lat2 - lat1);
//    double dLon = Math.toRadians(lon2 - lon1);
//    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//            + Math.cos(Math.toRadians(lat1))
//            * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
//            * Math.sin(dLon / 2);
//    double c = 2 * Math.asin(Math.sqrt(a));
//
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return km;
    }
}