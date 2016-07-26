package com.wang.leadmap.mapdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.google.gson.Gson;
import com.wang.leadmap.mapdemo.maplayerinfo.Lods;
import com.wang.leadmap.mapdemo.maplayerinfo.MapInfo;

/**
 * Created by wang on 15/6/15.
 */
public class MapActivity extends ActionBarActivity {

    private final static String MAPURL = "http://61.164.50.124:6080/arcgis/rest/services/Lm_YhWater_GWDXT0422/MapServer";
    private MapView mapView;
//    private RequestQueue mQueue;
    private MyTiledMapLayer tiledMapLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_map);
        mapView = (MapView)findViewById(R.id.map);
//        this.mQueue = Volley.newRequestQueue(this);
//        StringRequest sr = new StringRequest(MAPURL+"?f=pjson",listener,errorListener);
//        this.mQueue.add(sr);


    }

//    private Response.Listener<String> listener = new Response.Listener<String>() {
//        @Override
//        public void onResponse(String response) {
//            Gson gson = new Gson();
//            try {
//                MapInfo mapInfo = gson.fromJson(response,MapInfo.class);
//                if (mapInfo != null && mapInfo.tileInfo != null)
//                {
//                    MyLayerInfo.COLS = mapInfo.tileInfo.cols;
//                    MyLayerInfo.ROWS = mapInfo.tileInfo.rows;
//                    MyLayerInfo.DPI = mapInfo.tileInfo.dpi;
//                    MyLayerInfo.LEVELS = mapInfo.tileInfo.lods.size();
//                    MyLayerInfo.WKID = mapInfo.spatialReference.wkid;
//
//                    MyLayerInfo.SCALES = new double[mapInfo.tileInfo.lods.size()];
//                    MyLayerInfo.RESOLUTIONS = new double[mapInfo.tileInfo.lods.size()];
//
//                    for (int i = 0; i < mapInfo.tileInfo.lods.size(); i++)
//                        MyLayerInfo.SCALES[i] = mapInfo.tileInfo.lods.get(i).scale;
//
//                    for (int i = 0; i < mapInfo.tileInfo.lods.size(); i++)
//                        MyLayerInfo.RESOLUTIONS[i] = mapInfo.tileInfo.lods.get(i).resolution;
//                    MyLayerInfo.POINT = new Point(mapInfo.tileInfo.origin.x,mapInfo.tileInfo.origin.y);
//
//                    MyLayerInfo.FULLEXTENT = new Envelope(mapInfo.fullExtent.xmin,mapInfo.fullExtent.ymin,mapInfo.fullExtent.xmax,mapInfo.fullExtent.ymax);
//                    MyLayerInfo.INITEXTENT = new Envelope(mapInfo.initialExtent.xmin,mapInfo.initialExtent.ymin,mapInfo.initialExtent.xmax,mapInfo.initialExtent.ymax);
//
//                    tiledMapLayer = new MyTiledMapLayer(MAPURL);
//                    mapView.addLayer(tiledMapLayer);
//
//
//
//                }
//            }catch (Exception e)
//            {
//                Log.e("wang",e.getMessage().toString());
//            }
//
//        }
//    };
//
//    private Response.ErrorListener errorListener = new Response.ErrorListener() {
//
//        @Override
//        public void onErrorResponse(VolleyError error) {
//
//        }
//    };
}
