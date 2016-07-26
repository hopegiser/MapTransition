package com.wang.leadmap.mapdemo;

/**
 * Created by wang on 15/6/15.
 */
public class TDTMapUrl {

    private TDTLayerType mTdtLayerType;
    private int mLevel;
    private int mCol;
    private int mRow;

    private String mapUrl;

    public TDTMapUrl(int level, int col, int row,TDTLayerType type)
    {
        this.mTdtLayerType = type;
        this.mLevel = level;
        this.mCol = col;
        this.mRow = row;

    }

    public String getMapUrl() {
        return mapUrl;
    }



    public void setMapUrl() {
        StringBuilder url=new StringBuilder("http://t0");
        switch (this.mTdtLayerType)
        {
            case VEC_C:
                url.append(".tianditu.com/vec_c/wmts?request=GetCapabilities&service=wmts&X=")
                        .append(this.mCol).append("&Y=")
                        .append(this.mRow).append("&L=")
                        .append(this.mLevel);
                break;
            case IMG_C:

                url.append(".tianditu.com/img_c/wmts?request=GetCapabilities&service=wmts&X=")
                        .append(this.mCol).append("&Y=")
                        .append(this.mRow).append("&L=")
                        .append(this.mLevel);

                break;
            case CIA_C:

                url.append(".tianditu.com/cia_c/wmts?request=GetCapabilities&service=wmts&X=")
                        .append(this.mCol).append("&Y=")
                        .append(this.mRow).append("&L=")
                        .append(this.mLevel);

                break;
            case CVA_C:

                url.append(".tianditu.com/cva_c/wmts?request=GetCapabilities&service=wmts&X=")
                        .append(this.mCol).append("&Y=")
                        .append(this.mRow).append("&L=")
                        .append(this.mLevel);

                break;
        }

        mapUrl = url.toString();
    }

}
