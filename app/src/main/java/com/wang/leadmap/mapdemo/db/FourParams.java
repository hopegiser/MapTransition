package com.wang.leadmap.mapdemo.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Hope on 2015/12/10.
 */
@Table(name = "TAB_FOURPARAMS")
public class FourParams extends Model {
    @Column(name = "dx")
    public double dx;

    @Column(name = "dy")
    public double dy;

    @Column(name = "red")
    public double red;

    @Column(name = "k")
    public double k;

    public FourParams(){
        super();
    }

    public FourParams(double dx, double dy, double red, double k) {
        this.dx = dx;
        this.dy = dy;
        this.red = red;
        this.k = k;
    }
}
