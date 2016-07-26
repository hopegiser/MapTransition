package com.wang.leadmap.mapdemo.maplayerinfo;

import java.util.List;

/**
 * Created by wang on 15/6/25.
 */
public class MapInfo {

    public double currentVersion;

    public String serviceDescription;

    public String mapName;

    public String description;

    public String copyrightText;

    public String supportsDynamicLayers;

    public List<Layers> layers;

    public int[] tables;

    public SpatialReference spatialReference;

    public boolean singleFusedMapCache;

    public TileInfo tileInfo;

    public InitialExtent initialExtent;

    public InitialExtent fullExtent;

    public double minScale;

    public double maxScale;

    public String units;

    public String supportedImageFormatTypes;

    public DocumentInfo documentInfo;

    public String capabilities;

    public String supportedQueryFormats;

    public boolean hasVersionedData;

    public int maxRecordCount;

    public int maxImageHeight;

    public int maxImageWidth;

}
