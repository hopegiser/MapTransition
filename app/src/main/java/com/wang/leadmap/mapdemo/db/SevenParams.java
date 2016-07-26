package com.wang.leadmap.mapdemo.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Hope on 2016/4/5.
 */
@Table(name = "TAB_SEVENPARAMS")
public class SevenParams extends Model {
    @Column(name = "Px")
    public double Px;

    @Column(name = "Py")
    public double Py;

    @Column(name = "Pz")
    public double Pz;

    @Column(name = "Rx")
    public double Rx;

    @Column(name = "Ry")
    public double Ry;

    @Column(name = "Rz")
    public double Rz;

    @Column(name = "K")
    public double K;

    public SevenParams() {
        super();
    }

    public SevenParams(double px, double py, double pz, double rx, double ry, double rz, double k) {
        Px = px;
        Py = py;
        Pz = pz;
        Rx = rx;
        Ry = ry;
        Rz = rz;
        K = k;
    }
}
