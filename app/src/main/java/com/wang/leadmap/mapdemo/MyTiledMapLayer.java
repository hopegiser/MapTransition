package com.wang.leadmap.mapdemo;

import android.os.Environment;

import com.esri.android.map.TiledServiceLayer;
import com.esri.core.geometry.SpatialReference;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wang on 15/6/25.
 */
public class MyTiledMapLayer extends TiledServiceLayer {

    private static final String CACHE_PATH = Environment.getExternalStorageState()+"MapTiled";
    private String mapUrl;
    private TileInfo layerInfo;


    public MyTiledMapLayer(String url) {
        super(url);
        this.mapUrl = url;
        initLayer();
    }

    @Override
    protected byte[] getTile(int level, int col, int row) throws Exception {

        byte [] bytes = null;

        bytes = getOfflineCacheFile(level, col, row);

        if (bytes == null)
        {
            String url = mapUrl + "/tile/"+level+"/"+row+"/"+col;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            URL sjwurl = new URL(url);
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
            bytes = bos.toByteArray();
            //保存在本地
            AddOfflineCacheFile(level,col,row,bytes);
        }

        return bytes;
    }

    @Override
    protected void initLayer() {
        buildTileInfo();
        this.setFullExtent(MyLayerInfo.FULLEXTENT);
        this.setInitialExtent(MyLayerInfo.INITEXTENT);
        this.setDefaultSpatialReference(SpatialReference.create(MyLayerInfo.WKID));
        setTileInfo(layerInfo);
        super.initLayer();
    }

    private void buildTileInfo()
    {
        if (MyLayerInfo.SCALES != null)
        this.layerInfo = new TileInfo(MyLayerInfo.POINT
                ,MyLayerInfo.SCALES
                ,MyLayerInfo.RESOLUTIONS
                ,MyLayerInfo.LEVELS
                ,MyLayerInfo.DPI
                ,MyLayerInfo.ROWS
                ,MyLayerInfo.COLS);
    }


    private byte[] AddOfflineCacheFile(int level, int col, int row, byte[] bytes) throws Exception
    {
        File file = new File(CACHE_PATH);
        if (!file.exists())
            file.mkdirs();

        File levelFile = new File(CACHE_PATH + "/"+level);
        if (!levelFile.exists())
            levelFile.mkdirs();

        File colFile = new File(CACHE_PATH + "/" + level + "/" + col);
        if (!colFile.exists())
            colFile.mkdirs();

        File rowFile = new File(CACHE_PATH + "/" + level + "/" + col +"/" + row +".dat");
        if (!rowFile.exists())
        {
            FileOutputStream out = new FileOutputStream(rowFile);
            out.write(bytes);
        }

        return bytes;

    }


    private byte[] getOfflineCacheFile(int level, int col, int row) throws IOException
    {
        byte[] bytes = null;

        StringBuilder sb = new StringBuilder();
        sb.append(CACHE_PATH).append("/").append(level).append("/").append(col).append("/").append(row).append(".dat");
        File rowFile = new File(sb.toString());
        if (rowFile.exists())
        {
           bytes = copeSdbytes(rowFile);
        }

        return bytes;
    }

    private byte[] copeSdbytes(File file) throws IOException
    {
        FileInputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] temp = new byte[1024];
        int size = 0;
        while ((size = in.read(temp)) != -1)
        {
            out.write(temp,0,size);
        }
        in.close();

        return out.toByteArray();
    }
}
