package com.wang.leadmap.mapdemo;

import android.util.Log;

import com.esri.android.map.TiledServiceLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.io.UserCredentials;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wang on 15/6/15.
 */
public class TianDiTuLayout extends TiledServiceLayer {

    private TDTLayerType mapType;
    private TileInfo tiandituInfo;


    public TianDiTuLayout()
    {
        this(null,null,true);
    }

    public TianDiTuLayout(TDTLayerType type)
    {
        this(type,null,true);
    }

    public TianDiTuLayout(TDTLayerType type,UserCredentials userCredentials)
    {
        this(type,userCredentials,true);
    }

    public TianDiTuLayout(TDTLayerType type, UserCredentials userCredentials,boolean flag)
    {
        super("");
        this.mapType = type;
        setCredentials(userCredentials);
        if (flag)
        {
            try {

                getServiceExecutor().submit(new Runnable() {
                    @Override
                    public void run() {

                        layout.initLayer();

                    }
                    final TianDiTuLayout layout;
                    {
                        layout = TianDiTuLayout.this;
                    }

                });
                return;

            }catch (Exception e)
            {

            }
        }
    }


    @Override
    protected byte[] getTile(int level, int col, int row) throws Exception {

        byte[] result = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            URL sjwurl = new URL(this.getTianDiMapUrl(level, col, row));
            HttpURLConnection httpUrl = null;
            BufferedInputStream bis = null;
            byte[] buf = new byte[1024];

            httpUrl = (HttpURLConnection) sjwurl.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());

            while (true) {
                int bytes_read = bis.read(buf);
                if (bytes_read > 0) {
                    bos.write(buf, 0, bytes_read);
                } else {
                    break;
                }
            }

            bis.close();
            httpUrl.disconnect();

            result = bos.toByteArray();

        }catch (Exception e){}



        return result;
    }

    private String getTianDiMapUrl(int level, int col, int row){

        TDTMapUrl tdtMapUrl = new TDTMapUrl(level,col,row,this.mapType);
        tdtMapUrl.setMapUrl();
        String url = tdtMapUrl.getMapUrl();
        Log.e("wang",url);
        return url;
    }

    @Override
    protected void initLayer() {
        this.buildTileInfo();
        this.setFullExtent(new Envelope(-180,-90,180,90));
        this.setDefaultSpatialReference(SpatialReference.create(4490));   //CGCS2000
        //this.setDefaultSpatialReference(SpatialReference.create(4326));
        this.setInitialExtent(new Envelope(90.52,33.76,113.59,42.88));
        super.initLayer();
    }

    private void buildTileInfo()
    {
        Point originalPoint=new Point(-180,90);

        double[] res={
                1.40625,
                0.703125,
                0.3515625,
                0.17578125,
                0.087890625,
                0.0439453125,
                0.02197265625,
                0.010986328125,
                0.0054931640625,
                0.00274658203125,
                0.001373291015625,
                0.0006866455078125,
                0.00034332275390625,
                0.000171661376953125,
                8.58306884765629E-05,
                4.29153442382814E-05,
                2.14576721191407E-05,
                1.07288360595703E-05,
                5.36441802978515E-06,
                2.68220901489258E-06,
                1.34110450744629E-06
        };
        double[] scale={
                400000000,
                295497598.5708346,
                147748799.285417,
                73874399.6427087,
                36937199.8213544,
                18468599.9106772,
                9234299.95533859,
                4617149.97766929,
                2308574.98883465,
                1154287.49441732,
                577143.747208662,
                288571.873604331,
                144285.936802165,
                72142.9684010827,
                36071.4842005414,
                18035.7421002707,
                9017.87105013534,
                4508.93552506767,
                2254.467762533835,
                1127.2338812669175,
                563.616940
        };
        int levels=21;
        int dpi=96;
        int tileWidth=256;
        int tileHeight=256;
        this.tiandituInfo=new TiledServiceLayer.TileInfo(originalPoint, scale, res, levels, dpi, tileWidth,tileHeight);
        this.setTileInfo(this.tiandituInfo);
    }
}
