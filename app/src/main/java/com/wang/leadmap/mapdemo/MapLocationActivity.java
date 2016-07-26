package com.wang.leadmap.mapdemo;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.symbol.MarkerSymbol;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.Symbol;

/**
 * Created by wang on 15/7/3.
 */
public class MapLocationActivity extends ActionBarActivity {

    private MapView mapView;
    private ArcGISDynamicMapServiceLayer dynamicMapServiceLayer;
    private LocationDisplayManager mLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_map);
        mapView = (MapView)findViewById(R.id.map);
        ArcGISRuntime.setClientId("uK0DxqYT0om1UXa9");
        mapView.setMapBackground(0xffffff, 0xffffff, 10, 1);
        dynamicMapServiceLayer = new ArcGISDynamicMapServiceLayer("http://183.129.204.238:6082/arcgis/rest/services/Lm_XsWater_GWT/MapServer");
        mapView.addLayer(dynamicMapServiceLayer);


        mapView.getLocationDisplayManager().getLocation();

        mLocationService = mapView.getLocationDisplayManager();
        mLocationService.setAllowNetworkLocation(true);
        mLocationService.setShowLocation(true);
        mLocationService.setShowPings(true);
        mLocationService.setAutoPanMode(LocationDisplayManager.AutoPanMode.LOCATION);
        mLocationService.setLocationListener(new CustomLocationListener());
        mLocationService.setUseCourseSymbolOnMovement(true);
//        mLocationService.setOpacity(0.5f);
//        mLocationService.setWanderExtentFactor(20f);

        Drawable drawable = this.getResources().getDrawable(R.drawable.icon_user_location);
        Drawable drawable1 = this.getResources().getDrawable(R.drawable.icon_location_coord);
        Drawable drawable2 = this.getResources().getDrawable(R.drawable.icon_start);
        PictureMarkerSymbol picMS = new PictureMarkerSymbol(drawable);
        PictureMarkerSymbol picMS1 = new PictureMarkerSymbol(drawable1);
        PictureMarkerSymbol picMS2 = new PictureMarkerSymbol(drawable2);
        try {
//            mLocationService.setLocationAcquiringSymbol(picMS);
//            mLocationService.setDefaultSymbol(picMS);
//            mLocationService.setPingSymbol(picMS1);
//            mLocationService.setHeadingSymbol(picMS2);
//            mLocationService.setCourseSymbol(picMS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLocationService.start();
    }


    private class CustomLocationListener implements LocationListener
    {

        @Override
        public void onLocationChanged(Location location) {

            if (location != null)
            {
                Toast.makeText(MapLocationActivity.this
                        ,"定位模式"+location.getProvider()
                        +"\n时间："+location.getTime()
                        +"\nX："+location.getLatitude()
                        +"\nY："+location.getLongitude()
                        ,Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


}
